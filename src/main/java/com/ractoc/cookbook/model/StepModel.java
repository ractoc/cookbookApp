package com.ractoc.cookbook.model;

import lombok.*;

import javax.validation.constraints.NotNull;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
public class StepModel {
    private Integer id;
    @NotNull
    private String description;
    @NotNull
    private Integer stepCounter;
}
