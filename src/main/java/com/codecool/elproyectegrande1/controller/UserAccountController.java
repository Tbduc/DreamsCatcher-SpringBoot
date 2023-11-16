package com.codecool.elproyectegrande1.controller;

import com.codecool.elproyectegrande1.entity.ConfirmationToken;
import com.codecool.elproyectegrande1.entity.User;
import com.codecool.elproyectegrande1.repository.ConfirmationTokenRepository;
import com.codecool.elproyectegrande1.service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@CrossOrigin(origins = "http://localhost:8081/")
@RestController
@RequestMapping("/api/v1/user-account")
public class UserAccountController {

    private final UserService userService;

    private final ConfirmationTokenRepository confirmationTokenRepository;

    public UserAccountController(UserService userService, ConfirmationTokenRepository confirmationTokenRepository) {
        this.userService = userService;
        this.confirmationTokenRepository = confirmationTokenRepository;
    }

    /**
     * Receive email of the user, create token and send it via email to the user
     */
    @PostMapping("/forgot-password")
    public void forgotUserPassword(@RequestParam("email") String email) {
        userService.sendResetPasswordEmail(email);
    }

    @PostMapping("/confirm-reset")
    public String validateResetToken(@RequestParam("token") String confirmationToken) {
        ConfirmationToken token = confirmationTokenRepository.findByConfirmationToken(confirmationToken);

        if(token != null) {
            User user = userService.findUserById(token.getUser().getId());
            if (user != null)
                return user.getEmail();
        }

        return null;
    }

    /**
     * Receive the token from the link sent via email and display form to reset password
     */
    @PostMapping("/reset-password")
    public ResponseEntity<?> resetUserPassword(String email, String password) {

        User user = userService.findUserByEmailAndChangePassword(email, password);
        if (user != null)
            return ResponseEntity.ok("Password was changed.");

        return ResponseEntity.internalServerError().body("No user was found.");
    }

}