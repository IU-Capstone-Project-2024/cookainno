// UserInfoController.java
package org.innopolis.cookainno.controller;

import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.innopolis.cookainno.dto.SaveUserInfoRequest;
import org.innopolis.cookainno.dto.SaveUserInfoResponse;
import org.innopolis.cookainno.service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/user")
@RequiredArgsConstructor
@Tag(name = "UserInfo")
public class UserInfoController implements UserInfoAPI {

    private final UserService service;

    @Override
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<SaveUserInfoResponse> saveUserInfo(@RequestBody @Valid SaveUserInfoRequest request, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return ResponseEntity.badRequest().build();
        }

        SaveUserInfoResponse response = service.updateUserInfo(request);
        return ResponseEntity.ok(response);
    }
}