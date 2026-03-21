package com.lmp.geographyservice.infrastructure.out.persistence.repository;

import com.lmp.geographyservice.domain.enums.TravelStatus;

public interface CountryRepositoryCustom {

    long updateCountryTravelStatus(String iso2, TravelStatus travelStatus);

}
