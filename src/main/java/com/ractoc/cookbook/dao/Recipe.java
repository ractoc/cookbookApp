package com.ractoc.cookbook.dao;

import lombok.*;

import javax.persistence.*;

@Getter
@Setter
@RequiredArgsConstructor
@ToString
@Entity(name = "Recipe")
@Table(name = "recipe", indexes = {
        @Index(name = "RECIPE_NAME_uindex", columnList = "NAME", unique = true)
})
public class Recipe {
    @Id
    @Column(name = "ID", nullable = false, updatable = false)
    @GeneratedValue
    private Integer id;

    @Column(name = "NAME", nullable = false, unique = true, length = 50)
    private String name;

    @Column(name = "DESCRIPTION", length = 250)
    private String description;

    @Column(name = "IMAGE")
    private byte[] image;

}