package com.ractoc.cookbook.dao.entity;

import lombok.*;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.io.Serializable;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@EqualsAndHashCode
@Embeddable
public class RecipeIngredientId implements Serializable {

    @Column(name = "recipe_id", nullable = false)
    Integer recipeId;
    @Column(name = "ingredient_id", nullable = false)
    Integer ingredientId;
}