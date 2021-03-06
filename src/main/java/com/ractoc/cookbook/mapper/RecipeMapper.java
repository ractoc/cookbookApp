package com.ractoc.cookbook.mapper;

import com.ractoc.cookbook.dao.entity.Recipe;
import com.ractoc.cookbook.model.RecipeModel;
import com.ractoc.cookbook.model.SimpleRecipeModel;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface RecipeMapper extends BaseMapper {
    RecipeMapper INSTANCE = Mappers.getMapper(RecipeMapper.class);

    Recipe modelToDB(RecipeModel recipeModel);

    RecipeModel dbToModel(Recipe recipe);

    SimpleRecipeModel dbToSimpleModel(Recipe recipe);
}
