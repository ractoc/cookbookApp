package com.ractoc.cookbook.mapper;

import com.ractoc.cookbook.dao.Recipe;
import com.ractoc.cookbook.model.RecipeModel;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface RecipeMapper extends BaseMapper {
    RecipeMapper INSTANCE = Mappers.getMapper(RecipeMapper.class);

    Recipe modelToDB(RecipeModel recipeModel);
    RecipeModel dbToModel(Recipe recipe);
}
