package com.ractoc.cookbook.service;

import com.ractoc.cookbook.dao.StepRepository;
import com.ractoc.cookbook.dao.entity.Recipe;
import com.ractoc.cookbook.dao.entity.Step;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public record StepService(StepRepository stepRepository) {
    public void saveStep(Recipe recipe, Step step) {
        step.setRecipe(recipe);
        stepRepository.save(step);
    }

    public Optional<Step> findStepByRecipeAndCounter(Integer recipeId, Integer stepCounter) {
        return stepRepository.findStepByRecipeIdAndStepCounter(recipeId, stepCounter);
    }

    public Optional<Step> findStepById(Integer stepId) {
        return stepRepository.findById(stepId);
    }

    public void deleteStep(Integer stepId) {
        stepRepository.deleteById(stepId);
    }

    public List<Step> getStepsForRecipe(Integer recipeId) {
        return stepRepository.findAllByRecipeId(recipeId);
    }
}
