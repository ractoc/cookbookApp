package com.ractoc.cookbook.model;

import lombok.*;

import java.io.Serializable;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
public class RecipeModel implements Serializable {
    private Integer id;
    private String name;
    private String description;
    private String imageFileName;

    private Set<RecipeIngredientModel> recipeIngredients;
}
