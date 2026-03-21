package com.lmp.geographyservice.application;

import com.lmp.geographyservice.application.exception.InvalidCountryPageQueryException;
import com.lmp.geographyservice.application.query.CountrySortField;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class CountrySortFieldTest {

    @Test
    void getProperty_whenIso2_shouldReturnIso2Property() {
        assertThat(CountrySortField.ISO2.getProperty()).isEqualTo("iso2");
    }

    @Test
    void getProperty_whenIso3_shouldReturnIso3Property() {
        assertThat(CountrySortField.ISO3.getProperty()).isEqualTo("iso3");
    }

    @Test
    void getProperty_whenDefaultName_shouldReturnDefaultNameProperty() {
        assertThat(CountrySortField.DEFAULT_NAME.getProperty()).isEqualTo("defaultName");
    }

    @Test
    void from_whenValueIsNull_shouldReturnDefaultName() {
        CountrySortField result = CountrySortField.from(null);

        assertThat(result).isEqualTo(CountrySortField.DEFAULT_NAME);
    }

    @Test
    void from_whenValueIsBlank_shouldReturnDefaultName() {
        CountrySortField result = CountrySortField.from("   ");

        assertThat(result).isEqualTo(CountrySortField.DEFAULT_NAME);
    }

    @Test
    void from_whenValueIsIso2InUppercase_shouldReturnIso2() {
        CountrySortField result = CountrySortField.from("ISO2");

        assertThat(result).isEqualTo(CountrySortField.ISO2);
    }

    @Test
    void from_whenValueIsIso3InLowercase_shouldReturnIso3() {
        CountrySortField result = CountrySortField.from("iso3");

        assertThat(result).isEqualTo(CountrySortField.ISO3);
    }

    @Test
    void from_whenValueIsInvalid_shouldThrowIllegalArgumentException() {
        assertThatThrownBy(() -> CountrySortField.from("name"))
                .isInstanceOf(InvalidCountryPageQueryException.class)
                .hasMessage("country.page.invalid.sort");
    }

}
