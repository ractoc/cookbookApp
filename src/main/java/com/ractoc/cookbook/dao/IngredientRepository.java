package com.ractoc.cookbook.dao;

import com.ractoc.cookbook.dao.entity.Ingredient;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface IngredientRepository extends JpaRepository<Ingredient, Integer> {

    Optional<Ingredient> findIngredientByName(String name);

    List<Ingredient> findAllByNameContainingIgnoreCase(String SearchString);
}