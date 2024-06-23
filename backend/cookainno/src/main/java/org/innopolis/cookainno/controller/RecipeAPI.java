package org.innopolis.cookainno.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.innopolis.cookainno.dto.AddRecipeRequest;
import org.innopolis.cookainno.dto.RecipeResponse;
import org.innopolis.cookainno.dto.UpdateRecipeRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequestMapping("/recipes")
@Tag(name = "Recipes")
public interface RecipeAPI {
    @Operation(summary = "Add a new recipe")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Recipe added successfully"),
            @ApiResponse(responseCode = "400", description = "Invalid request")
    })
    @PostMapping()
    ResponseEntity<RecipeResponse> addRecipe(@RequestBody AddRecipeRequest request);

    @Operation(summary = "Update a recipe by ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Recipe updated successfully"),
            @ApiResponse(responseCode = "400", description = "Invalid request"),
            @ApiResponse(responseCode = "404", description = "Recipe not found")
    })
    @PutMapping()
    ResponseEntity<RecipeResponse> updateRecipe(
            @RequestBody UpdateRecipeRequest request);

    @Operation(summary = "Get a recipe by ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Recipe retrieved successfully"),
            @ApiResponse(responseCode = "404", description = "Recipe not found")
    })
    @GetMapping("/{id}")
    ResponseEntity<RecipeResponse> getRecipeById(@PathVariable Long id);

    @Operation(summary = "Delete a recipe by ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Recipe deleted successfully"),
            @ApiResponse(responseCode = "404", description = "Recipe not found")
    })
    @DeleteMapping("/{id}")
    ResponseEntity<Void> deleteRecipe(@PathVariable Long id);

    @Operation(summary = "Search recipes by name with pagination")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Recipes retrieved successfully")
    })
    @GetMapping("/search")
    ResponseEntity<List<RecipeResponse>> searchRecipesByName(
            @RequestParam String name,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size);

    @Operation(summary = "Get recipes sorted by likes with pagination")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Recipes retrieved successfully")
    })
    @GetMapping("/sorted-by-likes")
    ResponseEntity<List<RecipeResponse>> getRecipesSortedByLikes(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size);

    @Operation(summary = "Add recipe to favorites for a user")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Recipe added to favorites successfully"),
            @ApiResponse(responseCode = "400", description = "Invalid request"),
            @ApiResponse(responseCode = "404", description = "Recipe or User not found")
    })
    @PostMapping("/{userId}/favorites/{recipeId}")
    ResponseEntity<Void> addRecipeToFavorites(
            @PathVariable Long userId,
            @PathVariable Long recipeId);

    @Operation(summary = "Get paged favorite recipes for a user")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Favorite recipes retrieved successfully"),
            @ApiResponse(responseCode = "400", description = "Invalid request"),
            @ApiResponse(responseCode = "404", description = "User not found")
    })
    @GetMapping("/{userId}/favorites")
    ResponseEntity<List<RecipeResponse>> getFavoriteRecipes(
            @PathVariable Long userId,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "true") boolean oldestFirst);

    @Operation(summary = "Search user's favorite recipes by name with pagination")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Favorite recipes retrieved successfully")
    })
    @GetMapping("/{userId}/favorites/search")
    ResponseEntity<List<RecipeResponse>> searchFavoriteRecipesByName(
            @PathVariable Long userId,
            @RequestParam String name,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size);

    @Operation(summary = "Remove recipe from favorites for a user")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Recipe removed from favorites successfully"),
            @ApiResponse(responseCode = "404", description = "Recipe or User not found")
    })
    @DeleteMapping("/{userId}/favorites/{recipeId}")
    ResponseEntity<Void> removeRecipeFromFavorites(
            @PathVariable Long userId,
            @PathVariable Long recipeId);
}