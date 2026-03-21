package com.lmp.geographyservice.infrastructure.in.web.dto;

import com.lmp.geographyservice.domain.enums.TravelStatus;
import jakarta.validation.constraints.NotNull;

public record CountryTravelStatusUpdateRequest(

        @NotNull
        TravelStatus travelStatus

) {
}
