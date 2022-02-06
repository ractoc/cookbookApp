package com.ractoc.cookbook.dao;

import com.ractoc.cookbook.dao.entity.Recipe;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface RecipeRepository extends JpaRepository<Recipe, Integer> {

    Optional<Recipe> findRecipeByName(String name);

    List<Recipe> findAllByNameContainingIgnoreCase(String SearchString);
}