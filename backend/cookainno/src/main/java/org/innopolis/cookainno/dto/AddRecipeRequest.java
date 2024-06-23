package org.innopolis.cookainno.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class AddRecipeRequest {
    private String name;
    private String instructions;
    private String ingredients;
}