package com.ractoc.cookbook.controller;

import com.ractoc.cookbook.exception.DuplicateEntryException;
import com.ractoc.cookbook.handler.IngredientHandler;
import com.ractoc.cookbook.model.IngredientModel;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.xml.bind.ValidationException;
import java.util.List;

import static org.springframework.http.HttpStatus.*;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@RestController
@RequestMapping("/ingredient")
@Validated
@Slf4j
public class IngredientController extends BaseController {

    private final IngredientHandler ingredientHandler;

    @Autowired
    public IngredientController(IngredientHandler ingredientHandler) {
        this.ingredientHandler = ingredientHandler;
    }

    @Transactional
    @GetMapping(value = "/", produces = APPLICATION_JSON_VALUE)
    public ResponseEntity<List<IngredientModel>> findAllIngredients(@RequestParam("searchString") String searchString) {
        return new ResponseEntity<>(
                ingredientHandler.findAllIngredients(searchString)
                , OK);
    }

    @Transactional
    @GetMapping(value = "/{id}", produces = APPLICATION_JSON_VALUE)
    public ResponseEntity<IngredientModel> findIngredientById(@PathVariable("id") Integer ingredientId) {
        return ingredientHandler.findIngredientById(ingredientId).map(ingredient -> new ResponseEntity<>(ingredient, OK)).orElse(new ResponseEntity<>(NOT_FOUND));
    }

    @Transactional
    @PostMapping(value = "/", consumes = APPLICATION_JSON_VALUE, produces = APPLICATION_JSON_VALUE)
    public ResponseEntity<IngredientModel> saveIngredient(@RequestBody IngredientModel ingredient) {
        try {
            return new ResponseEntity<>(ingredientHandler.saveIngredient(ingredient), CREATED);
        } catch (DuplicateEntryException e) {
            return new ResponseEntity(e.getMessage(), CONFLICT);
        }
    }

    @Transactional
    @PutMapping(value = "/{id}", consumes = APPLICATION_JSON_VALUE, produces = APPLICATION_JSON_VALUE)
    public ResponseEntity<IngredientModel> updateIngredient(@PathVariable("id") Integer ingredientId, @RequestBody IngredientModel ingredient) throws ValidationException {
        if (!ingredientId.equals(ingredient.getId())) {
            throw new ValidationException("Supplied ID doesn't match ingredient id");
        }
        try {
            return new ResponseEntity<>(ingredientHandler.saveIngredient(ingredient), OK);
        } catch (DuplicateEntryException e) {
            return new ResponseEntity(e.getMessage(), CONFLICT);
        }
    }

    @Transactional
    @DeleteMapping(value = "/{id}", produces = APPLICATION_JSON_VALUE)
    public ResponseEntity<Integer> deleteIngredient(@PathVariable("id") Integer ingredientId) {
        return ingredientHandler.deleteIngredient(ingredientId).map(id -> new ResponseEntity<>(id, NO_CONTENT)).orElse(new ResponseEntity<>(NOT_FOUND));
    }

}
