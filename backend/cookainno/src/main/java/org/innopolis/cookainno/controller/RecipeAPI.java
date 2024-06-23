package org.innopolis.cookainno.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.innopolis.cookainno.dto.AddRecipeRequest;
import org.innopolis.cookainno.dto.RecipeResponse;
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
    @PostMapping("/add")
    ResponseEntity<RecipeResponse> addRecipe(@RequestBody AddRecipeRequest request);

    @Operation(summary = "Update a recipe by ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Recipe updated successfully"),
            @ApiResponse(responseCode = "400", description = "Invalid request"),
            @ApiResponse(responseCode = "404", description = "Recipe not found")
    })
    @PutMapping("/{id}/update")
    ResponseEntity<RecipeResponse> updateRecipe(
            @PathVariable Long id,
            @RequestBody AddRecipeRequest request);

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
    @DeleteMapping("/{id}/delete")
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
}