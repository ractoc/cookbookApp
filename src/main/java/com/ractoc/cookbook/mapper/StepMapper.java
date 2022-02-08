package com.ractoc.cookbook.mapper;

import com.ractoc.cookbook.dao.entity.Step;
import com.ractoc.cookbook.model.StepModel;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface StepMapper extends BaseMapper {
    StepMapper INSTANCE = Mappers.getMapper(StepMapper.class);

    Step modelToDB(StepModel stepModel);

    StepModel dbToModel(Step step);
}
