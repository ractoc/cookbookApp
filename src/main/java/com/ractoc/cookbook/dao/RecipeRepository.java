package com.ractoc.cookbook.dao;

import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface RecipeRepository extends CrudRepository<Recipe, Integer> {

    Optional<Recipe> findRecipeByName(String name);
}