package org.innopolis.cookainno.service;

import jakarta.mail.MessagingException;
import lombok.RequiredArgsConstructor;
import org.innopolis.cookainno.dto.ConfirmEmailRequest;
import org.innopolis.cookainno.dto.JwtAuthenticationResponse;
import org.innopolis.cookainno.dto.SignInRequest;
import org.innopolis.cookainno.dto.SignUpRequest;
import org.innopolis.cookainno.entity.Role;
import org.innopolis.cookainno.entity.User;
import org.innopolis.cookainno.exception.EmailNotConfirmedException;
import org.innopolis.cookainno.exception.InvalidConfirmationCodeException;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Random;

@Service
@RequiredArgsConstructor
public class AuthenticationService {
    private final UserService userService;
    private final JwtService jwtService;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;
    private final EmailService emailService;

    /**
     * Регистрация пользователя с отправкой кода на почту
     *
     * @param request данные пользователя
     * @return сообщение о проверке кода на почту
     */
    public JwtAuthenticationResponse signUp(SignUpRequest request) throws MessagingException {
        var user = User.builder()
                .username(request.getUsername())
                .email(request.getEmail())
                .password(passwordEncoder.encode(request.getPassword()))
                .role(Role.ROLE_USER)
                .isEnabled(false)
                .confirmationCode(String.format("%04d", new Random().nextInt(10000)))
                .build();

        var userSaved = userService.create(user);

        emailService.sendConfirmationEmail(user.getEmail(), user.getConfirmationCode());

        return new JwtAuthenticationResponse("User registered successfully. Please check your email for the confirmation code.", userSaved.getId());
    }

    /**
     * Регистрация пользователя без кода
     *
     * @param request данные пользователя
     * @return jwt
     */
    public JwtAuthenticationResponse signUp_Без_СМС_и_Регистрации(SignUpRequest request) {
        var user = User.builder()
                .username(request.getUsername())
                .email(request.getEmail())
                .password(passwordEncoder.encode(request.getPassword()))
                .role(Role.ROLE_USER)
                .isEnabled(false)
                .confirmationCode(String.format("%04d", new Random().nextInt(10000)))
                .build();

        var userSaved = userService.create(user);

        var jwt = jwtService.generateToken(user);
        return new JwtAuthenticationResponse(jwt, userSaved.getId());
    }

    /**
     * Подтверждения пользователя по коду
     *
     * @param request данные почты и проверочного кода
     */
    public void confirmEmail(ConfirmEmailRequest request) {
        User user = userService.getByEmail(request.getEmail());

        if (user.getConfirmationCode().equals(request.getConfirmationCode())) {
            user.setEnabled(true);
            user.setConfirmationCode(null);
            userService.save(user);
        } else {
            throw new InvalidConfirmationCodeException("Invalid confirmation code");
        }
    }

    /**
     * Аутентификация пользователя
     *
     * @param request данные пользователя
     * @return токен
     */
    public JwtAuthenticationResponse signIn(SignInRequest request) {

        var user = userService
                .userDetailsService()
                .loadUserByUsername(request.getUsername());

        if (!user.isEnabled()) {
            throw new EmailNotConfirmedException("Email not confirmed");
        }
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
                request.getUsername(),
                request.getPassword()
        ));

        var jwt = jwtService.generateToken(user);
        return new JwtAuthenticationResponse(jwt, ((User) user).getId());
    }
}