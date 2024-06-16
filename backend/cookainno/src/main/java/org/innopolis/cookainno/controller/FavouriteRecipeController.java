package org.innopolis.cookainno.controller;

import lombok.RequiredArgsConstructor;
import org.innopolis.cookainno.dto.AddFavouriteRecipeRequest;
import org.innopolis.cookainno.dto.FavouriteRecipeResponse;
import org.innopolis.cookainno.dto.UpdateFavouriteRecipeRequest;
import org.innopolis.cookainno.service.FavouriteRecipeService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class FavouriteRecipeController implements FavouriteRecipeAPI {
    private final FavouriteRecipeService favouriteRecipeService;

    @Override
    public ResponseEntity<FavouriteRecipeResponse> addFavouriteRecipe(@RequestBody AddFavouriteRecipeRequest request) {
        FavouriteRecipeResponse response = favouriteRecipeService.addFavouriteRecipe(request);
        return ResponseEntity.ok(response);
    }

    @Override
    public ResponseEntity<Void> deleteFavouriteRecipe(@PathVariable Long recipeId) {
        favouriteRecipeService.deleteFavouriteRecipe(recipeId);
        return ResponseEntity.ok().build();
    }

    @Override
    public ResponseEntity<FavouriteRecipeResponse> updateFavouriteRecipe(@RequestBody UpdateFavouriteRecipeRequest request) {
        FavouriteRecipeResponse response = favouriteRecipeService.updateFavouriteRecipe(request);
        return ResponseEntity.ok(response);
    }

    @Override
    public ResponseEntity<List<FavouriteRecipeResponse>> getFavouriteRecipesByUserId(
            @PathVariable Long userId,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size
    ) {
        List<FavouriteRecipeResponse> responses = favouriteRecipeService.getFavouriteRecipesByUserId(userId, page, size);
        return ResponseEntity.ok(responses);
    }

    @Override
    public ResponseEntity<List<FavouriteRecipeResponse>> searchFavouriteRecipesByName(
            @RequestParam Long userId,
            @RequestParam String name,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size
    ) {
        List<FavouriteRecipeResponse> responses = favouriteRecipeService.searchFavouriteRecipesByName(userId, name, page, size);
        return ResponseEntity.ok(responses);
    }
}