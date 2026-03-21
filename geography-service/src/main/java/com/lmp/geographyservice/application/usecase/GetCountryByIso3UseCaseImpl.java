package com.lmp.geographyservice.application.usecase;

import com.lmp.flightbookingcommon.common.exception.ResourceNotFoundException;
import com.lmp.geographyservice.application.port.in.GetCountryByIso3UseCase;
import com.lmp.geographyservice.application.port.out.CountryRepositoryPort;
import com.lmp.geographyservice.domain.model.Country;

import java.util.Locale;

public class GetCountryByIso3UseCaseImpl implements GetCountryByIso3UseCase {

    private final CountryRepositoryPort  countryRepositoryPort;

    public GetCountryByIso3UseCaseImpl(CountryRepositoryPort countryRepositoryPort) {
        this.countryRepositoryPort = countryRepositoryPort;
    }

    @Override
    public Country getCountryByIso3(String iso3) {

        String normalized = iso3 == null ? null : iso3.trim().toUpperCase(Locale.ROOT);

        return countryRepositoryPort.findByIso3(normalized)
                .orElseThrow(() -> new ResourceNotFoundException("country.iso3.not.found", normalized));
    }
}
