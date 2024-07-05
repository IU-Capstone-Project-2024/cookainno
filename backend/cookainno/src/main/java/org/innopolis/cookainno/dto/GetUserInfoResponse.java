package org.innopolis.cookainno.dto;

import java.time.LocalDate;

public record GetUserInfoResponse(
        Long id,
        String username,
        String email,
        Integer height,
        Integer weight,
        LocalDate dateOfBirth
) {
}