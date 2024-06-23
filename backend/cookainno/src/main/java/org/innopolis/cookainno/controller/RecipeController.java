package org.innopolis.cookainno.controller;

import lombok.RequiredArgsConstructor;
import org.innopolis.cookainno.dto.AddRecipeRequest;
import org.innopolis.cookainno.dto.RecipeResponse;
import org.innopolis.cookainno.service.RecipeService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class RecipeController implements RecipeAPI {
    private final RecipeService recipeService;

    @Override
    public ResponseEntity<RecipeResponse> addRecipe(@RequestBody AddRecipeRequest request) {
        RecipeResponse response = recipeService.addRecipe(request);
        return ResponseEntity.ok(response);
    }

    @Override
    public ResponseEntity<Void> deleteRecipe(@PathVariable Long id) {
        recipeService.deleteRecipe(id);
        return ResponseEntity.noContent().build();
    }

    @Override
    public ResponseEntity<List<RecipeResponse>> searchRecipesByName(
            @RequestParam String name,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        List<RecipeResponse> responses = recipeService.searchRecipesByName(name, page, size);
        return ResponseEntity.ok(responses);
    }

    @Override
    public ResponseEntity<List<RecipeResponse>> getRecipesSortedByLikes(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        List<RecipeResponse> responses = recipeService.getRecipesSortedByLikes(page, size);
        return ResponseEntity.ok(responses);
    }
}
