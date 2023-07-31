package com.codecool.elproyectegrande1.security.oauth2;

import com.codecool.elproyectegrande1.entity.User;
import lombok.Getter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.oauth2.core.user.OAuth2User;

import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Map;

@Getter
public class UserPrincipal implements OAuth2User, UserDetails {

    private final Long id;
    private final String name;
    private final String email;
    private final String password;
    private Collection<? extends GrantedAuthority> authorities;
    private Map<String, Object> attributes;

    public UserPrincipal(Long id, String name, String email, String password, Collection<? extends GrantedAuthority> authorities) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.password = password;
        this.authorities = authorities;
    }

    public static UserPrincipal create(User appUser){
        List<GrantedAuthority> authorities = Collections.singletonList(new SimpleGrantedAuthority("ROLE_DREAMER"));

        return new UserPrincipal(
                appUser.getId(),
                appUser.getUsername(),
                appUser.getEmail(),
                appUser.getPassword(),
                authorities
        );
    }

    public static UserPrincipal create(User appUser, Map<String, Object> attributes){
        UserPrincipal principal = create(appUser);
        principal.attributes = attributes;
        return principal;
    }


    @Override
    public String getUsername() {
        return email;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

    @Override
    public String getName() {
        return name;
    }

}
