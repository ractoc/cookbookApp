package com.ractoc.cookbook.model;

import lombok.*;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import java.io.Serializable;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
public class IngredientModel implements Serializable {
    private Integer id;
    @NotNull
    private String name;
    @NotNull
    private MeasurementType measurementType;
    @Positive
    private Integer amount;
    private boolean inUse;
}
