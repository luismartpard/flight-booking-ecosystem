package com.lmp.geographyservice.application.usecase;

import com.lmp.flightbookingcommon.infrastructure.pagination.PageQuery;
import com.lmp.flightbookingcommon.infrastructure.pagination.PageQueryConfig;
import com.lmp.flightbookingcommon.infrastructure.pagination.PageQueryNormalizer;
import com.lmp.flightbookingcommon.infrastructure.pagination.PageResult;
import com.lmp.flightbookingcommon.infrastructure.pagination.SortDirection;
import com.lmp.geographyservice.application.exception.InvalidCountryPageQueryException;
import com.lmp.geographyservice.application.port.in.GetCountriesUseCase;
import com.lmp.geographyservice.application.port.out.CountryRepositoryPort;
import com.lmp.geographyservice.application.query.CountrySearchQuery;
import com.lmp.geographyservice.application.query.CountrySortField;
import com.lmp.geographyservice.domain.model.Country;

public class GetCountriesUseCaseImpl implements GetCountriesUseCase {

    private static final PageQueryConfig<CountrySortField> CONFIG =
            new PageQueryConfig<>(
                    0,
                    20,
                    100,
                    CountrySortField.ISO2,
                    SortDirection.ASC
            );

    private final CountryRepositoryPort countryRepositoryPort;

    public GetCountriesUseCaseImpl(CountryRepositoryPort countryRepositoryPort) {
        this.countryRepositoryPort = countryRepositoryPort;
    }

    @Override
    public PageResult<Country> searchCountries(CountrySearchQuery countrySearchQuery,
                                               PageQuery<CountrySortField> pageQuery) {
        PageQuery<CountrySortField> normalizedPageQuery = PageQueryNormalizer
                .normalize(pageQuery,
                        CONFIG.getDefaultPage(),
                        CONFIG.getDefaultSize(),
                        CONFIG.getDefaultSortBy(),
                        CONFIG.getDefaultSortDirection(),
                        CONFIG.getMaxSize(),
                        InvalidCountryPageQueryException::new);
        return countryRepositoryPort.findAll(countrySearchQuery, normalizedPageQuery);
    }
}
