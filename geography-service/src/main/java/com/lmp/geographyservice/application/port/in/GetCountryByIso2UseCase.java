package com.lmp.geographyservice.application.port.in;

import com.lmp.geographyservice.domain.model.Country;

public interface GetCountryByIso2UseCase {

    Country getCountryByIso2(String iso2);

}
