package com.codecool.elproyectegrande1.controller;

import com.codecool.elproyectegrande1.email.EmailServiceImpl;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/v1/test")
public class TestController {
    private final EmailServiceImpl emailServiceImpl;

    public TestController(EmailServiceImpl emailServiceImpl) {
        this.emailServiceImpl = emailServiceImpl;
    }

    @GetMapping("/all")
    public String allAccess() {
        return "Public Content.";
    }

    @GetMapping("/dreamer")
    @PreAuthorize("hasRole('DREAMER') or hasRole('MENTOR') or hasRole('ADMIN')")
    public String userAccess() {
        return "Dreamer Content.";
    }

    @GetMapping("/mentor")
    @PreAuthorize("hasRole('MENTOR')")
    public String moderatorAccess() {
        return "Mentor Board.";
    }

    @GetMapping("/admin")
    @PreAuthorize("hasRole('ADMIN')")
    public String adminAccess() {
        return "Admin Board.";
    }

    @PostMapping("/send-test-mail")
    public void sendTestEmail() {
        emailServiceImpl.sendSimpleMessage("txbuiduc@gmail.com", "hello", "Hi, how are you?");
    }
}
