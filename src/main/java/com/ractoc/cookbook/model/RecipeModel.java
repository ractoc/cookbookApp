package com.ractoc.cookbook.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RecipeModel implements Serializable {
    private Integer id;
    private String name;
    private String description;
    private byte[] image;
}
