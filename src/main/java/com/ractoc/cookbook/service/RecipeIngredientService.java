package com.ractoc.cookbook.service;

import com.ractoc.cookbook.dao.RecipeIngredientRepository;
import com.ractoc.cookbook.dao.entity.Ingredient;
import com.ractoc.cookbook.dao.entity.Recipe;
import com.ractoc.cookbook.dao.entity.RecipeIngredient;
import com.ractoc.cookbook.dao.entity.RecipeIngredientId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public record RecipeIngredientService(RecipeIngredientRepository recipeIngredientRepository) {

    @Autowired
    public RecipeIngredientService {
    }

    public void saveIngredientToRecipe(Recipe recipe, Ingredient ingredient, Integer amount) {
        RecipeIngredient recipeIngredient = new RecipeIngredient();
        recipeIngredient.setId(new RecipeIngredientId(recipe.getId(), ingredient.getId()));
        recipeIngredient.setRecipe(recipe);
        recipeIngredient.setIngredient(ingredient);
        recipeIngredient.setAmount(amount);
        recipeIngredientRepository.save(recipeIngredient);
    }

    public void removeIngredientFromRecipe(Recipe recipe, Ingredient ingredient) {
        RecipeIngredient recipeIngredient = new RecipeIngredient();
        recipeIngredient.setId(new RecipeIngredientId(recipe.getId(), ingredient.getId()));
        recipeIngredient.setRecipe(recipe);
        recipeIngredient.setIngredient(ingredient);
        recipeIngredientRepository.delete(recipeIngredient);
    }
}
