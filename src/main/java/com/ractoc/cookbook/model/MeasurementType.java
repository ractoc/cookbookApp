package com.ractoc.cookbook.model;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum MeasurementType {
    GRAM(100, "Gram"),
    TEASPOON(200, "Theelepel"),
    TABLESPOON(300, "Eetlepel"),
    PIECE(400, "Stuks"),
    PINCH(500, "Snufje");

    final Integer id;
    final String displayName;
}
