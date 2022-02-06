package com.ractoc.cookbook.mapper;

import com.ractoc.cookbook.dao.entity.RecipeIngredient;
import com.ractoc.cookbook.model.RecipeIngredientModel;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface RecipeIngredientMapper extends BaseMapper {
    RecipeIngredientMapper INSTANCE = Mappers.getMapper(RecipeIngredientMapper.class);

    RecipeIngredient modelToDB(RecipeIngredientModel recipeIngredientModel);

    RecipeIngredientModel dbToModel(RecipeIngredient recipeIngredientIngredient);
}
