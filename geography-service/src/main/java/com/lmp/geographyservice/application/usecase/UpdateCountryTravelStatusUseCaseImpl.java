package com.lmp.geographyservice.application.usecase;

import com.lmp.flightbookingcommon.common.exception.BadRequestException;
import com.lmp.geographyservice.application.port.in.UpdateCountryTravelStatusUseCase;
import com.lmp.geographyservice.application.port.out.CountryRepositoryPort;
import com.lmp.geographyservice.domain.enums.TravelStatus;

import java.util.Locale;

public class UpdateCountryTravelStatusUseCaseImpl implements UpdateCountryTravelStatusUseCase {

    private final CountryRepositoryPort countryRepositoryPort;

    public UpdateCountryTravelStatusUseCaseImpl(CountryRepositoryPort countryRepositoryPort) {
        this.countryRepositoryPort = countryRepositoryPort;
    }

    @Override
    public void updateCountryTravelStatus(String iso2, TravelStatus travelStatus) {

        if (iso2 == null || iso2.isBlank()) {
            throw new BadRequestException("country.iso2.required");
        }

        if (travelStatus == null) {
            throw new BadRequestException("country.travelStatus.required");
        }

        String normalized = iso2.trim().toUpperCase(Locale.ROOT);

        countryRepositoryPort.updateCountryTravelStatus(normalized, travelStatus);

    }

}
