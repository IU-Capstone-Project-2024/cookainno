package org.innopolis.cookainno.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.Valid;
import org.innopolis.cookainno.dto.SaveUserInfoRequest;
import org.innopolis.cookainno.dto.SaveUserInfoResponse;
import org.innopolis.cookainno.exception.model.APIErrorResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

@RequestMapping("/user")
public interface UserInfoAPI {

    @Operation(summary = "Update user info. Accessible only to authorized users")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "User info updated successfully", content = @Content(schema = @Schema(implementation = SaveUserInfoResponse.class))),
            @ApiResponse(responseCode = "400", description = "Request was malformed", content = @Content(schema = @Schema(implementation = APIErrorResponse.class))),
            @ApiResponse(responseCode = "404", description = "User not found", content = @Content(schema = @Schema(implementation = APIErrorResponse.class)))
    })
    @PutMapping
    ResponseEntity<SaveUserInfoResponse> saveUserInfo(@RequestBody @Valid SaveUserInfoRequest request, BindingResult bindingResult);
}