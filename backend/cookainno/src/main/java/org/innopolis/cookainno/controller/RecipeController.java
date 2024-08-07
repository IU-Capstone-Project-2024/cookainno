package org.innopolis.cookainno.controller;

import lombok.RequiredArgsConstructor;
import org.innopolis.cookainno.dto.AddRecipeRequest;
import org.innopolis.cookainno.dto.RecipeResponse;
import org.innopolis.cookainno.dto.UpdateRecipeRequest;
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
    public ResponseEntity<RecipeResponse> updateRecipe(
            @RequestBody UpdateRecipeRequest request) {
        RecipeResponse response = recipeService.updateRecipe(request);
        return ResponseEntity.ok(response);
    }

    @Override
    public ResponseEntity<RecipeResponse> getRecipeById(@PathVariable Long id) {
        RecipeResponse response = recipeService.getRecipeById(id);
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

    @Override
    public ResponseEntity<Void> addRecipeToFavorites(Long userId, Long recipeId) {
        recipeService.addRecipeToFavorites(userId, recipeId);
        return ResponseEntity.ok().build();
    }

    @Override
    public ResponseEntity<List<RecipeResponse>> getFavoriteRecipes(Long userId, int page, int size, boolean oldestFirst) {
        List<RecipeResponse> recipes = recipeService.getFavoriteRecipes(userId, page, size, oldestFirst);
        return ResponseEntity.ok(recipes);
    }

    @Override
    public ResponseEntity<Void> removeRecipeFromFavorites(Long userId, Long recipeId) {
        recipeService.removeRecipeFromFavorites(userId, recipeId);
        return ResponseEntity.noContent().build();
    }

    @Override
    public ResponseEntity<List<RecipeResponse>> searchFavoriteRecipesByName(Long userId, String name, int page, int size) {
        List<RecipeResponse> recipes = recipeService.searchFavoriteRecipesByName(userId, name, page, size);
        return ResponseEntity.ok(recipes);
    }
}
