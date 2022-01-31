package com.ractoc.cookbook.dao;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;

@Entity(name = "Recipe")
@Getter
@Setter
@ToString
@Table(name = "recipe", indexes = {
        @Index(name = "RECIPE_NAME_uindex", columnList = "NAME", unique = true)
})
public class Recipe {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID", nullable = false)
    private Integer id;

    @Column(name = "NAME", nullable = false, unique = true, length = 50)
    private String name;

    @Column(name = "DESCRIPTION", length = 250)
    private String description;

    @Column(name = "IMAGE_FILE_NAME", length = 25)
    private String imageFileName;

}