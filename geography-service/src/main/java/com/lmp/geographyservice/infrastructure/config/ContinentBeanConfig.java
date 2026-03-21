package com.lmp.geographyservice.infrastructure.config;

import com.lmp.geographyservice.application.port.in.GetContinentByCodeUseCase;
import com.lmp.geographyservice.application.port.in.GetContinentsUseCase;
import com.lmp.geographyservice.application.port.out.ContinentRepositoryPort;
import com.lmp.geographyservice.application.usecase.GetContinentByCodeUseCaseImpl;
import com.lmp.geographyservice.application.usecase.GetContinentsUseCaseImpl;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ContinentBeanConfig {

    @Bean
    public GetContinentByCodeUseCase getContinentByCodeUseCase(ContinentRepositoryPort continentRepositoryPort) {
        return new GetContinentByCodeUseCaseImpl(continentRepositoryPort);
    }

    @Bean
    public GetContinentsUseCase getContinentsUseCase(ContinentRepositoryPort continentRepositoryPort) {
        return new GetContinentsUseCaseImpl(continentRepositoryPort);
    }

}
