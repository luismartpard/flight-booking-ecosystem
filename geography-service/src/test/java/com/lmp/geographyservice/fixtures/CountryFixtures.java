package com.lmp.geographyservice.fixtures;

import com.lmp.geographyservice.domain.enums.TravelStatus;
import com.lmp.geographyservice.infrastructure.out.persistence.entity.CountryJpaEntity;
import com.lmp.geographyservice.infrastructure.out.persistence.repository.CountryRepositoryJpa;

public class CountryFixtures {

    private final CountryRepositoryJpa countryRepositoryJpa;

    public CountryFixtures(CountryRepositoryJpa countryRepositoryJpa) {
        this.countryRepositoryJpa = countryRepositoryJpa;
    }

    public CountryJpaEntity createCountry(String iso2, String iso3, Integer isoNumeric,
                                          String defaultName,
                                          String currencyCode, TravelStatus travelStatus) {
        CountryJpaEntity countryJpaEntity = new CountryJpaEntity(iso2, iso3,
                isoNumeric, defaultName, currencyCode, travelStatus);
        return countryRepositoryJpa.saveAndFlush(countryJpaEntity);

    }

}
