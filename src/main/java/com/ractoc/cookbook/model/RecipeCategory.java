package com.ractoc.cookbook.model;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum RecipeCategory {
    BREAKFAST(100, "Ontbijt"),
    LUNCH(200, "Lunch"),
    DINNER(300, "Diner"),
    SNACK(400, "Tussendoortje");

    final Integer id;
    final String displayName;
}
