package com.ractoc.cookbook.mapper;

import java.util.Optional;
import java.util.OptionalInt;

public interface BaseMapper {

    @SuppressWarnings({"unused", "OptionalUsedAsFieldOrParameterType"})
    default <T> T unwrapOptional(Optional<T> optional) {
        return optional.orElse(null);
    }

    @SuppressWarnings({"unused", "OptionalUsedAsFieldOrParameterType"})
    default int unwrapOptionalInt(OptionalInt optional) {
        return optional.orElse(0);
    }

}
