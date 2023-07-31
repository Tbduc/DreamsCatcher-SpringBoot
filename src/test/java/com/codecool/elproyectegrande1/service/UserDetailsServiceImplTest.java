package com.codecool.elproyectegrande1.service;

import com.codecool.elproyectegrande1.entity.User;
import com.codecool.elproyectegrande1.repository.UserRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.Optional;

import static org.mockito.Mockito.when;


@ExtendWith(MockitoExtension.class)

class UserDetailsServiceImplTest {
    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private UserDetailsServiceImpl userDetailsService;


    @Test
    public void shouldReturnsUserDetailsIfUserFoundByUsername() {

        String username = "username";
        User user = new User(username, "email", "password");
        when(userRepository.findByUsername(username)).thenReturn(Optional.of(user));

        UserDetails userDetails = userDetailsService.loadUserByUsername(username);

        Assertions.assertNotNull(userDetails);
        Assertions.assertEquals(username, userDetails.getUsername());
    }

    @Test
    public void ShouldThrowsExceptionIfUsernameNotFound() {

        String username = "username";
        when(userRepository.findByUsername(username)).thenReturn(Optional.empty());

        Assertions.assertThrows(
                UsernameNotFoundException.class,
                () -> userDetailsService.loadUserByUsername(username),
                "User Not Found with username: " + username
        );
    }

}