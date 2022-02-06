package com.ractoc.cookbook.model;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
public class RecipeIngredientModel {
    private IngredientModel ingredient;
    private Integer amount;
}
