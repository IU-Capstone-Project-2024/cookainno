package org.innopolis.cookainno.repository;

import org.innopolis.cookainno.entity.FavouriteRecipe;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FavouriteRecipeRepository extends JpaRepository<FavouriteRecipe, Long> {
    Page<FavouriteRecipe> findByUserId(Long userId, Pageable pageable);
    Page<FavouriteRecipe> findByUserIdAndNameContainingIgnoreCase(Long userId, String name, Pageable pageable);
}