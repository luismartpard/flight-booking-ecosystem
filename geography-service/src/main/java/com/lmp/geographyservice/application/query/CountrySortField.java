package com.lmp.geographyservice.application.query;

import com.lmp.geographyservice.application.exception.InvalidCountryPageQueryException;

public enum CountrySortField {

    ISO2("iso2"),
    ISO3("iso3"),
    DEFAULT_NAME("defaultName");

    private final String property;

    CountrySortField(String property) {
        this.property = property;
    }

    public String getProperty() {
        return property;
    }

    public static CountrySortField from(String value) {
        if (value == null || value.isBlank()) {
            return DEFAULT_NAME;
        }

        try {
            return CountrySortField.valueOf(value.toUpperCase());
        } catch(IllegalArgumentException e) {
            throw new InvalidCountryPageQueryException("country.page.invalid.sort", value);
        }

    }
}
