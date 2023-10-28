package com.codecool.elproyectegrande1.controller;

import com.codecool.elproyectegrande1.dto.user.UserDto;
import com.codecool.elproyectegrande1.jwt.JwtUtils;
import com.codecool.elproyectegrande1.security.oauth2.CurrentUser;
import com.codecool.elproyectegrande1.security.oauth2.UserPrincipal;
import com.codecool.elproyectegrande1.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;

@CrossOrigin(origins = "https://dreams-catcher.onrender.com/, http://localhost:8081/")
@RestController
@RequestMapping("/api/v1/users")
public class UserController {

    public static final String DREAMER = "ROLE_DREAMER";
    private final UserService userService;
    private final AuthenticationManager authenticationManager;
    private final JwtUtils jwtUtils;


    public UserController(UserService userService, AuthenticationManager authenticationManager, JwtUtils jwtUtils) {
        this.userService = userService;
        this.authenticationManager = authenticationManager;
        this.jwtUtils = jwtUtils;
    }

    @ResponseStatus(value = HttpStatus.OK)
    @PostMapping(value="/upload/avatar", produces = MediaType.MULTIPART_FORM_DATA_VALUE)
    public void uploadImage(@RequestParam("image") MultipartFile file) throws IOException {
        userService.uploadAvatar(file);
    }

    @GetMapping(value ="/avatar/{id}", produces = {MediaType.IMAGE_PNG_VALUE, MediaType.IMAGE_JPEG_VALUE})
    @ResponseBody
    public String showAvatar(@PathVariable("id") Long id) {
        byte[] avatar = userService.getAvatarById(id);
        String encodedString = Base64.getEncoder().encodeToString(avatar);
        return encodedString;
    }

    @GetMapping("/profile")
    public UserDto getCurrentUser(@CurrentUser UserPrincipal userPrincipal){
        UserDto user = userService.findUserDtoById(userPrincipal.getId());
        List<String> roles = new ArrayList<>();
        roles.add(DREAMER);
        user.setRoles(roles);
        return user;
    }

    @GetMapping("/profile-auth/{id}")
    public UserDto getCurrentUserAuth(@PathVariable Long id) {
        return userService.findUserByUserId(id);
    }
}
