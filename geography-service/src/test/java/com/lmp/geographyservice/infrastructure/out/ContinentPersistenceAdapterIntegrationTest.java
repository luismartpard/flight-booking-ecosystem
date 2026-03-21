package com.lmp.geographyservice.infrastructure.out;

import com.lmp.flightbookingcommon.AbstractIntegrationTest;
import com.lmp.geographyservice.domain.model.Continent;
import com.lmp.geographyservice.fixtures.ContinentFixtures;
import com.lmp.geographyservice.infrastructure.out.persistence.adapter.ContinentPersistenceAdapter;
import com.lmp.geographyservice.infrastructure.out.persistence.repository.ContinentRepositoryJpa;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@Transactional
class ContinentPersistenceAdapterIntegrationTest extends AbstractIntegrationTest {

    @Autowired
    private ContinentPersistenceAdapter continentPersistenceAdapter;

    @Autowired
    private ContinentRepositoryJpa continentRepositoryJpa;

    private ContinentFixtures continentFixtures;

    @BeforeEach
    void setUp() {
        this.continentFixtures = new ContinentFixtures(continentRepositoryJpa);
    }

    @Test
    void shouldSaveAndFindByCode() {

        continentFixtures.createContinent("XX");

        Optional<Continent> found = continentPersistenceAdapter.findByCode("XX");

        assertThat(found)
                .isPresent()
                .get()
                .extracting(
                        Continent::getCode
                )
                .isEqualTo("XX");

    }


}
