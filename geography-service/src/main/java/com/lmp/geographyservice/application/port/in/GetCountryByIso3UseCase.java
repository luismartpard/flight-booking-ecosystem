package com.lmp.geographyservice.application.port.in;

import com.lmp.geographyservice.domain.model.Country;

public interface GetCountryByIso3UseCase {

    Country getCountryByIso3(String iso3);

}
