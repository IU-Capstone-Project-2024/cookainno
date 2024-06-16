package org.innopolis.cookainno.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.innopolis.cookainno.dto.SaveUserInfoRequest;
import org.innopolis.cookainno.dto.SaveUserInfoResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

@RequestMapping("/users")
@Tag(name = "UserInfo")
public interface UserInfoAPI {

    @Operation(summary = "Update user info. Accessible only to authorized users", security = @SecurityRequirement(name = "bearerAuth"))
    @PreAuthorize("isAuthenticated()")
    @PutMapping
    ResponseEntity<SaveUserInfoResponse> saveUserInfo(@RequestBody @Valid SaveUserInfoRequest request, BindingResult bindingResult);
}
