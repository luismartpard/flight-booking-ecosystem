package com.lmp.geographyservice.application.port.in;

import com.lmp.geographyservice.domain.model.Continent;

public interface GetContinentByCodeUseCase {

    Continent getContinentByCode(String code);

}
