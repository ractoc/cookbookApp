package com.ractoc.cookbook.dao;

import com.ractoc.cookbook.dao.entity.Recipe;
import com.ractoc.cookbook.dao.entity.Step;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface StepRepository extends JpaRepository<Step, Integer> {
    Optional<Step> findStepByRecipeIdAndStepCounter(Integer recipeId, Integer stepCounter);

    List<Step> findAllByRecipeId(Integer recipeId);
}