package com.ractoc.cookbook.handler;

import com.ractoc.cookbook.dao.entity.Ingredient;
import com.ractoc.cookbook.exception.DuplicateEntryException;
import com.ractoc.cookbook.mapper.IngredientMapper;
import com.ractoc.cookbook.model.IngredientModel;
import com.ractoc.cookbook.service.FileStorageService;
import com.ractoc.cookbook.service.IngredientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Component
public record IngredientHandler(IngredientService ingredientService, FileStorageService fileStorageService) {

    @Autowired
    public IngredientHandler {
    }

    public List<IngredientModel> findAllIngredients(String searchString) {
        return ingredientService.findAllIngredients(searchString).map(IngredientMapper.INSTANCE::dbToModel).collect(Collectors.toList());
    }

    public Optional<IngredientModel> findIngredientById(Integer id) {
        return ingredientService.findIngredientById(id).map(IngredientMapper.INSTANCE::dbToModel);
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
