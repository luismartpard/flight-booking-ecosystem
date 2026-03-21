package com.lmp.geographyservice.application;

import com.lmp.geographyservice.application.exception.InvalidContinentPageQueryException;
import com.lmp.geographyservice.application.query.ContinentSortField;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class ContinentSortFieldTest {

    @Test
    void from_whenValueIsNull_shouldReturnCode() {
        assertThat(ContinentSortField.from(null)).isEqualTo(ContinentSortField.CODE);
    }

    @Test
    void from_whenValueIsBlank_shouldReturnCode() {
        assertThat(ContinentSortField.from("   ")).isEqualTo(ContinentSortField.CODE);
    }

    @Test
    void from_whenValueIsValid_shouldReturnCode() {
        assertThat(ContinentSortField.from("CODE")).isEqualTo(ContinentSortField.CODE);
        assertThat(ContinentSortField.from("code")).isEqualTo(ContinentSortField.CODE);
    }

    @Test
    void from_whenValueIsInvalid_shouldThrowIllegalArgumentException() {
        assertThatThrownBy(() -> ContinentSortField.from("name"))
                .isInstanceOf(InvalidContinentPageQueryException.class)
                .hasMessage("continent.page.invalid.sort");
    }

}
