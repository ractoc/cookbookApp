package com.ractoc.cookbook.service;

import com.ractoc.cookbook.dao.entity.Ingredient;
import com.ractoc.cookbook.dao.IngredientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.stream.Stream;

@Service
public class IngredientService {

    private final IngredientRepository ingredientRepository;

    @Autowired
    public IngredientService(IngredientRepository ingredientRepository) {
        this.ingredientRepository = ingredientRepository;
    }

    public Stream<Ingredient> findAllIngredients(String searchString) {
        if (searchString.isBlank()) {
            return ingredientRepository.findAll().stream();
        } else {
            return ingredientRepository.findAllByNameContainingIgnoreCase(searchString).stream();
        }
    }

    public Optional<Ingredient> findIngredientById(Integer id) {
        return ingredientRepository.findById(id);
    }

    public Optional<Ingredient> findIngredientByName(String name) {
        return ingredientRepository.findIngredientByName(name);
    }

    public Ingredient saveIngredient(Ingredient ingredient) {
        return ingredientRepository.save(ingredient);
    }

    public void deleteIngredient(Integer id) {
        ingredientRepository.deleteById(id);
    }
}
