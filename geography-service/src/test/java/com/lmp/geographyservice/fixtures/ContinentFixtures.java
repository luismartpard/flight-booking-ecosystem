package com.lmp.geographyservice.fixtures;

import com.lmp.geographyservice.infrastructure.out.persistence.entity.ContinentJpaEntity;
import com.lmp.geographyservice.infrastructure.out.persistence.repository.ContinentRepositoryJpa;

public class ContinentFixtures {

    private final ContinentRepositoryJpa continentRepositoryJpa;

    public ContinentFixtures(ContinentRepositoryJpa continentRepositoryJpa) {
        this.continentRepositoryJpa = continentRepositoryJpa;
    }

    public ContinentJpaEntity createContinent(String code) {
        ContinentJpaEntity continent = new ContinentJpaEntity(code);

        return continentRepositoryJpa.saveAndFlush(continent);

    }


}
