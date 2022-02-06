package com.ractoc.cookbook.dao.entity;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;

@Getter
@Setter
@RequiredArgsConstructor
@ToString
@Entity(name = "RecipeIngredient")
@Table(name = "recipe_ingredients")
public class RecipeIngredient {
    @EmbeddedId
    private RecipeIngredientId id;

    @Column(name = "amount")
    private Integer amount;

    @ManyToOne
    @MapsId("recipeId")
    @JoinColumn(name = "recipe_id")
    private Recipe recipe;

    @ManyToOne
    @MapsId("ingredientId")
    @JoinColumn(name = "ingredient_id")
    private Ingredient ingredient;
}