package org.innopolis.cookainno.repository;

import org.innopolis.cookainno.entity.Recipe;
import org.innopolis.cookainno.entity.User;
import org.innopolis.cookainno.entity.UserFavourite;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserFavouriteRepository extends JpaRepository<UserFavourite, Long> {
    Optional<UserFavourite> findByUserAndRecipe(User user, Recipe recipe);

    boolean existsByUserAndRecipe(User user, Recipe recipe);

    Page<UserFavourite> findByUserIdOrderByDateAddedAsc(Long userId, Pageable pageable);

    Page<UserFavourite> findByUserIdOrderByDateAddedDesc(Long userId, Pageable pageable);
}