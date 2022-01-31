package com.ractoc.cookbook.handler;

import com.ractoc.cookbook.dao.Recipe;
import com.ractoc.cookbook.exception.DuplicateEntryException;
import com.ractoc.cookbook.exception.FileStorageException;
import com.ractoc.cookbook.exception.NoSuchEntryException;
import com.ractoc.cookbook.mapper.RecipeMapper;
import com.ractoc.cookbook.model.RecipeModel;
import com.ractoc.cookbook.model.SimpleRecipeModel;
import com.ractoc.cookbook.service.FileStorageService;
import com.ractoc.cookbook.service.RecipeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Component;
import org.springframework.util.MimeTypeUtils;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Component
public record RecipeHandler(RecipeService recipeService, FileStorageService fileStorageService) {

    @Autowired
    public RecipeHandler {
    }

    public List<SimpleRecipeModel> findAllRecipes(String searchString) {
        return recipeService.findAllRecipes(searchString).map(RecipeMapper.INSTANCE::dbToSimpleModel).collect(Collectors.toList());
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

    public RecipeModel storeImageForRecipe(Integer recipeId, MultipartFile file) throws NoSuchEntryException, FileStorageException {
        if (MimeTypeUtils.IMAGE_JPEG_VALUE.equals(file.getContentType()) ||
                MimeTypeUtils.IMAGE_PNG_VALUE.equals(file.getContentType())) {
            Optional<Recipe> recipeOptional = recipeService.findRecipeById(recipeId);
            if (recipeOptional.isPresent()) {
                String imageFileName = fileStorageService.storeFile(recipeId, file);
                return RecipeMapper.INSTANCE.dbToModel(
                        recipeService.storeImageFileForRecipe(recipeOptional.get(), imageFileName));
            } else {
                throw new NoSuchEntryException("No Recipe found for ID " + recipeId);
            }
        } else {
            throw new FileStorageException("Invalid file type, only image/jpeg and image/png are supported");
        }
    }

    public Resource loadImageForRecipe(String fileName) throws FileStorageException {
        return fileStorageService.loadFileAsResource(fileName);
    }

}
