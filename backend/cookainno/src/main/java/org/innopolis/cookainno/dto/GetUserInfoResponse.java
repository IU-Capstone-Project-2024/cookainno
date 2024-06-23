package org.innopolis.cookainno.dto;

public record GetUserInfoResponse(
        Long id,
        String username,
        String email,
        int height,
        int weight,
        int age
) {
}