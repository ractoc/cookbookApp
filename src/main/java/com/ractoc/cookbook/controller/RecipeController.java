package com.ractoc.cookbook.controller;

import com.ractoc.cookbook.handler.RecipeHandler;
import com.ractoc.cookbook.model.RecipeModel;
import io.swagger.annotations.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.xml.bind.ValidationException;

import static org.springframework.http.HttpStatus.*;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@Api(tags = {"Recipe Resource"}, value = "/", produces = "application/json")
@SwaggerDefinition(tags = {
        @Tag(name = "Recipe Resource", description = "Main entry point for the Recipe API. " +
                "Handles all recipe management actions. Aside from the HTTP return codes on the requests.")
})
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
    @ApiOperation(value = "Retreive all recipes", response = RecipeModel[].class, produces = "application/json")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Retrieval successfully processed.", response = RecipeModel[].class),
            @ApiResponse(code = 500, message = "Internal server error")
    })
    @GetMapping(value = "/", produces = APPLICATION_JSON_VALUE)
    public ResponseEntity<RecipeModel[]> findAllRecipes() {
        return new ResponseEntity<>(
                recipeHandler.findAllRecipes().toArray(new RecipeModel[0])
                , OK);
    }

    @Transactional
    @ApiOperation(value = "Get recipe by ID", response = RecipeModel.class, produces = "application/json")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Retrieval successfully processed.", response = RecipeModel.class),
            @ApiResponse(code = 404, message = "Recipe not found")
    })
    @GetMapping(value = "/{id}", produces = APPLICATION_JSON_VALUE)
    public ResponseEntity<RecipeModel> findRecipeById(@PathVariable("id") Integer recipeId) {
        return recipeHandler.findRecipeById(recipeId).map(recipe -> new ResponseEntity<>(recipe, OK)).orElse(new ResponseEntity<>(NOT_FOUND));
    }

    @Transactional
    @ApiOperation(value = "Save recipe.", response = RecipeModel.class, consumes = "application/json", produces = "application/json")
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "The recipe was successfully created", response = RecipeModel.class),
            @ApiResponse(code = 500, message = "Internal server error")
    })
    @PostMapping(value = "/", consumes = APPLICATION_JSON_VALUE, produces = APPLICATION_JSON_VALUE)
    public ResponseEntity<RecipeModel> saveRecipe(@RequestBody RecipeModel recipe) {
        return new ResponseEntity<>(recipeHandler.saveRecipe(recipe), CREATED);
    }

    @Transactional
    @ApiOperation(value = "Update recipe.", response = RecipeModel.class, consumes = "application/json", produces = "application/json")
    @ApiResponses(value = {
            @ApiResponse(code = 202, message = "The recipe was successfully updated", response = RecipeModel.class),
            @ApiResponse(code = 500, message = "Internal server error")
    })
    @PutMapping(value = "/{id}", consumes = APPLICATION_JSON_VALUE, produces = APPLICATION_JSON_VALUE)
    public ResponseEntity<RecipeModel> updateRecipe(@PathVariable("id") Integer recipeId, @RequestBody RecipeModel recipe) throws ValidationException {
        if (!recipeId.equals(recipe.getId())) {
            throw new ValidationException("Supplied ID doesn't match fleet id");
        }
        return new ResponseEntity<>(recipeHandler.saveRecipe(recipe), OK);
    }

    @Transactional
    @ApiOperation(value = "Delete recipe.", consumes = "application/json")
    @ApiResponses(value = {
            @ApiResponse(code = 204, message = "The fleet was successfully deleted"),
            @ApiResponse(code = 500, message = "Internal server error")
    })
    @DeleteMapping(value = "/{id}", produces = APPLICATION_JSON_VALUE)
    public ResponseEntity<Integer> deleteFleet(@PathVariable("id") Integer recipeId) {
        return recipeHandler.deleteRecipe(recipeId).map(id -> new ResponseEntity<>(id, NO_CONTENT)).orElse(new ResponseEntity<>(NOT_FOUND));
    }

}
