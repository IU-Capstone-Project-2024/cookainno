package org.innopolis.cookainno.service;

import lombok.RequiredArgsConstructor;
import org.innopolis.cookainno.dto.AddRecipeRequest;
import org.innopolis.cookainno.dto.RecipeResponse;
import org.innopolis.cookainno.dto.UpdateRecipeRequest;
import org.innopolis.cookainno.entity.Recipe;
import org.innopolis.cookainno.exception.RecipeNotFoundException;
import org.innopolis.cookainno.repository.RecipeRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class RecipeService {
    private final RecipeRepository recipeRepository;

    @Transactional
    public RecipeResponse addRecipe(AddRecipeRequest request) {
        Recipe recipe = Recipe.builder()
                .name(request.getName())
                .instructions(request.getInstructions())
                .ingredients(request.getIngredients())
                .imageUrl(request.getImageUrl())
                .build();
        recipeRepository.save(recipe);
        return RecipeResponse.builder()
                .id(recipe.getId())
                .name(recipe.getName())
                .instructions(recipe.getInstructions())
                .ingredients(recipe.getIngredients())
                .likes(0L)
                .imageUrl(recipe.getImageUrl())
                .build();
    }

    @Transactional
    public RecipeResponse updateRecipe(UpdateRecipeRequest request) {
        Recipe recipe = recipeRepository.findById(request.getId())
                .orElseThrow(() -> new RecipeNotFoundException("Recipe not found"));

        // Update the recipe fields
        recipe.setName(request.getName());
        recipe.setInstructions(request.getInstructions());
        recipe.setIngredients(request.getIngredients());
        recipe.setImageUrl(request.getImageUrl()); // Update image URL if needed

        recipeRepository.save(recipe);

        // Return updated recipe details
        return RecipeResponse.builder()
                .id(recipe.getId())
                .name(recipe.getName())
                .instructions(recipe.getInstructions())
                .ingredients(recipe.getIngredients())
                .likes((long) recipe.getUserFavourites().size())
                .imageUrl(recipe.getImageUrl()) // Include image URL in response
                .build();
    }

    @Transactional(readOnly = true)
    public RecipeResponse getRecipeById(Long recipeId) {
        Recipe recipe = recipeRepository.findById(recipeId)
                .orElseThrow(() -> new RecipeNotFoundException("Recipe not found"));

        // Return full recipe details
        return RecipeResponse.builder()
                .id(recipe.getId())
                .name(recipe.getName())
                .instructions(recipe.getInstructions())
                .ingredients(recipe.getIngredients())
                .likes((long) recipe.getUserFavourites().size())
                .imageUrl(recipe.getImageUrl()) // Include image URL in response
                .build();
    }

    @Transactional
    public void deleteRecipe(Long recipeId) {
        Recipe recipe = recipeRepository.findById(recipeId)
                .orElseThrow(() -> new RecipeNotFoundException("Recipe not found"));
        recipeRepository.delete(recipe);
    }

    public List<RecipeResponse> searchRecipesByName(String name, int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        Page<Recipe> recipePage = recipeRepository.findByNameContaining(name, pageable);
        return recipePage.stream()
                .map(recipe -> RecipeResponse.builder()
                        .id(recipe.getId())
                        .name(recipe.getName())
                        .instructions(recipe.getInstructions())
                        .ingredients(recipe.getIngredients())
                        .likes((long) recipe.getUserFavourites().size())
                        .imageUrl(recipe.getImageUrl())
                        .build())
                .collect(Collectors.toList());
    }

    public List<RecipeResponse> getRecipesSortedByLikes(int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        Page<Recipe> recipePage = recipeRepository.findAllSortedByLikes(pageable);
        return recipePage.stream()
                .map(recipe -> RecipeResponse.builder()
                        .id(recipe.getId())
                        .name(recipe.getName())
                        .instructions(recipe.getInstructions())
                        .ingredients(recipe.getIngredients())
                        .likes((long) recipe.getUserFavourites().size())
                        .imageUrl(recipe.getImageUrl())
                        .build())
                .collect(Collectors.toList());
    }
}