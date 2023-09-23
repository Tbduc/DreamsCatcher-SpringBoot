package com.codecool.elproyectegrande1.service;

import com.codecool.elproyectegrande1.dto.user.UserDto;
import com.codecool.elproyectegrande1.entity.Avatar;
import com.codecool.elproyectegrande1.entity.ERole;
import com.codecool.elproyectegrande1.entity.Mentor;
import com.codecool.elproyectegrande1.entity.User;
import com.codecool.elproyectegrande1.jwt.JwtUtils;
import com.codecool.elproyectegrande1.jwt.payload.request.LoginRequest;
import com.codecool.elproyectegrande1.jwt.payload.request.SignupRequest;
import com.codecool.elproyectegrande1.jwt.payload.response.MessageResponse;
import com.codecool.elproyectegrande1.mapper.UserMapper;
import com.codecool.elproyectegrande1.repository.AvatarRepository;
import com.codecool.elproyectegrande1.repository.MentorRepository;
import com.codecool.elproyectegrande1.repository.UserRepository;
import com.codecool.elproyectegrande1.security.oauth2.UserPrincipal;
import com.codecool.elproyectegrande1.service.exceptions.ResourceNotFoundException;
import com.codecool.elproyectegrande1.util.ImageUtil;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.oauth2.core.oidc.user.OidcUser;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Collections;
import java.util.Map;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final AvatarRepository avatarRepository;
    private final UserMapper userMapper;
    private final MentorRepository mentorRepository;

    public UserService(UserRepository userRepository, AvatarRepository avatarRepository, UserMapper userMapper, MentorRepository mentorRepository, AuthenticationManager authenticationManager, JwtUtils jwtUtils) {
        this.userRepository = userRepository;
        this.avatarRepository = avatarRepository;
        this.userMapper = userMapper;
        this.mentorRepository = mentorRepository;
    }

    public Map<String, Object> getUserClaims() {
        Authentication authentication = SecurityContextHolder.getContext()
                .getAuthentication();
        if (authentication.getPrincipal() instanceof OidcUser) {
            OidcUser principal = ((OidcUser) authentication.getPrincipal());
            return principal.getClaims();
        }
        return Collections.emptyMap();
    }

    public Avatar uploadAvatar(MultipartFile file) throws IOException {
        Avatar pAvatar = new Avatar();
        pAvatar.setName(file.getOriginalFilename());
        pAvatar.setType(file.getContentType());
        pAvatar.setImageData(ImageUtil.compressImage(file.getBytes()));
        return avatarRepository.save(pAvatar);
    }

    public Avatar getUserAvatar(String fileName) {
        Avatar avatar = avatarRepository.findAvatarByName(fileName);
        return avatar;
    }

    public byte[] getAvatarById(Long id) {
        Avatar image = avatarRepository.findAvatarByID(id);
        byte [] imageData = ImageUtil.decompressImage(image.getImageData());
        return imageData;
    }

    public User findUserById(Long id) {
        return userRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("User", "id", id));
    }

    public UserDto findUserDtoById(Long id) {
        User user = userRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("User", "id", id));
        return userMapper.mapEntityToDto(user);
    }

    public UserDetails loadUserById(String id) {
        User user = userRepository.findById(Long.valueOf(id))
                .orElseThrow(() -> new ResourceNotFoundException("User", "id", id));

        return UserPrincipal.create(user);
    }

    public void findUserIfExists(LoginRequest loginRequest) {
        User user = userRepository.findByUsername(loginRequest.getUsername()).orElseThrow(() -> new UsernameNotFoundException("User not found"));
        System.out.println(user.getRoles());
        if (user.getRoles().iterator().next().getName() == ERole.ROLE_MENTOR) {
            Mentor mentor = mentorRepository.findByUsername(loginRequest.getUsername()).orElseThrow(() -> new UsernameNotFoundException("User not found"));
            if (!mentor.isVerified()) {
                throw new BadCredentialsException("User not verified!");
            }
        }
    }

    public ResponseEntity<MessageResponse> getMessageResponseResponseEntity(SignupRequest signUpRequest) {
        if (userRepository.existsByUsername(signUpRequest.getUsername())) {
            return ResponseEntity
                    .badRequest()
                    .body(new MessageResponse("Error: Username is already taken!"));
        }

        if (userRepository.existsByEmail(signUpRequest.getEmail())) {
            return ResponseEntity
                    .badRequest()
                    .body(new MessageResponse("Error: Email is already in use!"));
        }
        return null;
    }

    public UserDto findUserByUserId(Long userId) {
        User user = userRepository.findById(userId).orElseThrow(() -> new ResourceNotFoundException("User", "id", userId));
        return userMapper.mapEntityToDto(user);
    }
}
