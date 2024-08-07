package org.innopolis.cookainno.repository;

import org.innopolis.cookainno.entity.Recipe;
import org.innopolis.cookainno.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface RecipeRepository extends JpaRepository<Recipe, Long> {
    @Query("SELECT r FROM Recipe r WHERE LOWER(r.name) LIKE LOWER(CONCAT('%', :name, '%'))")
    Page<Recipe> findByNameContainingIgnoreCase(String name, Pageable pageable);

    @Query("SELECT r FROM Recipe r LEFT JOIN UserFavourite uf ON r.id = uf.recipe.id GROUP BY r.id ORDER BY COUNT(uf.id) DESC")
    Page<Recipe> findAllSortedByLikes(Pageable pageable);

    @Query("SELECT r FROM Recipe r JOIN UserFavourite uf ON r.id = uf.recipe.id WHERE uf.user = :user AND LOWER(r.name) LIKE LOWER(CONCAT('%', :name, '%'))")
    Page<Recipe> findByNameContainingIgnoreCaseAndUserFavouritesUser(@Param("name") String name, @Param("user") User user, Pageable pageable);

}