package org.innopolis.cookainno.repository;

import org.innopolis.cookainno.entity.UserFavourite;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface UserFavouriteRepository extends JpaRepository<UserFavourite, Long> {
    List<UserFavourite> findByUserId(Long userId);
}