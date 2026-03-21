package com.lmp.geographyservice.application.usecase;

import com.lmp.flightbookingcommon.common.exception.ResourceNotFoundException;
import com.lmp.geographyservice.application.port.in.GetCountryByIso2UseCase;
import com.lmp.geographyservice.application.port.out.CountryRepositoryPort;
import com.lmp.geographyservice.domain.model.Country;

import java.util.Locale;

public class GetCountryByIso2UseCaseImpl implements GetCountryByIso2UseCase {

    private final CountryRepositoryPort countryRepositoryPort;

    public GetCountryByIso2UseCaseImpl(CountryRepositoryPort countryRepositoryPort) {
        this.countryRepositoryPort = countryRepositoryPort;
    }

    @Override
    public Country getCountryByIso2(String iso2) {

        String normalized = iso2 == null ? null : iso2.trim().toUpperCase(Locale.ROOT);

        return countryRepositoryPort.findByIso2(normalized)
                .orElseThrow(() -> new ResourceNotFoundException("country.iso2.not.found", normalized));
    }

}
