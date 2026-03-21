package com.lmp.geographyservice.application.usecase;

import com.lmp.flightbookingcommon.infrastructure.pagination.PageQuery;
import com.lmp.flightbookingcommon.infrastructure.pagination.PageResult;
import com.lmp.geographyservice.application.port.in.GetCountriesUseCase;
import com.lmp.geographyservice.application.port.out.CountryRepositoryPort;
import com.lmp.geographyservice.application.query.CountrySearchQuery;
import com.lmp.geographyservice.application.query.CountrySortField;
import com.lmp.geographyservice.domain.model.Country;

public class GetCountriesUseCaseImpl implements GetCountriesUseCase {

    private final CountryRepositoryPort countryRepositoryPort;

    public GetCountriesUseCaseImpl(CountryRepositoryPort countryRepositoryPort) {
        this.countryRepositoryPort = countryRepositoryPort;
    }

    @Override
    public PageResult<Country> searchCountries(CountrySearchQuery countrySearchQuery, PageQuery<CountrySortField> pageQuery) {
        return countryRepositoryPort.findAll(countrySearchQuery, pageQuery);
    }
}
