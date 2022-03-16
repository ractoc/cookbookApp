package com.ractoc.cookbook.dao;

import com.ractoc.cookbook.dao.entity.RecipeIngredient;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.stream.Stream;

@Repository
public interface RecipeIngredientRepository extends JpaRepository<RecipeIngredient, Integer> {
    boolean existsByIngredientId(Integer ingredientId);

    Stream<RecipeIngredient> findAllByIngredientId(Integer ingredientId);
}
