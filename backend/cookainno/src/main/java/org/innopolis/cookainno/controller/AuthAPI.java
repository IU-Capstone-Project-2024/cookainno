package org.innopolis.cookainno.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.mail.MessagingException;
import jakarta.validation.Valid;
import org.innopolis.cookainno.dto.ConfirmEmailRequest;
import org.innopolis.cookainno.dto.JwtAuthenticationResponse;
import org.innopolis.cookainno.dto.SignInRequest;
import org.innopolis.cookainno.dto.SignUpRequest;
import org.innopolis.cookainno.exception.model.APIErrorResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

@RequestMapping("/auth")
@Tag(name = "Authentication")
public interface AuthAPI {

    @Operation(summary = "User Registration")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "User registered successfully", content = @Content(schema = @Schema(implementation = JwtAuthenticationResponse.class))),
            @ApiResponse(responseCode = "400", description = "Request was malformed", content = @Content(schema = @Schema(implementation = APIErrorResponse.class))),
            @ApiResponse(responseCode = "409", description = "User already exists", content = @Content(schema = @Schema(implementation = APIErrorResponse.class)))
    })
    @PostMapping("/sign-up")
    ResponseEntity<JwtAuthenticationResponse> signUp(@RequestBody @Valid SignUpRequest request, BindingResult bindingResult) throws MessagingException;

    @Operation(summary = "Confirm User Email")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Email confirmed successfully", content = @Content(schema = @Schema(implementation = String.class))),
            @ApiResponse(responseCode = "400", description = "Invalid confirmation code", content = @Content(schema = @Schema(implementation = APIErrorResponse.class))),
            @ApiResponse(responseCode = "404", description = "User not found", content = @Content(schema = @Schema(implementation = APIErrorResponse.class)))
    })
    @PostMapping("/confirm-email")
    ResponseEntity<String> confirmEmail(@RequestBody @Valid ConfirmEmailRequest request, BindingResult bindingResult);

    @Operation(summary = "User Authentication")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "User authenticated successfully", content = @Content(schema = @Schema(implementation = JwtAuthenticationResponse.class))),
            @ApiResponse(responseCode = "400", description = "Invalid credentials", content = @Content(schema = @Schema(implementation = APIErrorResponse.class))),
            @ApiResponse(responseCode = "403", description = "Email not confirmed", content = @Content(schema = @Schema(implementation = APIErrorResponse.class))),
            @ApiResponse(responseCode = "404", description = "User not found", content = @Content(schema = @Schema(implementation = APIErrorResponse.class)))
    })
    @PostMapping("/sign-in")
    ResponseEntity<JwtAuthenticationResponse> signIn(@RequestBody @Valid SignInRequest request, BindingResult bindingResult);
}
