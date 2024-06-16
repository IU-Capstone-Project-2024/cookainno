package org.innopolis.cookainno.service;

import lombok.RequiredArgsConstructor;
import org.innopolis.cookainno.dto.AddFavouriteRecipeRequest;
import org.innopolis.cookainno.dto.FavouriteRecipeResponse;
import org.innopolis.cookainno.dto.UpdateFavouriteRecipeRequest;
import org.innopolis.cookainno.entity.FavouriteRecipe;
import org.innopolis.cookainno.entity.User;
import org.innopolis.cookainno.repository.FavouriteRecipeRepository;
import org.innopolis.cookainno.repository.UserRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class FavouriteRecipeService {
    private final FavouriteRecipeRepository favouriteRecipeRepository;
    private final UserRepository userRepository;

    public FavouriteRecipeResponse addFavouriteRecipe(AddFavouriteRecipeRequest request) {
        User user = userRepository.findById(request.userId())
                .orElseThrow(() -> new RuntimeException("User not found"));

        FavouriteRecipe recipe = FavouriteRecipe.builder()
                .user(user)
                .name(request.name())
                .instructions(request.instructions())
                .dateAdded(LocalDateTime.now())
                .build();

        favouriteRecipeRepository.save(recipe);

        return new FavouriteRecipeResponse(
                recipe.getId(),
                recipe.getUser().getId(),
                recipe.getName(),
                recipe.getInstructions(),
                recipe.getDateAdded()
        );
    }

    public void deleteFavouriteRecipe(Long recipeId) {
        favouriteRecipeRepository.deleteById(recipeId);
    }

    public FavouriteRecipeResponse updateFavouriteRecipe(UpdateFavouriteRecipeRequest request) {
        FavouriteRecipe recipe = favouriteRecipeRepository.findById(request.recipeId())
                .orElseThrow(() -> new RuntimeException("Recipe not found"));

        recipe.setName(request.name());
        recipe.setInstructions(request.instructions());
        favouriteRecipeRepository.save(recipe);

        return new FavouriteRecipeResponse(
                recipe.getId(),
                recipe.getUser().getId(),
                recipe.getName(),
                recipe.getInstructions(),
                recipe.getDateAdded()
        );
    }

    public List<FavouriteRecipeResponse> getFavouriteRecipesByUserId(Long userId, int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        Page<FavouriteRecipe> recipePage = favouriteRecipeRepository.findByUserId(userId, pageable);
        return recipePage.stream()
                .map(recipe -> new FavouriteRecipeResponse(
                        recipe.getId(),
                        recipe.getUser().getId(),
                        recipe.getName(),
                        recipe.getInstructions(),
                        recipe.getDateAdded()))
                .collect(Collectors.toList());
    }

    public List<FavouriteRecipeResponse> searchFavouriteRecipesByName(Long userId, String name, int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        Page<FavouriteRecipe> recipePage = favouriteRecipeRepository.findByUserIdAndNameContainingIgnoreCase(userId, name, pageable);
        return recipePage.stream()
                .map(recipe -> new FavouriteRecipeResponse(
                        recipe.getId(),
                        recipe.getUser().getId(),
                        recipe.getName(),
                        recipe.getInstructions(),
                        recipe.getDateAdded()))
                .collect(Collectors.toList());
    }
}