package org.innopolis.cookainno.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.innopolis.cookainno.service.UserService;

@RestController
@RequestMapping("/example")
@RequiredArgsConstructor
@Tag(name = "Authentication")
public class ExampleController implements ExampleAPI{
    private final UserService service;

    @GetMapping
    @Operation(summary = "Accessible only to authorized users")
    public ResponseEntity<String> example() {
        return ResponseEntity.ok("Привет, гениальным андроид developerам!");
    }
    @GetMapping("/admin")
    @Operation(summary = "Доступен только авторизованным пользователям с ролью ADMIN")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<String> exampleAdmin() {
        return ResponseEntity.ok("Hello, admin!");
    }

    @GetMapping("/get-admin")
    @Operation(summary = "Получить роль ADMIN (для демонстрации)")
    public ResponseEntity<Void> getAdmin() {
        service.getAdmin();
        return ResponseEntity.noContent().build();
    }
}
