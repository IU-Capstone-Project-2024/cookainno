package org.innopolis.cookainno.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record UpdateFavouriteRecipeRequest(
        @NotNull Long recipeId,
        @NotBlank String name,
        @NotBlank String instructions
) {
}
