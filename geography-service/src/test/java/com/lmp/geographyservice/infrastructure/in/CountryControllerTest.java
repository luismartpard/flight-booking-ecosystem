package com.lmp.geographyservice.infrastructure.in;

import com.lmp.flightbookingcommon.common.exception.ResourceNotFoundException;
import com.lmp.flightbookingcommon.infrastructure.pagination.PageQuery;
import com.lmp.flightbookingcommon.infrastructure.pagination.PageResult;
import com.lmp.flightbookingcommon.infrastructure.pagination.SortDirection;
import com.lmp.flightbookingcommon.infrastructure.web.exception.GlobalExceptionHandler;
import com.lmp.geographyservice.application.port.in.GetCountriesUseCase;
import com.lmp.geographyservice.application.port.in.GetCountryByIso2UseCase;
import com.lmp.geographyservice.application.port.in.GetCountryByIso3UseCase;
import com.lmp.geographyservice.application.port.in.UpdateCountryTravelStatusUseCase;
import com.lmp.geographyservice.application.query.CountrySearchQuery;
import com.lmp.geographyservice.application.query.CountrySortField;
import com.lmp.geographyservice.domain.enums.TravelStatus;
import com.lmp.geographyservice.domain.model.Country;
import com.lmp.geographyservice.infrastructure.in.web.CountryController;
import com.lmp.geographyservice.infrastructure.in.web.dto.CountryResponse;
import com.lmp.geographyservice.infrastructure.in.web.mapper.CountryWebMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@Import(GlobalExceptionHandler.class)
@WebMvcTest(CountryController.class)
class CountryControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private GetCountryByIso2UseCase getCountryByIso2UseCase;

    @MockitoBean
    private GetCountryByIso3UseCase getCountryByIso3UseCase;

    @MockitoBean
    private GetCountriesUseCase getCountriesUseCase;

    @MockitoBean
    private UpdateCountryTravelStatusUseCase updateCountryTravelStatusUseCase;

    @MockitoBean
    private CountryWebMapper countryWebMapper;

    Country spain;
    CountryResponse spainResponse;

    @BeforeEach
    void setUp() {
        spain = new Country("ES", "ESP", 724, "Spain", "+34", "EUR",
                TravelStatus.AVAILABLE);
        spainResponse = new CountryResponse("ES", "ESP", 724, "Spain", "+34", "EUR",
                TravelStatus.AVAILABLE);
    }

    @Test
    void getCountry_ValidIso2_returns200() throws Exception {

        when(getCountryByIso2UseCase.getCountryByIso2("ES"))
                .thenReturn(spain);

        when(countryWebMapper.toResponse(spain))
                .thenReturn(new CountryResponse(spain.getIso2(), spain.getIso3(),
                        spain.getIsoNumeric(), spain.getDefaultName(), spain.getPhoneCode(),
                        spain.getCurrencyCode(), spain.getTravelStatus()));


        mockMvc.perform(get("/api/v1/countries/iso2/ES"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.iso2").value("ES"))
                .andExpect(jsonPath("$.iso3").value("ESP"))
                .andExpect(jsonPath("$.isoNumeric").value(724))
                .andExpect(jsonPath("$.defaultName").value("Spain"))
                .andExpect(jsonPath("$.currencyCode").value("EUR"))
                .andExpect(jsonPath("$.travelStatus").value("AVAILABLE"));


        verify(getCountryByIso2UseCase).getCountryByIso2("ES");
        verifyNoMoreInteractions(getCountryByIso2UseCase);

    }

    @Test
    void getCountry_NoExistingIso2_return404() throws Exception {

        when(getCountryByIso2UseCase.getCountryByIso2("XX"))
                .thenThrow(new ResourceNotFoundException("country.iso2.not.found"));

        mockMvc.perform(get("/api/v1/countries/iso2/XX"))
                .andExpect(status().isNotFound());

        verify(getCountryByIso2UseCase).getCountryByIso2("XX");
        verifyNoInteractions(countryWebMapper);
        verifyNoMoreInteractions(getCountryByIso2UseCase);

    }

    @Test
    void getCountry_ValidIso3_returns200() throws Exception {

        when(getCountryByIso3UseCase.getCountryByIso3("ESP"))
                .thenReturn(spain);

        when(countryWebMapper.toResponse(spain))
                .thenReturn(new CountryResponse(spain.getIso2(), spain.getIso3(),
                        spain.getIsoNumeric(), spain.getDefaultName(), spain.getPhoneCode(),
                        spain.getCurrencyCode(), spain.getTravelStatus()));

        mockMvc.perform(get("/api/v1/countries/iso3/ESP"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.iso2").value("ES"))
                .andExpect(jsonPath("$.iso3").value("ESP"))
                .andExpect(jsonPath("$.isoNumeric").value(724))
                .andExpect(jsonPath("$.defaultName").value("Spain"))
                .andExpect(jsonPath("$.currencyCode").value("EUR"))
                .andExpect(jsonPath("$.travelStatus").value("AVAILABLE"));


        verify(getCountryByIso3UseCase).getCountryByIso3("ESP");
        verifyNoMoreInteractions(getCountryByIso3UseCase);

    }

    @Test
    void getCountry_NoExistingIso3_returns404() throws Exception {

        when(getCountryByIso3UseCase.getCountryByIso3("XXX"))
                .thenThrow(new ResourceNotFoundException("country.iso3.not.found"));

        mockMvc.perform(get("/api/v1/countries/iso3/XXX"))
                .andExpect(status().isNotFound());

        verify(getCountryByIso3UseCase).getCountryByIso3("XXX");
        verifyNoInteractions(countryWebMapper);
        verifyNoMoreInteractions(getCountryByIso3UseCase);

    }

    @Test
    void searchCountries_AllFilters_returns200() throws Exception {

        PageResult<Country> pageResult = new PageResult<>(
                List.of(spain),
                0,
                10,
                1,
                1,
                true,
                true,
                false,
                false
        );

        when(getCountriesUseCase.searchCountries(
                any(CountrySearchQuery.class),
                any(PageQuery.class)))
                .thenReturn(pageResult);

        when(countryWebMapper.toResponse(spain))
                .thenReturn(spainResponse);

        mockMvc.perform(get("/api/v1/countries/search")
                        .param("continentCode", "EU")
                        .param("currencyCode", "EUR")
                        .param("travelStatus", "AVAILABLE")
                        .param("page", "0")
                        .param("size", "10")
                        .param("sort", "iso2,asc"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.totalElements").value(1))
                .andExpect(jsonPath("$.content[0].iso2").value("ES"))
                .andExpect(jsonPath("$.content[0].iso3").value("ESP"))
                .andExpect(jsonPath("$.content[0].defaultName").value("Spain"))
                .andExpect(jsonPath("$.content[0].isoNumeric").value(724))
                .andExpect(jsonPath("$.content[0].phoneCode").value("+34"))
                .andExpect(jsonPath("$.content[0].currencyCode").value("EUR"))
                .andExpect(jsonPath("$.content[0].travelStatus").value("AVAILABLE"));

        ArgumentCaptor<CountrySearchQuery> queryCaptor =
                ArgumentCaptor.forClass(CountrySearchQuery.class);

        @SuppressWarnings("unchecked")
        ArgumentCaptor<PageQuery<CountrySortField>> pageQueryCaptor =
                ArgumentCaptor.forClass((Class) PageQuery.class);

        verify(getCountriesUseCase).searchCountries(
                queryCaptor.capture(),
                pageQueryCaptor.capture()
        );
        verify(countryWebMapper).toResponse(spain);
        verifyNoMoreInteractions(getCountriesUseCase, countryWebMapper);

        CountrySearchQuery usedQuery = queryCaptor.getValue();
        assertThat(usedQuery.continentCode()).isEqualTo("EU");
        assertThat(usedQuery.currencyCode()).isEqualTo("EUR");
        assertThat(usedQuery.travelStatus()).isEqualTo(TravelStatus.AVAILABLE);

        PageQuery<CountrySortField> usedPageQuery = pageQueryCaptor.getValue();
        assertThat(usedPageQuery.page()).isZero();
        assertThat(usedPageQuery.size()).isEqualTo(10);
        assertThat(usedPageQuery.sortBy()).isEqualTo(CountrySortField.ISO2);
        assertThat(usedPageQuery.sortDirection()).isEqualTo(SortDirection.ASC);
    }

    @Test
    void searchCountries_NoFilters_returns200() throws Exception {

        PageResult<Country> pageResult = new PageResult<>(
                List.of(spain),
                0,
                10,
                1,
                1,
                true,
                true,
                false,
                false
        );

        when(getCountriesUseCase.searchCountries(
                any(CountrySearchQuery.class),
                any(PageQuery.class)))
                .thenReturn(pageResult);

        when(countryWebMapper.toResponse(spain))
                .thenReturn(spainResponse);

        mockMvc.perform(get("/api/v1/countries/search")
                        .param("page", "0")
                        .param("size", "10")
                        .param("sort", "iso2,asc"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.totalElements").value(1))
                .andExpect(jsonPath("$.content[0].iso2").value("ES"))
                .andExpect(jsonPath("$.content[0].iso3").value("ESP"))
                .andExpect(jsonPath("$.content[0].defaultName").value("Spain"))
                .andExpect(jsonPath("$.content[0].isoNumeric").value(724))
                .andExpect(jsonPath("$.content[0].phoneCode").value("+34"))
                .andExpect(jsonPath("$.content[0].currencyCode").value("EUR"))
                .andExpect(jsonPath("$.content[0].travelStatus").value("AVAILABLE"));

        ArgumentCaptor<CountrySearchQuery> queryCaptor =
                ArgumentCaptor.forClass(CountrySearchQuery.class);

        @SuppressWarnings("unchecked")
        ArgumentCaptor<PageQuery<CountrySortField>> pageQueryCaptor =
                ArgumentCaptor.forClass((Class) PageQuery.class);

        verify(getCountriesUseCase).searchCountries(
                queryCaptor.capture(),
                pageQueryCaptor.capture()
        );
        verify(countryWebMapper).toResponse(spain);
        verifyNoMoreInteractions(getCountriesUseCase, countryWebMapper);

        CountrySearchQuery usedQuery = queryCaptor.getValue();
        assertThat(usedQuery.continentCode()).isNull();
        assertThat(usedQuery.currencyCode()).isNull();
        assertThat(usedQuery.travelStatus()).isNull();

        PageQuery<CountrySortField> usedPageQuery = pageQueryCaptor.getValue();
        assertThat(usedPageQuery.page()).isZero();
        assertThat(usedPageQuery.size()).isEqualTo(10);
        assertThat(usedPageQuery.sortBy()).isEqualTo(CountrySortField.ISO2);
        assertThat(usedPageQuery.sortDirection()).isEqualTo(SortDirection.ASC);
    }

    @Test
    void updateTravelStatus_when_valid_request_calls_service_and_returns_200() throws Exception {

        mockMvc.perform(patch("/api/v1/countries/{iso2}/travelstatus", "es")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                                {"travelStatus":"SUSPENDED"}
                                """))
                .andExpect(status().isOk());

        verify(updateCountryTravelStatusUseCase).updateCountryTravelStatus("es", TravelStatus.SUSPENDED);
        verifyNoMoreInteractions(updateCountryTravelStatusUseCase);

    }

    @Test
    void updateTravelStatus_when_iso2_invalid_returns_400_and_does_not_call_service() throws Exception {

        mockMvc.perform(patch("/api/v1/countries/{iso2}/travelstatus", "e")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                                {"travelStatus":null}
                                """))
                .andExpect(status().isBadRequest());

        verifyNoInteractions(updateCountryTravelStatusUseCase);

    }

    @Test
    void updateTravelStatus_when_body_missing_field_returns_400_and_does_not_call_service() throws Exception {

        mockMvc.perform(patch("/api/v1/countries/{iso2}/travelstatus", "es")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                                {}
                                """))
                .andExpect(status().isBadRequest());

        verifyNoInteractions(updateCountryTravelStatusUseCase);

    }

    @Test
    void updatedTravelStatus_when_malformed_json_returns_400_and_doesNotCallService() throws Exception {

        mockMvc.perform(patch("/api/v1/countries/{iso2}/travelstatus", "es")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                                {"travelStatus:null}
                                """))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.messageKey").value("error.request.malformed_json"));

        verify(updateCountryTravelStatusUseCase, never()).updateCountryTravelStatus(anyString(), any());

    }


}
