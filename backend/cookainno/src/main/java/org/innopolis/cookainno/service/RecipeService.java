package org.innopolis.cookainno.service;

import lombok.RequiredArgsConstructor;
import org.innopolis.cookainno.dto.AddRecipeRequest;
import org.innopolis.cookainno.dto.RecipeResponse;
import org.innopolis.cookainno.dto.UpdateRecipeRequest;
import org.innopolis.cookainno.entity.Recipe;
import org.innopolis.cookainno.entity.User;
import org.innopolis.cookainno.entity.UserFavourite;
import org.innopolis.cookainno.exception.RecipeNotFoundException;
import org.innopolis.cookainno.exception.UserNotFoundException;
import org.innopolis.cookainno.repository.RecipeRepository;
import org.innopolis.cookainno.repository.UserFavouriteRepository;
import org.innopolis.cookainno.repository.UserRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class RecipeService {
    private final RecipeRepository recipeRepository;
    private final UserFavouriteRepository userFavouriteRepository;
    private final UserRepository userRepository;

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
        return mapToRecipeResponse(recipe);
    }

    @Transactional(readOnly = true)
    public RecipeResponse getRecipeById(Long recipeId) {
        Recipe recipe = recipeRepository.findById(recipeId)
                .orElseThrow(() -> new RecipeNotFoundException("Recipe not found"));

        // Return full recipe details
        return mapToRecipeResponse(recipe);

    }

    @Transactional
    public void deleteRecipe(Long recipeId) {
        Recipe recipe = recipeRepository.findById(recipeId)
                .orElseThrow(() -> new RecipeNotFoundException("Recipe not found"));
        recipeRepository.delete(recipe);
    }

    public List<RecipeResponse> searchRecipesByName(String name, int page, int size) {
        name = name.toLowerCase().trim();

        Pageable pageable = PageRequest.of(page, size);
        Page<Recipe> recipePage = recipeRepository.findByNameContaining(name, pageable);
        return recipePage.stream()
                .map(this::mapToRecipeResponse)
                .toList();
    }

    public List<RecipeResponse> getRecipesSortedByLikes(int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        Page<Recipe> recipePage = recipeRepository.findAllSortedByLikes(pageable);
        return recipePage.stream()
                .map(this::mapToRecipeResponse)
                .toList();
    }

    @Transactional
    public void addRecipeToFavorites(Long userId, Long recipeId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new UserNotFoundException("User not found"));

        Recipe recipe = recipeRepository.findById(recipeId)
                .orElseThrow(() -> new RecipeNotFoundException("Recipe not found"));

        // Check if the recipe is already in favorites for this user
        if (userFavouriteRepository.existsByUserAndRecipe(user, recipe)) {
            return; // Recipe already in favorites, do nothing
        }

        UserFavourite userFavourite = UserFavourite.builder()
                .user(user)
                .recipe(recipe)
                .dateAdded(LocalDateTime.now())
                .build();

        userFavouriteRepository.save(userFavourite);
    }

    @Transactional(readOnly = true)
    public List<RecipeResponse> getFavoriteRecipes(Long userId, int page, int size, boolean oldestFirst) {
        Pageable pageable = PageRequest.of(page, size);
        Page<UserFavourite> userFavoritesPage;

        if (oldestFirst) {
            userFavoritesPage = userFavouriteRepository.findByUserIdOrderByDateAddedAsc(userId, pageable);
        } else {
            userFavoritesPage = userFavouriteRepository.findByUserIdOrderByDateAddedDesc(userId, pageable);
        }

        return userFavoritesPage.getContent().stream()
                .map(UserFavourite::getRecipe)
                .map(this::mapToRecipeResponse)
                .toList();
    }

    @Transactional
    public void removeRecipeFromFavorites(Long userId, Long recipeId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new UserNotFoundException("User not found"));

        Recipe recipe = recipeRepository.findById(recipeId)
                .orElseThrow(() -> new RecipeNotFoundException("Recipe not found"));

        UserFavourite userFavourite = userFavouriteRepository.findByUserAndRecipe(user, recipe)
                .orElseThrow(() -> new RuntimeException("Recipe is not in favorites"));

        userFavouriteRepository.delete(userFavourite);
    }

    @Transactional(readOnly = true)
    public List<RecipeResponse> searchFavoriteRecipesByName(Long userId, String name, int page, int size) {
        name  = name.toLowerCase().trim();

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new UserNotFoundException("User not found"));

        Pageable pageable = PageRequest.of(page, size);
        Page<Recipe> recipePage = recipeRepository.findByNameContainingAndUserFavouritesUser(name, user, pageable);

        return recipePage.getContent().stream()
                .map(this::mapToRecipeResponse)
                .toList();
    }

    private RecipeResponse mapToRecipeResponse(Recipe recipe) {
        return RecipeResponse.builder()
                .id(recipe.getId())
                .name(recipe.getName())
                .instructions(recipe.getInstructions())
                .ingredients(recipe.getIngredients())
                .likes((long) recipe.getUserFavourites().size())
                .imageUrl(recipe.getImageUrl())
                .build();

    }
}