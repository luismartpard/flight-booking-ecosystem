package com.lmp.geographyservice.domain;

import com.lmp.geographyservice.domain.enums.TravelStatus;
import com.lmp.geographyservice.domain.model.Country;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

class CountryTest {

    @Test
    void should_create_country_required_constructor() {

        Country country = Country.of("ES", "ESP", 724,
                "Spain", "+34", "EUR", TravelStatus.AVAILABLE);

        assertEquals("ES", country.getIso2());
        assertEquals("ESP", country.getIso3());
        assertEquals(724, country.getIsoNumeric());
        assertEquals("Spain", country.getDefaultName());
        assertEquals("+34", country.getPhoneCode());
        assertEquals(TravelStatus.AVAILABLE, country.getTravelStatus());

    }

    @Test
    void should_return_false_when_compared_with_null() {

        Country country = Country.of("ES", "ESP", 724,
                "Spain", "+34", "EUR", TravelStatus.AVAILABLE);

        assertNotEquals(null, country);

    }

    @Test
    void should_return_false_when_compared_with_other_class() {

        Country country = Country.of("ES", "ESP", 724,
                "Spain", "+34", "EUR", TravelStatus.AVAILABLE);

        Object other = new Object();

        assertNotEquals(other, country);

    }

    @Test
    void should_return_false_when_code_is_different() {

        Country spain = Country.of("ES", "ESP", 724,
                "Spain", "+34", "EUR", TravelStatus.AVAILABLE);

        Country france = Country.of("FR", "FRA", 250,
                "France", "+33", "EUR", TravelStatus.AVAILABLE);

        assertNotEquals(spain, france);

    }

    @Test
    void should_be_equal_to_itself() {

        Country country = Country.of("ES", "ESP", 724,
                "Spain", "+34", "EUR", TravelStatus.AVAILABLE);

        Country sameReference = country;

        assertEquals(country, sameReference);

    }

    @Test
    void should_have_different_hashCode_when_objects_are_equal()  {

        Country spain = Country.of("ES", "ESP", 724,
                "Spain", "+34", "EUR", TravelStatus.AVAILABLE);

        Country spain2 = Country.of("ES", "ESP", 724,
                "Spain", "+34", "EUR", TravelStatus.AVAILABLE);

        assertEquals(spain.hashCode(), spain2.hashCode());
    }

    @Test
    void should_have_different_hashCode_when_objects_are_not_equal() {

        Country spain = Country.of("ES", "ESP", 724,
                "Spain", "+34", "EUR", TravelStatus.AVAILABLE);

        Country france = Country.of("FR", "FRA", 250,
                "France", "+33", "EUR", TravelStatus.AVAILABLE);

        assertNotEquals(spain.hashCode(), france.hashCode());

    }

}
