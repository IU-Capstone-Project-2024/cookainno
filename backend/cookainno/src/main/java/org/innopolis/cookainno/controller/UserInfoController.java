package org.innopolis.cookainno.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.innopolis.cookainno.dto.GetUserInfoResponse;
import org.innopolis.cookainno.dto.SaveUserInfoRequest;
import org.innopolis.cookainno.dto.SaveUserInfoResponse;
import org.innopolis.cookainno.service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
public class UserInfoController implements UserInfoAPI {

    private final UserService service;

    @Override
    public ResponseEntity<GetUserInfoResponse> getUserInfoById(@PathVariable("id") Long id) {
        GetUserInfoResponse response = service.getUserInfoById(id);
        return ResponseEntity.ok(response);
    }

    @Override
    public ResponseEntity<SaveUserInfoResponse> saveUserInfo(@RequestBody @Valid SaveUserInfoRequest request, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return ResponseEntity.badRequest().build();
        }

        SaveUserInfoResponse response = service.updateUserInfo(request);
        return ResponseEntity.ok(response);
    }

    @Override
    public ResponseEntity<Void> deleteUserById(@PathVariable("id") Long id) {
        service.deleteUserById(id);
        return ResponseEntity.ok().build();
    }
}