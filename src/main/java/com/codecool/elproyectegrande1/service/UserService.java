package com.codecool.elproyectegrande1.service;

import com.codecool.elproyectegrande1.config.AppConfig;
import com.codecool.elproyectegrande1.dto.user.UserDto;
import com.codecool.elproyectegrande1.entity.*;
import com.codecool.elproyectegrande1.jwt.payload.request.LoginRequest;
import com.codecool.elproyectegrande1.jwt.payload.request.SignupRequest;
import com.codecool.elproyectegrande1.jwt.payload.response.MessageResponse;
import com.codecool.elproyectegrande1.mapper.UserMapper;
import com.codecool.elproyectegrande1.repository.AvatarRepository;
import com.codecool.elproyectegrande1.repository.ConfirmationTokenRepository;
import com.codecool.elproyectegrande1.repository.MentorRepository;
import com.codecool.elproyectegrande1.repository.UserRepository;
import com.codecool.elproyectegrande1.security.oauth2.UserPrincipal;
import com.codecool.elproyectegrande1.service.exceptions.ResourceNotFoundException;
import com.codecool.elproyectegrande1.util.ImageUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.core.oidc.user.OidcUser;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Collections;
import java.util.Map;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final AvatarRepository avatarRepository;
    private final UserMapper userMapper;
    private final MentorRepository mentorRepository;
    private final ConfirmationTokenRepository confirmationTokenRepository;
    private final EmailSenderService emailSenderService;
    private final PasswordEncoder encoder;
    private final AppConfig appConfig;

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

    public void sendConfirmationEmail(User user) {
        ConfirmationToken confirmationToken = new ConfirmationToken(user);

        confirmationTokenRepository.save(confirmationToken);

        SimpleMailMessage mailMessage = new SimpleMailMessage();
        mailMessage.setTo(user.getEmail());
        mailMessage.setSubject("Complete Registration!");
        mailMessage.setFrom("admin@dreamscatcher.com");
        mailMessage.setText("To complete the password reset process, please click here: "
                + appConfig.getPasswordResetUrl() + "confirm-reset?token=" + confirmationToken.getConfirmationToken());

        emailSenderService.sendEmail(mailMessage);
    }

    public void sendResetPasswordEmail(String email) {
        User user = userRepository.findByEmail(email).orElse(null);
        if(user != null) {
            // create token
            ConfirmationToken confirmationToken = new ConfirmationToken(user);

            // save it
            confirmationTokenRepository.save(confirmationToken);

            // create the email
            SimpleMailMessage mailMessage = new SimpleMailMessage();
            mailMessage.setTo(user.getEmail());
            mailMessage.setSubject("Complete Password Reset!");
            mailMessage.setFrom("admin@dreamscatcher.com");
            mailMessage.setText("To complete the password reset process, please click here: "
                    + appConfig.getPasswordResetUrl() + "confirm-reset?token=" + confirmationToken.getConfirmationToken());

            emailSenderService.sendEmail(mailMessage);
        }
    }

    public User findUserByEmailAndChangePassword(String email, String password) {
        User user = userRepository.findByEmail(email).orElseThrow();
        user.setPassword(encoder.encode(password));
        User savedUser = userRepository.save(user);
        return savedUser;
    }

    public void sendEmail() {
        // create the email
        SimpleMailMessage mailMessage = new SimpleMailMessage();
        mailMessage.setTo("trungusek@yahoo.com");
        mailMessage.setSubject("Complete Password Reset!");
        mailMessage.setFrom("admin@dreamscatcher.com");
        mailMessage.setText("To complete the password reset process, please click here");

        emailSenderService.sendEmail(mailMessage);
    }
}
