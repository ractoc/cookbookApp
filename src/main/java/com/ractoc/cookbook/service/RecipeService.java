package com.ractoc.cookbook.service;

import com.ractoc.cookbook.dao.Recipe;
import com.ractoc.cookbook.dao.RecipeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;

@Service
public record RecipeService(RecipeRepository recipeRepository) {

    @Autowired
    public RecipeService {
    }

    public Stream<Recipe> findAllRecipes() {
        return StreamSupport.stream(recipeRepository.findAll().spliterator(), false);
    }

    public Optional<Recipe> findRecipeById(Integer id) {
        return recipeRepository.findById(id);
    }

    public Optional<Recipe> findRecipeByName(String name) {
        return recipeRepository.findRecipeByName(name);
    }

    public Recipe saveRecipe(Recipe recipe) {
        return recipeRepository.save(recipe);
    }

    public void deleteRecipe(Integer id) {
        recipeRepository.deleteById(id);
    }
}
