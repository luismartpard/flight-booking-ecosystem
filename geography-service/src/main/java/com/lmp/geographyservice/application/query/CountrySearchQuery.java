package com.lmp.geographyservice.application.query;

import com.lmp.geographyservice.domain.enums.TravelStatus;

public record CountrySearchQuery(

        String continentCode,
        String currencyCode,
        TravelStatus travelStatus

) {
}
