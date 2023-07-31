package com.codecool.elproyectegrande1.security.oauth2.authentication;

import com.codecool.elproyectegrande1.entity.*;
import com.codecool.elproyectegrande1.repository.ImageRepository;
import com.codecool.elproyectegrande1.repository.RoleRepository;
import com.codecool.elproyectegrande1.service.exceptions.OAuth2AuthenticationProcessingException;
import com.codecool.elproyectegrande1.repository.UserRepository;
import com.codecool.elproyectegrande1.security.oauth2.UserPrincipal;
import com.codecool.elproyectegrande1.security.oauth2.authentication.user.OAuth2UserInfo;
import com.codecool.elproyectegrande1.security.oauth2.authentication.user.OAuth2UserInfoFactory;
import com.codecool.elproyectegrande1.service.DreamerService;
import lombok.AllArgsConstructor;
import org.springframework.security.authentication.InternalAuthenticationServiceException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

@Service
@AllArgsConstructor
public class CustomOauth2UserService extends DefaultOAuth2UserService {

    private final UserRepository userRepository;
    private final DreamerService dreamerService;
    private final RoleRepository roleRepository;

    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
        OAuth2User oAuth2User = super.loadUser(userRequest);

        try {
            return processOAuth2User(userRequest, oAuth2User);
        } catch (AuthenticationException ex) {
            throw ex;
        } catch (Exception e) {
            throw new InternalAuthenticationServiceException(e.getMessage(), e.getCause());
        }
    }

    private OAuth2User processOAuth2User(OAuth2UserRequest userRequest, OAuth2User oAuth2User) {
        OAuth2UserInfo oAuth2UserInfo = OAuth2UserInfoFactory.get(userRequest.getClientRegistration().getRegistrationId(), oAuth2User.getAttributes());

        if (!StringUtils.hasText(oAuth2UserInfo.getEmail())) {
            throw new OAuth2AuthenticationProcessingException("Email not found from provider");
        }

        Optional<User> userOptional = userRepository.findByEmail(oAuth2UserInfo.getEmail());
        User user;
        if (userOptional.isPresent()) {
            user = userOptional.get();
            if(!user.getProvider().equals(AuthProvider.valueOf(userRequest.getClientRegistration().getRegistrationId()))) {
                throw new OAuth2AuthenticationProcessingException("Looks like you're signed up with " +
                        user.getProvider() + " account. Please use your " + user.getProvider() +
                        " account to login.");
            }
            user = update(user, oAuth2UserInfo);
        } else {
            user = register(userRequest, oAuth2UserInfo);
        }

        return UserPrincipal.create(user, oAuth2User.getAttributes());
    }

    private Dreamer register(OAuth2UserRequest userRequest, OAuth2UserInfo oAuth2UserInfo) {
        Dreamer user = new Dreamer();
        Set<Role> roles = new HashSet<>();

        user.setProvider(AuthProvider.valueOf(userRequest.getClientRegistration().getRegistrationId()));
        user.setProviderId(oAuth2UserInfo.getId());
        user.setUsername(oAuth2UserInfo.getName());
        user.setEmail(oAuth2UserInfo.getEmail());
        user.setImageUrl(oAuth2UserInfo.getImageUrl());
        Role dreamer = roleRepository.findByName(ERole.ROLE_DREAMER).orElseThrow(() -> new RuntimeException("Error: Role is not found."));
        roles.add(dreamer);
        user.setRoles(roles);
        Dreamer savedUser = userRepository.save(user);

        return savedUser;
    }

    private User update(User user, OAuth2UserInfo oAuth2UserInfo) {
        user.setUsername(oAuth2UserInfo.getName());
        user.setImageUrl(oAuth2UserInfo.getImageUrl());
        return userRepository.save(user);
    }
}
