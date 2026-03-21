package com.lmp.geographyservice.application;

import com.lmp.flightbookingcommon.common.exception.ResourceNotFoundException;
import com.lmp.geographyservice.application.port.out.CountryRepositoryPort;
import com.lmp.geographyservice.application.usecase.GetCountryByIso3UseCaseImpl;
import com.lmp.geographyservice.domain.model.Country;
import com.lmp.geographyservice.fixtures.CountryTestFactory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class GetCountryByIso3UseTest {

    @Mock
    private CountryRepositoryPort countryRepositoryPort;

    @InjectMocks
    private GetCountryByIso3UseCaseImpl getCountryByIso3UseCaseImpl;

    Country spain;

    @BeforeEach
    void setUp() {
        spain = CountryTestFactory.builder().build();
    }

    @Test
    void getCountryByIso3_existingIso3_returnResponse() {

        when(countryRepositoryPort.findByIso3("ESP"))
                .thenReturn(Optional.of(spain));


        Country expected = new Country(spain.getIso2(), spain.getIso3(), spain.getIsoNumeric(),
                spain.getDefaultName(), spain.getPhoneCode(), spain.getCurrencyCode(), spain.getTravelStatus());

        Country result = getCountryByIso3UseCaseImpl.getCountryByIso3("ESP");

        assertThat(result).isEqualTo(expected);

        verify(countryRepositoryPort).findByIso3("ESP");
        verifyNoMoreInteractions(countryRepositoryPort);

    }

    @Test
    void getCountryByIso3_nonExistingIso3_shouldThrowResourceNotFoundException() {

        when(countryRepositoryPort.findByIso3("ESP"))
                .thenReturn(Optional.empty());

        assertThatThrownBy(() -> getCountryByIso3UseCaseImpl.getCountryByIso3("ESP"))
                .isInstanceOf(ResourceNotFoundException.class)
                .hasMessageContaining("country.iso3.not.found");

        verify(countryRepositoryPort).findByIso3("ESP");
        verifyNoMoreInteractions(countryRepositoryPort);

    }

    @Test
    void getCountryByIso3_whenIso3ContainsSpacesAndLowercase_shouldNormalizeAndReturnContinent() {

        when(countryRepositoryPort.findByIso3("ESP"))
                .thenReturn(Optional.of(spain));

        Country result = getCountryByIso3UseCaseImpl.getCountryByIso3(" esp ");

        assertThat(result).isEqualTo(spain);

        verify(countryRepositoryPort).findByIso3("ESP");
        verifyNoMoreInteractions(countryRepositoryPort);

    }

    @Test
    void getCountryByIso3_whenIso3IsNull_shouldThrowResourceNotFoundException() {

        when(countryRepositoryPort.findByIso3(null))
                .thenReturn(Optional.empty());

        assertThatThrownBy(() -> getCountryByIso3UseCaseImpl.getCountryByIso3(null))
                .isInstanceOf(ResourceNotFoundException.class)
                .hasMessageContaining("country.iso3.not.found");

        verify(countryRepositoryPort).findByIso3(null);
        verifyNoMoreInteractions(countryRepositoryPort);


    }

}
