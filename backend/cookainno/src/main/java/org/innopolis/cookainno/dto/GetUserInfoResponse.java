package org.innopolis.cookainno.dto;

import java.time.LocalDate;

public record GetUserInfoResponse(
        Long id,
        String username,
        String email,
        int height,
        int weight,
        int age
) {
}