package com.ractoc.cookbook.service;

import com.ractoc.cookbook.dao.RecipeRepository;
import com.ractoc.cookbook.dao.entity.Recipe;
import com.ractoc.cookbook.model.RecipeCategory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.stream.Stream;

import static org.apache.commons.lang3.StringUtils.isBlank;

@Service
public class RecipeService {

    private final RecipeRepository recipeRepository;

    @Autowired
    public RecipeService(RecipeRepository recipeRepository) {
        this.recipeRepository = recipeRepository;
    }

    public Stream<Recipe> findAllRecipes(String searchString, RecipeCategory searchCategory) {
        Stream<Recipe> recipeStream;
        if (isBlank(searchString)) {
            recipeStream = recipeRepository.findAll().stream();
        } else {
            recipeStream = recipeRepository.findAllByNameContainingIgnoreCase(searchString).stream();
        }
        return recipeStream.filter(r -> searchCategory == null || searchCategory == r.getRecipeCategory());
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

    public Recipe storeImageFileForRecipe(Recipe recipe, String imageFileName) {
        recipe.setImageFileName(imageFileName);
        return saveRecipe(recipe);
    }
}
