package org.innopolis.cookainno.controller;

import lombok.RequiredArgsConstructor;
import org.innopolis.cookainno.service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/example")
@RequiredArgsConstructor
public class ExampleController implements ExampleAPI {
    private final UserService service;

    @GetMapping
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<String> example() {
        return ResponseEntity.ok("Привет, Гениальные андроид developers!");
    }

    @GetMapping("/admin")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<String> exampleAdmin() {
        return ResponseEntity.ok("Hello, admin!");
    }

    @GetMapping("/get-admin")
    public ResponseEntity<Void> getAdmin() {
        service.getAdmin();
        return ResponseEntity.noContent().build();
    }
}
