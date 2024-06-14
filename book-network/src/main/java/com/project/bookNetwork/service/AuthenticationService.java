package com.project.bookNetwork.service;

import com.project.bookNetwork.dao.entity.Token;
import com.project.bookNetwork.dao.entity.User;
import com.project.bookNetwork.dao.repository.RoleRepository;
import com.project.bookNetwork.dao.repository.TokenRepository;
import com.project.bookNetwork.dao.repository.UserRepository;
import com.project.bookNetwork.dto.EmailTemplateName;
import com.project.bookNetwork.dto.RegisterRequest;
import jakarta.mail.MessagingException;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.security.SecureRandom;
import java.time.LocalDateTime;
import java.util.List;


/**
 * @author fares.belaid
 * 14/06/2024
 */

@Service
@RequiredArgsConstructor
public class AuthenticationService {

    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;
    private final UserRepository userRepository;
    private final TokenRepository tokenRepository;
    private final EmailService emailService;
    @Value("${application.security.mailing.frontend.activation-url}")
    private String activationUrl;

    public void register(RegisterRequest registerRequest) throws MessagingException {
        var userRole = roleRepository.findByName("USER")
                .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
        var user = User.builder()
                .firstName(registerRequest.getFirstname())
                .lastName(registerRequest.getLastname())
                .email(registerRequest.getEmail())
                .password(passwordEncoder.encode(registerRequest.getPassword()))
                .enabled(false)
                .accountLocked(false)
                .roles(List.of(userRole))
                .build();
        userRepository.save(user);
        sendValidationEmail(user);
    }

    private void sendValidationEmail(User user) throws MessagingException {
        var newToken = generateAndSaveActivationToken(user);
        emailService.sendEmail(
                user.getEmail(),
                user.fullName(),
                EmailTemplateName.ACTIVATE_ACCOUNT,
                activationUrl,
                newToken,
                "Activate your account"
        );


    }

    private String generateAndSaveActivationToken(User user) {
        // generate token
        String generatedToken = generateActivationCode(6);
        var token = Token.builder()
                .token(generatedToken)
                .user(user)
                .createdAt(LocalDateTime.now())
                .expiresAt(LocalDateTime.now().plusMinutes(15))
                .build();
        // save token
         tokenRepository.save(token);
         return generatedToken;
    }

    private String generateActivationCode(int length) {
        String numbers = "0123456789";
        StringBuilder codeBuilder = new StringBuilder();
        SecureRandom secureRandom = new SecureRandom();
        for (int i = 0; i < length; i++){
            int randomIndex = secureRandom.nextInt(numbers.length());
            codeBuilder.append(numbers.charAt(randomIndex));
        }
        return codeBuilder.toString();
    }
}
