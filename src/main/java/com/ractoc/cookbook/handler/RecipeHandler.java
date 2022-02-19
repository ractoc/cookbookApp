package com.ractoc.cookbook.handler;

import com.ractoc.cookbook.dao.entity.Ingredient;
import com.ractoc.cookbook.dao.entity.Recipe;
import com.ractoc.cookbook.dao.entity.Step;
import com.ractoc.cookbook.exception.DuplicateEntryException;
import com.ractoc.cookbook.exception.FileStorageException;
import com.ractoc.cookbook.exception.NoSuchEntryException;
import com.ractoc.cookbook.mapper.RecipeMapper;
import com.ractoc.cookbook.mapper.StepMapper;
import com.ractoc.cookbook.model.RecipeModel;
import com.ractoc.cookbook.model.SimpleRecipeModel;
import com.ractoc.cookbook.model.StepModel;
import com.ractoc.cookbook.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Component;
import org.springframework.util.MimeTypeUtils;
import org.springframework.web.multipart.MultipartFile;

import java.util.*;
import java.util.stream.Collectors;

@Component
public record RecipeHandler(RecipeService recipeService,
                            IngredientService ingredientService,
                            RecipeIngredientService recipeIngredientService,
                            StepService stepService,
                            FileStorageService fileStorageService) {

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

    public Optional<RecipeModel> addOrUpdateIngredient(Integer recipeId, Integer ingredientId, Integer amount) throws NoSuchEntryException {
        Recipe recipe = recipeService.findRecipeById(recipeId).orElseThrow(() -> new NoSuchEntryException("No Recipe found for ID " + recipeId));
        Ingredient ingredient = ingredientService.findIngredientById(ingredientId).orElseThrow(() -> new NoSuchEntryException("No Ingredient found for ID " + ingredientId));
        recipeIngredientService.saveIngredientToRecipe(recipe, ingredient, amount);
        return findRecipeById(recipeId);
    }

    public Optional<RecipeModel> removeIngredient(Integer recipeId, Integer ingredientId) throws NoSuchEntryException {
        Recipe recipe = recipeService.findRecipeById(recipeId).orElseThrow(() -> new NoSuchEntryException("No Recipe found for ID " + recipeId));
        Ingredient ingredient = ingredientService.findIngredientById(ingredientId).orElseThrow(() -> new NoSuchEntryException("No Ingredient found for ID " + ingredientId));
        recipeIngredientService.removeIngredientFromRecipe(recipe, ingredient);
        return findRecipeById(recipeId);
    }

    public Optional<RecipeModel> saveStep(Integer recipeId, StepModel stepModel) throws NoSuchEntryException, DuplicateEntryException {
        Recipe recipe = recipeService.findRecipeById(recipeId).orElseThrow(() -> new NoSuchEntryException("No Recipe found for ID " + recipeId));
        Optional<Step> step = stepService.findStepByRecipeAndCounter(recipeId, stepModel.getStepCounter());
        if (step.isPresent() && !step.get().getId().equals(stepModel.getId())) {
            throw new DuplicateEntryException("stepCounter");
        }
        stepService.saveStep(recipe, StepMapper.INSTANCE.modelToDB(stepModel));
        return findRecipeById(recipeId);
    }

    public Optional<RecipeModel> switchSteps(Integer recipeId, Integer stepAId, Integer stepBId) throws NoSuchEntryException {
        Recipe recipe = recipeService.findRecipeById(recipeId).orElseThrow(() -> new NoSuchEntryException("No Recipe found for ID " + recipeId));
        Step stepA = stepService.findStepById(stepAId).orElseThrow(() -> new NoSuchEntryException("No Step found for ID " + stepAId));
        Step stepB = stepService.findStepById(stepBId).orElseThrow(() -> new NoSuchEntryException("No Step found for ID " + stepBId));

        Integer stepAStepCounter = stepA.getStepCounter();
        Integer stepBStepCounter = stepB.getStepCounter();

        stepA.setStepCounter(stepBStepCounter);
        stepB.setStepCounter(stepAStepCounter);

        stepService.saveStep(recipe, stepA);
        stepService.saveStep(recipe, stepB);

        return findRecipeById(recipeId);
    }

    public Optional<RecipeModel> removeStep(Integer recipeId, Integer stepId) throws NoSuchEntryException {
        Recipe recipe = recipeService.findRecipeById(recipeId).orElseThrow(() -> new NoSuchEntryException("No Recipe found for ID " + recipeId));
        stepService.findStepById(stepId).orElseThrow(() -> new NoSuchEntryException("No Step found for ID " + stepId));
        stepService.deleteStep(stepId);
        List<Step> steps = stepService.getStepsForRecipe(recipeId);
        Map<Integer, Step> stepMap = steps
                .stream()
                .sorted(Comparator.comparingInt(Step::getStepCounter))
                .collect(HashMap::new, (map, step) -> map.put(map.size(), step), Map::putAll);
        stepMap.entrySet()
                .stream()
                .map(entry -> {
                    Step step = entry.getValue();
                    step.setStepCounter(entry.getKey() + 1);
                    return step;
                }).forEach((step) -> stepService.saveStep(recipe, step));
        return findRecipeById(recipeId);
    }
}
