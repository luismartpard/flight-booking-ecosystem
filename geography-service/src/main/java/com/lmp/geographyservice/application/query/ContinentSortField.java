package com.lmp.geographyservice.application.query;

import com.lmp.geographyservice.application.exception.InvalidContinentPageQueryException;

import java.util.Arrays;

public enum ContinentSortField {

    CODE;

    public static ContinentSortField from(String value) {

        if(value == null || value.isBlank()) {
            return CODE;
        }

        return Arrays.stream(values())
                .filter(field -> field.name().equalsIgnoreCase(value))
                .findFirst()
                .orElseThrow(() -> new InvalidContinentPageQueryException("continent.page.invalid.sort", value));
    }

}
