package com.lmp.geographyservice.domain;

import com.lmp.geographyservice.domain.model.Continent;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

class ContinentTest {

    @Test
    void should_create_continent_required_constructor() {

        Continent continent = Continent.of(
                "EU", "Europe"
        );

        assertEquals("EU", continent.getCode());

    }

    @Test
    void should_return_false_when_compared_with_null() {

        Continent continent = Continent.of(
                "EU", "Europe"
        );

        assertNotEquals(null, continent);

    }

    @Test
    void should_return_false_when_compared_with_other_class() {

        Continent continent = Continent.of(
                "EU", "Europe"
        );

        Object other = new Object();

        assertNotEquals(continent, other);

    }

    @Test
    void should_return_true_when_code_is_same() {

        Continent continent = Continent.of(
                "EU", "Europe"
        );
        Continent continent2 = Continent.of(
                "EU", "Europe"
        );

        assertEquals(continent, continent2);

    }

    @Test
    void should_return_false_when_code_is_different() {

        Continent continent = Continent.of(
                "EU", "Europe"
        );
        Continent continent2 = Continent.of(
                "AF", "Africa"
        );

        assertNotEquals(continent, continent2);

    }

    @Test
    void should_be_equal_to_itself() {

        Continent continent = Continent.of(
                "EU", "Europe"
        );

        Continent sameReference = continent;

        assertEquals(continent, sameReference);
    }

    @Test
    void should_have_same_hashCode_when_objects_are_equal() {

        Continent continent = Continent.of(
                "EU", "Europe"
        );
        Continent continent2 = Continent.of(
                "EU", "Europe"
        );

        assertEquals(continent.hashCode(), continent2.hashCode());

    }

    @Test
    void should_have_different_hashcode_when_objects_are_not_equal() {

        Continent continent = Continent.of(
                "EU", "Europe"
        );
        Continent continent2 = Continent.of(
                "AF", "Africa"
        );

        assertNotEquals(continent.hashCode(), continent2.hashCode());

    }

}
