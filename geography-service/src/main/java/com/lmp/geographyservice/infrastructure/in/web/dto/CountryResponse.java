package com.lmp.geographyservice.infrastructure.in.web.dto;

import com.lmp.geographyservice.domain.enums.TravelStatus;

public record CountryResponse(

        String iso2,
        String iso3,
        Integer isoNumeric,
        String defaultName,
        String phoneCode,
        String currencyCode,
        TravelStatus travelStatus

) {
}
