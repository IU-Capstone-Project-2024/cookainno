package org.innopolis.cookainno.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record AddFavouriteRecipeRequest(
        @NotNull Long userId,
        @NotBlank String name,
        @NotBlank String instructions
) {
}