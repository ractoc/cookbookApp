package com.ractoc.cookbook.handler;

import com.ractoc.cookbook.dao.entity.Ingredient;
import com.ractoc.cookbook.exception.DuplicateEntryException;
import com.ractoc.cookbook.mapper.IngredientMapper;
import com.ractoc.cookbook.model.IngredientModel;
import com.ractoc.cookbook.service.IngredientService;
import com.ractoc.cookbook.service.RecipeIngredientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Component
public class IngredientHandler {

    private final IngredientService ingredientService;
    private final RecipeIngredientService recipeIngredientService;

    @Autowired
    public IngredientHandler(IngredientService ingredientService, RecipeIngredientService recipeIngredientService) {
        this.ingredientService = ingredientService;
        this.recipeIngredientService = recipeIngredientService;
    }

    public List<IngredientModel> findAllIngredients(String searchString) {
        return ingredientService.findAllIngredients(searchString)
                .map(IngredientMapper.INSTANCE::dbToModel)
                .peek(model -> model.setInUse(recipeIngredientService.isIngredientInUse(model.getId())))
                .collect(Collectors.toList());
    }

    public Optional<IngredientModel> findIngredientById(Integer id) {
        return ingredientService.findIngredientById(id)
                .map(IngredientMapper.INSTANCE::dbToModel)
                .map(model -> {
                    model.setInUse(recipeIngredientService.isIngredientInUse(model.getId()));
                    return model;
                });
    }

    public IngredientModel saveIngredient(IngredientModel ingredientModel) throws DuplicateEntryException {
        Optional<Ingredient> ingredient = ingredientService.findIngredientByName(ingredientModel.getName());
        if (ingredient.isPresent() && !ingredient.get().getId().equals(ingredientModel.getId())) {
            throw new DuplicateEntryException("name");
        }
        return IngredientMapper.INSTANCE.dbToModel(ingredientService.saveIngredient(IngredientMapper.INSTANCE.modelToDB(ingredientModel)));
    }

    public Optional<Integer> deleteIngredient(Integer id) {
        if (ingredientService.findIngredientById(id).isPresent()) {
            ingredientService.deleteIngredient(id);
            return Optional.of(id);
        }
        return Optional.empty();
    }

}
