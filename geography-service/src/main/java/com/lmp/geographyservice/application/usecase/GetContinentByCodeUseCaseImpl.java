package com.lmp.geographyservice.application.usecase;

import com.lmp.flightbookingcommon.common.exception.ResourceNotFoundException;
import com.lmp.geographyservice.application.port.in.GetContinentByCodeUseCase;
import com.lmp.geographyservice.application.port.out.ContinentRepositoryPort;
import com.lmp.geographyservice.domain.model.Continent;

import java.util.Locale;

public class GetContinentByCodeUseCaseImpl implements GetContinentByCodeUseCase {

    private final ContinentRepositoryPort continentRepositoryPort;

    public GetContinentByCodeUseCaseImpl(ContinentRepositoryPort continentRepositoryPort) {
        this.continentRepositoryPort = continentRepositoryPort;
    }

    @Override
    public Continent getContinentByCode(String code) {

        String normalized = code == null ? null : code.trim().toUpperCase(Locale.ROOT);

        return continentRepositoryPort.findByCode(normalized)
                .orElseThrow(() -> new ResourceNotFoundException("continent.code.not.found", normalized));

    }

}
