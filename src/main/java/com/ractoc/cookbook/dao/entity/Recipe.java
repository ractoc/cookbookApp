package com.ractoc.cookbook.dao.entity;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;
import java.util.Set;

@Entity(name = "Recipe")
@Getter
@Setter
@ToString(onlyExplicitlyIncluded = true)
@Table(name = "recipe", indexes = {
        @Index(name = "RECIPE_NAME_uindex", columnList = "NAME", unique = true)
})
public class Recipe {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID", nullable = false)
    @ToString.Include
    private Integer id;

    @Column(name = "NAME", nullable = false, unique = true, length = 50)
    @ToString.Include
    private String name;

    @Column(name = "DESCRIPTION", length = 250)
    @ToString.Include
    private String description;

    @Column(name = "IMAGE_FILE_NAME", length = 25)
    @ToString.Include
    private String imageFileName;

    @OneToMany(mappedBy = "recipe")
    Set<RecipeIngredient> recipeIngredients;

    @OneToMany(mappedBy = "recipe")
    Set<Step> steps;

}