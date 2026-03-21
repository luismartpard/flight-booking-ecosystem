package com.lmp.geographyservice.infrastructure.out;

import com.lmp.geographyservice.domain.model.Continent;
import com.lmp.geographyservice.infrastructure.out.persistence.entity.ContinentJpaEntity;
import com.lmp.geographyservice.infrastructure.out.persistence.mapper.ContinentPersistenceMapper;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

class ContinentPersistenceMapperTest {

    @Test
    void should_map_entity_to_domain() {

        ContinentJpaEntity continentJpaEntity = new ContinentJpaEntity(
                "EU"
        );

        Continent continent = ContinentPersistenceMapper.toDomain(continentJpaEntity);

        assertEquals("EU", continent.getCode());

    }

    @Test
    void should_return_null_when_entity_is_null() {

        assertNull(ContinentPersistenceMapper.toDomain(null));

    }

}
