package org.innopolis.cookainno.controller;

import jakarta.mail.MessagingException;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.innopolis.cookainno.dto.ConfirmEmailRequest;
import org.innopolis.cookainno.dto.JwtAuthenticationResponse;
import org.innopolis.cookainno.dto.SignInRequest;
import org.innopolis.cookainno.dto.SignUpRequest;
import org.innopolis.cookainno.exception.ValidationException;
import org.innopolis.cookainno.service.AuthenticationService;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class AuthController implements AuthAPI {
    private final AuthenticationService authenticationService;

    @Override
    public ResponseEntity<JwtAuthenticationResponse> signUp(@RequestBody @Valid SignUpRequest request, BindingResult bindingResult) throws MessagingException {
        if (bindingResult.hasErrors()) {
            List<FieldError> errors = bindingResult.getFieldErrors();
            throw new ValidationException(errors.getFirst());
        }
        return ResponseEntity.ok(authenticationService.signUp(request));
    }

    @Override
    public ResponseEntity<String> confirmEmail(@RequestBody @Valid ConfirmEmailRequest request, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            throw new ValidationException(bindingResult.getFieldErrors().getFirst());
        }
        authenticationService.confirmEmail(request);
        return ResponseEntity.ok("Email confirmed successfully. You can now sign in.");
    }

    @Override
    public ResponseEntity<JwtAuthenticationResponse> signIn(@RequestBody @Valid SignInRequest request, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            throw new ValidationException(bindingResult.getFieldErrors().getFirst());
        }

        return ResponseEntity.ok(authenticationService.signIn(request));
    }
}
