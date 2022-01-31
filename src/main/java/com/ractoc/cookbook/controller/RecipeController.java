package com.ractoc.cookbook.controller;

import com.ractoc.cookbook.exception.DuplicateEntryException;
import com.ractoc.cookbook.exception.FileStorageException;
import com.ractoc.cookbook.exception.NoSuchEntryException;
import com.ractoc.cookbook.handler.RecipeHandler;
import com.ractoc.cookbook.model.RecipeModel;
import com.ractoc.cookbook.model.SimpleRecipeModel;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.validation.constraints.NotNull;
import javax.xml.bind.ValidationException;
import java.io.IOException;
import java.util.List;

import static org.springframework.http.HttpStatus.*;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;
import static org.springframework.http.MediaType.MULTIPART_FORM_DATA_VALUE;

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
    public ResponseEntity<List<SimpleRecipeModel>> findAllRecipes(@RequestParam("searchString") String searchString) {
        return new ResponseEntity<>(
                recipeHandler.findAllRecipes(searchString)
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

    @PostMapping(value = "/{id}/uploadImage", consumes = MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<RecipeModel> uploadImage(@PathVariable("id") Integer recipeId,
                                                   @RequestParam("file") @NotNull MultipartFile file) {
        try {
            return new ResponseEntity<>(recipeHandler.storeImageForRecipe(recipeId, file), OK);
        } catch (NoSuchEntryException e) {
            return new ResponseEntity(e.getMessage(), NOT_FOUND);
        } catch (FileStorageException e) {
            return new ResponseEntity(e.getMessage(), BAD_REQUEST);
        }
    }

    @GetMapping("/downloadImage/{fileName:.+}")
    public ResponseEntity<Resource> downloadFile(@PathVariable String fileName, HttpServletRequest request) {
        try {
            Resource resource = recipeHandler.loadImageForRecipe(fileName);
            String contentType;
            try {
                contentType = request.getServletContext().getMimeType(resource.getFile().getAbsolutePath());
            } catch (IOException ex) {
                log.info("Could not determine file type. Using default, application/octet-stream");
                contentType = "application/octet-stream";
            }
            return ResponseEntity.ok()
                    .contentType(MediaType.parseMediaType(contentType))
                    .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + resource.getFilename() + "\"")
                    .body(resource);
        } catch (FileStorageException e) {
            return new ResponseEntity(e.getMessage(), BAD_REQUEST);
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
