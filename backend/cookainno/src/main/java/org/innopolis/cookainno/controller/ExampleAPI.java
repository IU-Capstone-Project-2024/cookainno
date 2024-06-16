package org.innopolis.cookainno.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@RequestMapping("/example")
@Tag(name = "Example")
public interface ExampleAPI {

    @Operation(summary = "Accessible only to authorized users", security = @SecurityRequirement(name = "bearerAuth"))
    @GetMapping(produces = "application/json", headers = "Auth")
    ResponseEntity<String> example();

    @Operation(summary = "Accessible only to authorized users with ADMIN role", security = @SecurityRequirement(name = "bearerAuth"))
    @GetMapping(value = "/admin", produces = "application/json", headers = "Auth")
    ResponseEntity<String> exampleAdmin();

    @Operation(summary = "Get ADMIN role (for demonstration purposes)", security = @SecurityRequirement(name = "bearerAuth"))
    @GetMapping(value = "/get-admin", produces = "application/json", headers = "Auth")
    ResponseEntity<Void> getAdmin();
}
