package com.ractoc.cookbook.model;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
public class StepModel {
    private Integer id;
    private String description;
    private Integer stepCounter;
}
