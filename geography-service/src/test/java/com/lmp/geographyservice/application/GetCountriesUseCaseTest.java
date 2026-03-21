package com.lmp.geographyservice.application;

import com.lmp.flightbookingcommon.infrastructure.pagination.PageQuery;
import com.lmp.flightbookingcommon.infrastructure.pagination.PageResult;
import com.lmp.flightbookingcommon.infrastructure.pagination.SortDirection;
import com.lmp.geographyservice.application.port.out.CountryRepositoryPort;
import com.lmp.geographyservice.application.query.CountrySearchQuery;
import com.lmp.geographyservice.application.query.CountrySortField;
import com.lmp.geographyservice.application.usecase.GetCountriesUseCaseImpl;
import com.lmp.geographyservice.domain.enums.TravelStatus;
import com.lmp.geographyservice.domain.model.Country;
import com.lmp.geographyservice.fixtures.CountryTestFactory;
import com.lmp.geographyservice.infrastructure.in.web.mapper.CountryWebMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class GetCountriesUseCaseTest {

    @Mock
    private CountryRepositoryPort countryRepositoryPort;

    @Mock
    private CountryWebMapper countryWebMapper;

    @InjectMocks
    private GetCountriesUseCaseImpl getCountriesUseCaseImpl;

    private PageQuery<CountrySortField> pageQuery;

    Country spain;

    @BeforeEach
    void setUp() {
        spain = CountryTestFactory.builder().build();
        pageQuery = new PageQuery<>(
          0,
          10,
          CountrySortField.DEFAULT_NAME,
          SortDirection.ASC
        );
    }

    @ParameterizedTest
    @MethodSource("CountrySearchQueryProvider")
    void searchCountries_AllFilters_returnPagedResponse(CountrySearchQuery countrySearchQuery) {

        PageResult<Country> page = buildSingleCountryPage();

        when(countryRepositoryPort.findAll(countrySearchQuery, pageQuery))
                .thenReturn(page);

        PageResult<Country> response =
                getCountriesUseCaseImpl.searchCountries(countrySearchQuery, pageQuery);

        assertThat(response).isNotNull();
        assertThat(response.totalElements()).isEqualTo(1);
        assertThat(response.content()).hasSize(1);
        assertThat(response.content().getFirst().getIso2()).isEqualTo(spain.getIso2());

        verify(countryRepositoryPort).findAll(countrySearchQuery, pageQuery);
        verifyNoMoreInteractions(countryWebMapper);
        verifyNoMoreInteractions(countryRepositoryPort);
        clearInvocations(countryRepositoryPort);

    }

    private static Stream<CountrySearchQuery> CountrySearchQueryProvider() {
        return Stream.of(
                new CountrySearchQuery("EU", null, null),
                new CountrySearchQuery(null, "EUR", null),
                new CountrySearchQuery(null, null, TravelStatus.AVAILABLE),
                new CountrySearchQuery(null, null, null)
        );
    }

    private PageResult<Country> buildSingleCountryPage() {
        return new PageResult<>(
                List.of(spain),
                0,
                20,
                1L,
                1,
                true,
                true,
                false,
                false
        );
    }

}
