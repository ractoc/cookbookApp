package com.ractoc.cookbook.model;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum MeasurementType {
    GRAM(100, "GRAMS"),
    TEASPOON(200, "TEASPOONS"),
    TABLESPOON(300, "TABLESPOONS"),
    PIECE(400, "PIECES"),
    PINCH(500, "PINCHES");

    final Integer id;
    final String plural;
}
