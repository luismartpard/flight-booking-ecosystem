package com.lmp.geographyservice.application;

import com.lmp.flightbookingcommon.common.exception.ResourceNotFoundException;
import com.lmp.geographyservice.application.port.out.ContinentRepositoryPort;
import com.lmp.geographyservice.application.usecase.GetContinentByCodeUseCaseImpl;
import com.lmp.geographyservice.domain.model.Continent;
import com.lmp.geographyservice.fixtures.ContinentTestFactory;
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
class GetContinentByCodeUseTest {

    @Mock
    private ContinentRepositoryPort continentRepositoryPort;

    @InjectMocks
    private GetContinentByCodeUseCaseImpl getContinentByCodeUseCaseImpl;

    Continent europe;

    @BeforeEach
    void setUp() {
        europe = ContinentTestFactory.builder().build();
    }

    @Test
    void getContinentByCode_whenCodeExists_shouldReturnContinent() {
        when(continentRepositoryPort.findByCode("EU"))
                .thenReturn(Optional.of(europe));

        Continent expected = new Continent(europe.getCode());

        Continent result = getContinentByCodeUseCaseImpl.getContinentByCode("EU");

        assertThat(result).isEqualTo(expected);
        verify(continentRepositoryPort).findByCode("EU");
        verifyNoMoreInteractions(continentRepositoryPort);

    }

    @Test
    void getContinentByCode_whenCodeContainsSpacesAndLowercase_shouldNormalizeAndReturnContinent() {

        when(continentRepositoryPort.findByCode("EU"))
                .thenReturn(Optional.of(europe));

        Continent result = getContinentByCodeUseCaseImpl.getContinentByCode(" eu ");

        assertThat(result).isEqualTo(europe);
        verify(continentRepositoryPort).findByCode("EU");
    }

    @Test
    void getCode_nonExistingCode_throwResourceNotFound() {

        when(continentRepositoryPort.findByCode("EU"))
                .thenReturn(Optional.empty());

        assertThatThrownBy(() -> getContinentByCodeUseCaseImpl.getContinentByCode("EU"))
                .isInstanceOf(ResourceNotFoundException.class)
                .hasMessageContaining("continent.code.not.found");

        verify(continentRepositoryPort).findByCode("EU");
        verifyNoMoreInteractions(continentRepositoryPort);

    }

    @Test
    void getContinentByCode_whenCodeIsNull_shouldThrowResourceNotFoundException() {

        when(continentRepositoryPort.findByCode(null))
                .thenReturn(Optional.empty());

        assertThatThrownBy(() -> getContinentByCodeUseCaseImpl.getContinentByCode(null))
                .isInstanceOf(ResourceNotFoundException.class)
                .hasMessageContaining("continent.code.not.found");

        verify(continentRepositoryPort).findByCode(null);
        verifyNoMoreInteractions(continentRepositoryPort);

    }

}
