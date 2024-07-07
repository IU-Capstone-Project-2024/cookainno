package org.innopolis.cookainno.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@AllArgsConstructor
public class UpdateRecipeRequest {
    private Long id;
    private String name;
    private String instructions;
    private String ingredients;
    private String imageUrl;
}
