package com.ractoc.cookbook.model;

import lombok.*;

import java.io.Serializable;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
public class IngredientModel implements Serializable {
    private Integer id;
    private String name;
    private MeasurementType measurementType;
}
