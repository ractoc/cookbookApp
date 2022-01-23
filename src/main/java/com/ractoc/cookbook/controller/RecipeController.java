package com.ractoc.cookbook.controller;

import com.ractoc.cookbook.exception.DuplicateEntryException;
import com.ractoc.cookbook.handler.RecipeHandler;
import com.ractoc.cookbook.model.RecipeModel;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.xml.bind.ValidationException;

import static org.springframework.http.HttpStatus.*;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@RestController
@RequestMapping("/recipe")
@Validated
@Slf4j
public class RecipeController extends BaseController {

    private final RecipeHandler recipeHandler;

    @Autowired
    public RecipeController(RecipeHandler recipeHandler) {
        this.recipeHandler = recipeHandler;
    }

    @Transactional
    @GetMapping(value = "/", produces = APPLICATION_JSON_VALUE)
    public ResponseEntity<RecipeModel[]> findAllRecipes(@RequestParam("searchString") String searchString) {
        return new ResponseEntity<>(
                recipeHandler.findAllRecipes(searchString).toArray(new RecipeModel[0])
                , OK);
    }

    @Transactional
    @GetMapping(value = "/{id}", produces = APPLICATION_JSON_VALUE)
    public ResponseEntity<RecipeModel> findRecipeById(@PathVariable("id") Integer recipeId) {
        return recipeHandler.findRecipeById(recipeId).map(recipe -> new ResponseEntity<>(recipe, OK)).orElse(new ResponseEntity<>(NOT_FOUND));
    }

    @Transactional
    @PostMapping(value = "/", consumes = APPLICATION_JSON_VALUE, produces = APPLICATION_JSON_VALUE)
    public ResponseEntity<RecipeModel> saveRecipe(@RequestBody RecipeModel recipe) {
        try {
            return new ResponseEntity<>(recipeHandler.saveRecipe(recipe), CREATED);
        } catch (DuplicateEntryException e) {
            return new ResponseEntity(e.getMessage(), CONFLICT);
        }
    }

    @Transactional
    @PutMapping(value = "/{id}", consumes = APPLICATION_JSON_VALUE, produces = APPLICATION_JSON_VALUE)
    public ResponseEntity<RecipeModel> updateRecipe(@PathVariable("id") Integer recipeId, @RequestBody RecipeModel recipe) throws ValidationException {
        if (!recipeId.equals(recipe.getId())) {
            throw new ValidationException("Supplied ID doesn't match recipe id");
        }
        try {
            return new ResponseEntity<>(recipeHandler.saveRecipe(recipe), OK);
        } catch (DuplicateEntryException e) {
            return new ResponseEntity(e.getMessage(), CONFLICT);
        }
    }

    @Transactional
    @DeleteMapping(value = "/{id}", produces = APPLICATION_JSON_VALUE)
    public ResponseEntity<Integer> deleteRecipe(@PathVariable("id") Integer recipeId) {
        return recipeHandler.deleteRecipe(recipeId).map(id -> new ResponseEntity<>(id, NO_CONTENT)).orElse(new ResponseEntity<>(NOT_FOUND));
    }

}
