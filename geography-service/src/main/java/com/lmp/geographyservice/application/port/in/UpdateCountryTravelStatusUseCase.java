package com.lmp.geographyservice.application.port.in;

import com.lmp.geographyservice.domain.enums.TravelStatus;

public interface UpdateCountryTravelStatusUseCase {

    void updateCountryTravelStatus(String iso2, TravelStatus travelStatus);

}
