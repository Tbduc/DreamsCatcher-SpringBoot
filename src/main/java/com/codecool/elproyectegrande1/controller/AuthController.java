package com.codecool.elproyectegrande1.controller;

import com.codecool.elproyectegrande1.entity.Role;
import com.codecool.elproyectegrande1.entity.User;
import com.codecool.elproyectegrande1.entity.UserDetailsImpl;
import com.codecool.elproyectegrande1.jwt.JwtUtils;
import com.codecool.elproyectegrande1.jwt.payload.request.LoginRequest;
import com.codecool.elproyectegrande1.jwt.payload.request.SignupRequest;
import com.codecool.elproyectegrande1.jwt.payload.response.JwtResponse;
import com.codecool.elproyectegrande1.jwt.payload.response.MessageResponse;
import com.codecool.elproyectegrande1.repository.RoleRepository;
import com.codecool.elproyectegrande1.repository.UserRepository;
import com.codecool.elproyectegrande1.service.DreamerService;
import com.codecool.elproyectegrande1.service.MentorService;
import com.codecool.elproyectegrande1.service.RoleService;
import com.codecool.elproyectegrande1.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;


@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/v1/auth")
public class AuthController {
    @Autowired
    AuthenticationManager authenticationManager;

    @Autowired
    UserRepository userRepository;

    @Autowired
    RoleRepository roleRepository;

    @Autowired
    PasswordEncoder encoder;

    @Autowired
    JwtUtils jwtUtils;

    private final DreamerService dreamerService;
    private final MentorService mentorService;
    private final UserService userService;
    private final RoleService roleService;

    private final String DREAMER = "dreamer";
    private final String MENTOR = "mentor";
    private final String ADMIN = "admin";


    @Autowired
    public AuthController(DreamerService dreamerService, MentorService mentorService, UserService userService, RoleService roleService) {
        this.dreamerService = dreamerService;
        this.mentorService = mentorService;
        this.userService = userService;
        this.roleService = roleService;
    }

    @PostMapping("/signin")
    public ResponseEntity<?> authenticateUser(@Valid @RequestBody LoginRequest loginRequest, HttpSession session) {

        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(loginRequest.getUsername(), loginRequest.getPassword()));

        SecurityContextHolder.getContext().setAuthentication(authentication);
        String jwt = jwtUtils.generateJwtToken(authentication);
        userService.findUserIfExists(loginRequest);

        UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
        List<String> roles = userDetails.getAuthorities().stream()
                .map(item -> item.getAuthority())
                .collect(Collectors.toList());

        session.setAttribute("id", userDetails.getId());

        return ResponseEntity.ok(new JwtResponse(jwt,
                userDetails.getId(),
                userDetails.getUsername(),
                userDetails.getEmail(),
                roles,
                userDetails.getProfilePictureId(),
                userDetails.getImageUrl()));
    }

    // Refactor it in order to create ConfirmationToken to restore forgotten password
    @PostMapping("/signup")
    public ResponseEntity<?> registerUser(@Valid @RequestBody SignupRequest signUpRequest) {
        userService.getMessageResponseResponseEntity(signUpRequest);

        User user = new User(signUpRequest.getUsername(),
                signUpRequest.getEmail(),
                encoder.encode(signUpRequest.getPassword()));

        Set<String> strRoles = signUpRequest.getRole();
        Set<Role> roles = new HashSet<>();
        roleService.findRoleAndAddToRoles(strRoles, roles);

        for (String entry:strRoles) {
            if (!DREAMER.equals(entry) && !MENTOR.equals(entry) && !ADMIN.equals(entry)) {
                return ResponseEntity.internalServerError().body(new MessageResponse("There's no such role."));
            }
        }

        if (strRoles.contains(DREAMER))
            dreamerService.createDreamerFromUser(user, roles);
        if (strRoles.contains(MENTOR))
            mentorService.createMentorFromUser(user, roles);

        return ResponseEntity.ok(new MessageResponse("User registered successfully!"));
    }
}