package org.innopolis.cookainno.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.innopolis.cookainno.dto.AddFavouriteRecipeRequest;
import org.innopolis.cookainno.dto.FavouriteRecipeResponse;
import org.innopolis.cookainno.dto.UpdateFavouriteRecipeRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequestMapping("/favourite-recipes")
@Tag(name = "Favourite Recipes")
public interface FavouriteRecipeAPI {

    @Operation(summary = "Add a favourite recipe")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Recipe added successfully"),
            @ApiResponse(responseCode = "404", description = "User not found")
    })
    @PostMapping
    ResponseEntity<FavouriteRecipeResponse> addFavouriteRecipe(@RequestBody AddFavouriteRecipeRequest request);

    @Operation(summary = "Delete a favourite recipe")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Recipe deleted successfully"),
            @ApiResponse(responseCode = "404", description = "Recipe not found")
    })
    @DeleteMapping("/{recipeId}")
    ResponseEntity<Void> deleteFavouriteRecipe(@PathVariable Long recipeId);

    @Operation(summary = "Update a favourite recipe")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Recipe updated successfully"),
            @ApiResponse(responseCode = "404", description = "Recipe not found")
    })
    @PutMapping
    ResponseEntity<FavouriteRecipeResponse> updateFavouriteRecipe(@RequestBody UpdateFavouriteRecipeRequest request);

    @Operation(summary = "Get favourite recipes by user ID with pagination")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Recipes retrieved successfully"),
            @ApiResponse(responseCode = "404", description = "User not found")
    })
    @GetMapping("/user/{userId}")
    ResponseEntity<List<FavouriteRecipeResponse>> getFavouriteRecipesByUserId(
            @PathVariable Long userId,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size
    );

    @Operation(summary = "Search favourite recipes by user ID and name with pagination")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Recipes retrieved successfully")
    })
    @GetMapping("/search")
    ResponseEntity<List<FavouriteRecipeResponse>> searchFavouriteRecipesByName(
            @RequestParam Long userId,
            @RequestParam String name,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size
    );
}