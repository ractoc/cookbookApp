package com.ractoc.cookbook.model;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
public class SimpleRecipeModel {
    private Integer id;
    private String name;
    private String description;
    private RecipeCategory recipeCategory;
    private String imageFileName;
}
