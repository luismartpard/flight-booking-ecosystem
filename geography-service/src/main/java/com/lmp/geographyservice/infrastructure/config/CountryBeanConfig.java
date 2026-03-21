package com.lmp.geographyservice.infrastructure.config;

import com.lmp.geographyservice.application.port.in.GetCountriesUseCase;
import com.lmp.geographyservice.application.port.in.GetCountryByIso2UseCase;
import com.lmp.geographyservice.application.port.in.GetCountryByIso3UseCase;
import com.lmp.geographyservice.application.port.in.UpdateCountryTravelStatusUseCase;
import com.lmp.geographyservice.application.port.out.CountryRepositoryPort;
import com.lmp.geographyservice.application.usecase.GetCountriesUseCaseImpl;
import com.lmp.geographyservice.application.usecase.GetCountryByIso2UseCaseImpl;
import com.lmp.geographyservice.application.usecase.GetCountryByIso3UseCaseImpl;
import com.lmp.geographyservice.application.usecase.UpdateCountryTravelStatusUseCaseImpl;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class CountryBeanConfig {

    @Bean
    public GetCountryByIso2UseCase getCountryByIso2UseCase(CountryRepositoryPort countryRepositoryPort) {
        return new GetCountryByIso2UseCaseImpl(countryRepositoryPort);
    }

    @Bean
    public GetCountryByIso3UseCase getCountryByIso3UseCase(CountryRepositoryPort countryRepositoryPort) {
        return new GetCountryByIso3UseCaseImpl(countryRepositoryPort);
    }

    @Bean
    public GetCountriesUseCase getCountriesUseCase(CountryRepositoryPort countryRepositoryPort) {
        return new GetCountriesUseCaseImpl(countryRepositoryPort);
    }

    @Bean
    public UpdateCountryTravelStatusUseCase updateCountryTravelStatusUseCase(CountryRepositoryPort countryRepositoryPort) {
        return new UpdateCountryTravelStatusUseCaseImpl(countryRepositoryPort);
    }

}
