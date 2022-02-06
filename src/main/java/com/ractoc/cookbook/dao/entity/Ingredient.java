package com.ractoc.cookbook.dao.entity;

import com.ractoc.cookbook.model.MeasurementType;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;
import java.util.Set;

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
    @ToString.Include
    private Integer id;

    @Column(name = "name", length = 25, nullable = false)
    @ToString.Include
    private String name;

    @Column(name = "measurement_type", nullable = false)
    @ToString.Include
    private MeasurementType measurementType;

    @OneToMany(mappedBy = "ingredient")
    Set<RecipeIngredient> recipesIngredients;

}