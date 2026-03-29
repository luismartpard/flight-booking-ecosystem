package com.lmp.geographyservice.infrastructure.out;

import com.lmp.geographyservice.domain.enums.TravelStatus;
import com.lmp.geographyservice.domain.model.Country;
import com.lmp.geographyservice.infrastructure.out.persistence.entity.CountryJpaEntity;
import com.lmp.geographyservice.infrastructure.out.persistence.mapper.CountryPersistenceMapper;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

class CountryPersistenceMapperTest {

    @Test
    void should_map_entity_toDomain() {

        CountryJpaEntity countryJpaEntity = CountryJpaEntity.of(
                "ES", "ESP", 724, "Spain", "+34",
                "EUR", TravelStatus.AVAILABLE
        );

        Country country = CountryPersistenceMapper.toDomain(countryJpaEntity);

        assertEquals("ES", country.getIso2());
        assertEquals("ESP", country.getIso3());
        assertEquals(724, country.getIsoNumeric());
        assertEquals("Spain", country.getDefaultName());
        assertEquals("EUR", country.getCurrencyCode());
        assertEquals(TravelStatus.AVAILABLE, country.getTravelStatus());

    }

    @Test
    void should_return_null_when_entity_is_null() {

        assertNull(CountryPersistenceMapper.toDomain(null));

    }

}
