package com.lmp.geographyservice.application.port.out;

import com.lmp.flightbookingcommon.infrastructure.pagination.PageQuery;
import com.lmp.flightbookingcommon.infrastructure.pagination.PageResult;
import com.lmp.geographyservice.application.query.CountrySearchQuery;
import com.lmp.geographyservice.application.query.CountrySortField;
import com.lmp.geographyservice.domain.enums.TravelStatus;
import com.lmp.geographyservice.domain.model.Country;

import java.util.Optional;

public interface CountryRepositoryPort {

    Optional<Country> findByIso2(String iso2);

    Optional<Country> findByIso3(String iso3);

    PageResult<Country> findAll(CountrySearchQuery countrySearchQuery,
                                        PageQuery<CountrySortField> pageQuery);

    void updateCountryTravelStatus(String iso2, TravelStatus travelStatus);

}
