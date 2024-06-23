package org.innopolis.cookainno.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class UpdateRecipeRequest {
    private Long id;
    private String name;
    private String instructions;
    private String ingredients;
    private String imageUrl;
}
