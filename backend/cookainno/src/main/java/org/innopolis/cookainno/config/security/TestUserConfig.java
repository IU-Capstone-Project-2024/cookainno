package org.innopolis.cookainno.config.security;

import jakarta.annotation.PostConstruct;
import jakarta.annotation.PreDestroy;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.innopolis.cookainno.entity.Role;
import org.innopolis.cookainno.entity.User;
import org.innopolis.cookainno.service.JwtService;
import org.innopolis.cookainno.service.UserService;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Optional;

@Configuration
@RequiredArgsConstructor
@Slf4j
public class TestUserConfig {
    private final UserService userService;
    private final JwtService jwtService;
    private final PasswordEncoder passwordEncoder;
    String testUsername = "testUser";
    String testPassword = "testPassword";

    @PostConstruct
    public void createTestUser() {
        String testEmail = "testUser@example.com";

        var user = User.builder()
                .username(testUsername)
                .email(testEmail)
                .password(passwordEncoder.encode(testPassword))
                .role(Role.ROLE_USER)
                .isEnabled(true)
                .build();
        userService.create(user);


        // generate JWT token
        var userDetails = userService.userDetailsService().loadUserByUsername(testUsername);
        var jwt = jwtService.generateToken(userDetails);

        log.info("Test JWT Token: " + jwt);
    }

    @PreDestroy
    public void deleteTestUser() {
        Optional<User> user = userService.findByUsername(testUsername);
        user.ifPresent(userService::delete);
        log.info("Test user deleted: " + testUsername);
    }
}
