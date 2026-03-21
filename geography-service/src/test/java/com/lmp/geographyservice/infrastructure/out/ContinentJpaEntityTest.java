package com.lmp.geographyservice.infrastructure.out;

import com.lmp.geographyservice.infrastructure.out.persistence.entity.ContinentJpaEntity;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ContinentJpaEntityTest {

    @Test
    void should_create_entity_with_required_constructor() {

        ContinentJpaEntity continentJpaEntity = new ContinentJpaEntity(
                "EU"
        );

        assertEquals("EU", continentJpaEntity.getCode());

    }

    @Test
    void should_not_be_equal_when_id_different() {

        ContinentJpaEntity continent1 = new ContinentJpaEntity(
                "EU"
        );

        ContinentJpaEntity continent2 = new ContinentJpaEntity(
                "EU"
        );

        setId(continent1, 1L);
        setId(continent2, 2L);

        assertNotEquals(continent1, continent2);

    }

    @Test
    void should_not_be_equal_when_null() {

        ContinentJpaEntity continent = new ContinentJpaEntity(
                "EU"
        );

        assertNotEquals(null, continent);

    }

    @Test
    void should_update_updatedAt_on_preUpdate() {

        ContinentJpaEntity continent = new ContinentJpaEntity(
                "EU"
        );

        continent.preUpdate();

        assertNotNull(continent.getUpdatedAt());

    }

    private void setId(ContinentJpaEntity continent, Long id) {

        try {
            var field = ContinentJpaEntity.class.getDeclaredField("id");
            field.setAccessible(true);
            field.set(continent, id);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

    }

}
