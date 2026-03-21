package com.lmp.geographyservice.infrastructure.out;

import com.lmp.geographyservice.domain.enums.TravelStatus;
import com.lmp.geographyservice.infrastructure.out.persistence.entity.CountryJpaEntity;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class CountryJpaEntityTest {

    @Test
    void should_create_entity_with_required_constructor() {

        CountryJpaEntity countryJpaEntity = new CountryJpaEntity(
                "ES", "ESP", 724, "Spain", "EUR", TravelStatus.AVAILABLE
        );

        assertEquals("ES", countryJpaEntity.getIso2());
        assertEquals("ESP", countryJpaEntity.getIso3());
        assertEquals(724, countryJpaEntity.getIsoNumeric());
        assertEquals("Spain", countryJpaEntity.getDefaultName());
        assertEquals("EUR", countryJpaEntity.getCurrencyCode());
        assertEquals(TravelStatus.AVAILABLE, countryJpaEntity.getTravelStatus());

    }

    @Test
    void should_not_be_equal_when_id_different() {

        CountryJpaEntity countryJpaEntity = new CountryJpaEntity(
                "ES", "ESP", 724, "Spain", "EUR", TravelStatus.AVAILABLE
        );

        CountryJpaEntity countryJpaEntity2 = new CountryJpaEntity(
                "FR", "FRA", 724, "France", "EUR", TravelStatus.AVAILABLE
        );

        setId(countryJpaEntity, 1L);
        setId(countryJpaEntity2, 2L);

        assertNotEquals(countryJpaEntity2, countryJpaEntity);

    }

    @Test
    void should_not_be_equal_when_null() {

        CountryJpaEntity countryJpaEntity = new CountryJpaEntity(
                "ES", "ESP", 724, "Spain", "EUR", TravelStatus.AVAILABLE
        );

        assertNotEquals(null, countryJpaEntity);

    }

    @Test
    void should_update_updateAt_on_PreUpdate() {

        CountryJpaEntity countryJpaEntity = new CountryJpaEntity(
                "ES", "ESP", 724, "Spain", "EUR", TravelStatus.AVAILABLE
        );

        countryJpaEntity.preUpdate();

        assertNotNull(countryJpaEntity.getUpdatedAt());

    }

    private void setId(CountryJpaEntity country, Long id) {
        try {
            var field = CountryJpaEntity.class.getDeclaredField("id");
            field.setAccessible(true);
            field.set(country, id);
        } catch (Exception e) {
            throw new  RuntimeException(e);
        }
    }

}
