package com.lmp.geographyservice.application;

import com.lmp.flightbookingcommon.common.exception.ResourceNotFoundException;
import com.lmp.geographyservice.application.port.out.CountryRepositoryPort;
import com.lmp.geographyservice.application.usecase.GetCountryByIso2UseCaseImpl;
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
class GetCountryByIso2UseTest {

    @Mock
    private CountryRepositoryPort countryRepositoryPort;

    @InjectMocks
    private GetCountryByIso2UseCaseImpl getCountryByIso2UseCaseImpl;

    Country spain;

    @BeforeEach
    void setUp() {
        spain = CountryTestFactory.builder().build();
    }

    @Test
    void getCountryByIso2_existingIso2_returnResponse() {

        when(countryRepositoryPort.findByIso2("ES"))
                .thenReturn(Optional.of(spain));

        Country result =  getCountryByIso2UseCaseImpl.getCountryByIso2("ES");

        assertThat(result).isEqualTo(spain);

        verify(countryRepositoryPort).findByIso2("ES");
        verifyNoMoreInteractions(countryRepositoryPort);
    }

    @Test
    void getCountryByIso2_nonExistingIso2_shouldThrowResourceNotFoundException() {

        when(countryRepositoryPort.findByIso2("ES"))
                .thenReturn(Optional.empty());

        assertThatThrownBy(() -> getCountryByIso2UseCaseImpl.getCountryByIso2("ES"))
                .isInstanceOf(ResourceNotFoundException.class)
                .hasMessageContaining("country.iso2.not.found");

        verify(countryRepositoryPort).findByIso2("ES");
        verifyNoMoreInteractions(countryRepositoryPort);

    }

    @Test
    void getCountryByIso2_whenIso2ContainsSpacesAndLowercase_shouldReturnCountry() {

        when(countryRepositoryPort.findByIso2("ES"))
                .thenReturn(Optional.of(spain));

        Country result =  getCountryByIso2UseCaseImpl.getCountryByIso2(" es ");

        assertThat(result).isEqualTo(spain);

        verify(countryRepositoryPort).findByIso2("ES");
        verifyNoMoreInteractions(countryRepositoryPort);


    }

    @Test
    void getCountryByIso2_whenIso2IsNull_shouldThrowResourceNotFoundException() {

        when(countryRepositoryPort.findByIso2(null))
                .thenReturn(Optional.empty());

        assertThatThrownBy(() -> getCountryByIso2UseCaseImpl.getCountryByIso2(null))
                .isInstanceOf(ResourceNotFoundException.class)
                .hasMessageContaining("country.iso2.not.found");

        verify(countryRepositoryPort).findByIso2(null);
        verifyNoMoreInteractions(countryRepositoryPort);

    }

}
