package org.innopolis.cookainno.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "favouriteRecipes")
public class FavouriteRecipe {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @MapsId("userId")
    private User user;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "instructions", nullable = false)
    private String instructions;

    @Column(name = "dateAdded", nullable = false)
    private LocalDateTime dateAdded;
}
