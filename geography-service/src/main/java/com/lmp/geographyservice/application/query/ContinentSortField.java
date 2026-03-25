package com.lmp.geographyservice.application.query;

import com.lmp.flightbookingcommon.infrastructure.pagination.SortField;
import com.lmp.geographyservice.application.exception.InvalidContinentPageQueryException;

import java.util.Locale;

public enum ContinentSortField implements SortField {

    CODE("code");

    private final String property;

    ContinentSortField(String property) {
        this.property = property;
    }

    @Override
    public String getProperty() {
        return property;
    }

    public static ContinentSortField from(String value) {

        if (value == null || value.isBlank()) {
            return CODE;
        }

        try {
            return ContinentSortField.valueOf(value.toUpperCase(Locale.ROOT));
        } catch (IllegalArgumentException e) {
            throw new InvalidContinentPageQueryException("continent.page.invalid.sort", value);
        }

    }

}
