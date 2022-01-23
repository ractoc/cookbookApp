package com.ractoc.cookbook.handler;

import com.ractoc.cookbook.dao.Recipe;
import com.ractoc.cookbook.exception.DuplicateEntryException;
import com.ractoc.cookbook.mapper.RecipeMapper;
import com.ractoc.cookbook.model.RecipeModel;
import com.ractoc.cookbook.service.RecipeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Component
public record RecipeHandler(RecipeService recipeService) {

    @Autowired
    public RecipeHandler {
    }

    public List<RecipeModel> findAllRecipes(String searchString) {
        return recipeService.findAllRecipes(searchString).map(RecipeMapper.INSTANCE::dbToModel).collect(Collectors.toList());
    }

    public Optional<RecipeModel> findRecipeById(Integer id) {
        return recipeService.findRecipeById(id).map(RecipeMapper.INSTANCE::dbToModel);
    }

    public RecipeModel saveRecipe(RecipeModel recipeModel) throws DuplicateEntryException {
        Optional<Recipe> recipe = recipeService.findRecipeByName(recipeModel.getName());
        if (recipe.isPresent() && !recipe.get().getId().equals(recipeModel.getId())) {
            throw new DuplicateEntryException("name");
        }
        return RecipeMapper.INSTANCE.dbToModel(recipeService.saveRecipe(RecipeMapper.INSTANCE.modelToDB(recipeModel)));
    }

    public Optional<Integer> deleteRecipe(Integer id) {
        if (recipeService.findRecipeById(id).isPresent()) {
            recipeService.deleteRecipe(id);
            return Optional.of(id);
        }
        return Optional.empty();
    }
}
