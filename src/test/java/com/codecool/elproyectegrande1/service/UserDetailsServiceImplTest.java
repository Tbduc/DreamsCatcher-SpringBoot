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
        //given:
        String username = "username";
        User user = new User(username, "email", "password");
        when(userRepository.findByUsername(username)).thenReturn(Optional.of(user));

        //when:
        UserDetails userDetails = userDetailsService.loadUserByUsername(username);

        //then:
        Assertions.assertNotNull(userDetails);
        Assertions.assertEquals(username, userDetails.getUsername());
    }

    @Test
    public void ShouldThrowsExceptionIfUsernameNotFound() {
        //given:
        String username = "username";

        //when:
        when(userRepository.findByUsername(username)).thenReturn(Optional.empty());

        //then:
        Assertions.assertThrows(
                UsernameNotFoundException.class,
                () -> userDetailsService.loadUserByUsername(username),
                "User Not Found with username: " + username
        );
    }

}