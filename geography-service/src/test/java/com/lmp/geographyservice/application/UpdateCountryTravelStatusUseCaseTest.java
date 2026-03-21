package com.lmp.geographyservice.application;

import com.lmp.flightbookingcommon.common.exception.BadRequestException;
import com.lmp.geographyservice.application.port.out.CountryRepositoryPort;
import com.lmp.geographyservice.application.usecase.UpdateCountryTravelStatusUseCaseImpl;
import com.lmp.geographyservice.domain.enums.TravelStatus;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoInteractions;

@ExtendWith(MockitoExtension.class)
class UpdateCountryTravelStatusUseCaseTest {

    @Mock
    private CountryRepositoryPort countryRepositoryPort;

    @InjectMocks
    private UpdateCountryTravelStatusUseCaseImpl updateCountryTravelStatusUseCase;

    @Test
    void updateTravelStatus_when_iso2_null_throws() throws  Exception {

        assertThrows(BadRequestException.class,
                () -> updateCountryTravelStatusUseCase.updateCountryTravelStatus(null, TravelStatus.SUSPENDED));

        verifyNoInteractions(countryRepositoryPort);

    }

    @Test
    void updateTravelStatus_when_travelStatus_null_throws() throws  Exception {

        assertThrows(BadRequestException.class,
                () -> updateCountryTravelStatusUseCase.updateCountryTravelStatus("ES", null));

    }

    void updateTravelStatus_valid_input_updates_status() throws Exception {

        updateCountryTravelStatusUseCase.updateCountryTravelStatus("ES", TravelStatus.SUSPENDED);

        verify(countryRepositoryPort).updateCountryTravelStatus("ES", TravelStatus.SUSPENDED);

    }

}
