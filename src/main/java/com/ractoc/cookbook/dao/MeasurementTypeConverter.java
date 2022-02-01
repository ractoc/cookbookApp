package com.ractoc.cookbook.dao;

import com.ractoc.cookbook.model.MeasurementType;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;
import java.util.stream.Stream;

@Converter(autoApply = true)
public class MeasurementTypeConverter implements AttributeConverter<MeasurementType, Integer> {

    @Override
    public Integer convertToDatabaseColumn(MeasurementType measurementType) {
        if (measurementType == null) {
            return null;
        }
        return measurementType.getId();
    }

    @Override
    public MeasurementType convertToEntityAttribute(Integer id) {
        if (id == null) {
            return null;
        }
        return Stream.of(MeasurementType.values())
                .filter(c -> c.getId().equals(id))
                .findFirst()
                .orElseThrow(IllegalArgumentException::new);
    }
}
