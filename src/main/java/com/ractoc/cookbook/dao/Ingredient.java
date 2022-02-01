package com.ractoc.cookbook.dao;

import com.ractoc.cookbook.model.MeasurementType;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;

@Getter
@Setter
@RequiredArgsConstructor
@ToString(onlyExplicitlyIncluded = true)
@Entity(name = "Ingredient")
@Table(name = "ingredient", indexes = {
        @Index(name = "ingredient_name_uindex", columnList = "name", unique = true)
})
public class Ingredient {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Integer id;

    @Column(name = "name", length = 25, nullable = false)
    private String name;

    @Column(name = "measurement_type", nullable = false)
    private MeasurementType measurementType;

}