package com.ractoc.cookbook.mapper;

import com.ractoc.cookbook.dao.entity.Ingredient;
import com.ractoc.cookbook.model.IngredientModel;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface IngredientMapper extends BaseMapper {
    IngredientMapper INSTANCE = Mappers.getMapper(IngredientMapper.class);

    Ingredient modelToDB(IngredientModel ingredientModel);

    IngredientModel dbToModel(Ingredient ingredient);
}
