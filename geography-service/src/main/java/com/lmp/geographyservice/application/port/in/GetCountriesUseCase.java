package com.lmp.geographyservice.application.port.in;

import com.lmp.flightbookingcommon.infrastructure.pagination.PageQuery;
import com.lmp.flightbookingcommon.infrastructure.pagination.PageResult;
import com.lmp.geographyservice.application.query.CountrySearchQuery;
import com.lmp.geographyservice.application.query.CountrySortField;
import com.lmp.geographyservice.domain.model.Country;

public interface GetCountriesUseCase {

    PageResult<Country> searchCountries(CountrySearchQuery countrySearchQuery, PageQuery<CountrySortField> pageQuery);

}
