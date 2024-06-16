package org.innopolis.cookainno.dto;

import java.time.LocalDateTime;

public record FavouriteRecipeResponse(
        Long id,
        Long userId,
        String name,
        String instructions,
        LocalDateTime dateAdded
) {
}
