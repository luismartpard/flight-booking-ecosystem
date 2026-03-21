package com.lmp.geographyservice.infrastructure.in.web;

import com.lmp.flightbookingcommon.infrastructure.pagination.PageQuery;
import com.lmp.flightbookingcommon.infrastructure.pagination.PageQueryMapper;
import com.lmp.flightbookingcommon.infrastructure.pagination.PageResult;
import com.lmp.flightbookingcommon.infrastructure.web.mapper.PageResultMapper;
import com.lmp.flightbookingcommon.infrastructure.web.response.PageResponse;
import com.lmp.geographyservice.application.port.in.GetCountriesUseCase;
import com.lmp.geographyservice.application.port.in.GetCountryByIso2UseCase;
import com.lmp.geographyservice.application.port.in.GetCountryByIso3UseCase;
import com.lmp.geographyservice.application.port.in.UpdateCountryTravelStatusUseCase;
import com.lmp.geographyservice.application.query.CountrySearchQuery;
import com.lmp.geographyservice.application.query.CountrySortField;
import com.lmp.geographyservice.domain.enums.TravelStatus;
import com.lmp.geographyservice.domain.model.Country;
import com.lmp.geographyservice.infrastructure.in.web.dto.CountryResponse;
import com.lmp.geographyservice.infrastructure.in.web.dto.CountryTravelStatusUpdateRequest;
import com.lmp.geographyservice.infrastructure.in.web.mapper.CountryWebMapper;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Pattern;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@Validated
@RestController
@RequestMapping("/api/v1/countries")
public class CountryController {

    private final GetCountryByIso2UseCase getCountryByIso2UseCase;

    private final GetCountryByIso3UseCase getCountryByIso3UseCase;

    private final GetCountriesUseCase getCountriesUseCase;

    private final UpdateCountryTravelStatusUseCase updateCountryTravelStatusUseCase;

    private final CountryWebMapper countryWebMapper;

    public CountryController(GetCountryByIso2UseCase getCountryByIso2UseCase, GetCountryByIso3UseCase getCountryByIso3UseCase,
                             GetCountriesUseCase getCountriesUseCase, UpdateCountryTravelStatusUseCase updateCountryTravelStatusUseCase,
                             CountryWebMapper countryWebMapper) {
        this.getCountryByIso2UseCase = getCountryByIso2UseCase;
        this.getCountryByIso3UseCase = getCountryByIso3UseCase;
        this.getCountriesUseCase = getCountriesUseCase;
        this.updateCountryTravelStatusUseCase = updateCountryTravelStatusUseCase;
        this.countryWebMapper = countryWebMapper;
    }


    @GetMapping("/iso2/{iso2}")
    public ResponseEntity<CountryResponse> getCountryByIso2(
            @Pattern(regexp = "^[A-Za-z]{2}$", message = "{country.iso2.invalid}")
            @PathVariable  String iso2) {

        Country country = getCountryByIso2UseCase.getCountryByIso2(iso2);
        CountryResponse response = countryWebMapper.toResponse(country);

        return ResponseEntity.ok(response);

    }

    @GetMapping("/iso3/{iso3}")
    public ResponseEntity<CountryResponse> getCountryByIso3(
            @Pattern(regexp = "^[A-Za-z]{3}$", message = "{country.iso3.invalid}")
            @PathVariable  String iso3) {

        Country country = getCountryByIso3UseCase.getCountryByIso3(iso3);
        CountryResponse response = countryWebMapper.toResponse(country);

        return ResponseEntity.ok(response);

    }

    @GetMapping("/search")
    public ResponseEntity<PageResponse<CountryResponse>> getCountries(
            @RequestParam(required = false) String continentCode,
            @RequestParam(required = false) String currencyCode,
            @RequestParam(required = false) TravelStatus travelStatus,
            @PageableDefault(size = 20) Pageable pageable) {

        CountrySearchQuery countrySearchQuery = new CountrySearchQuery(
                continentCode, currencyCode, travelStatus
        );

        PageQuery<CountrySortField> pageQuery =
                PageQueryMapper.toPageQuery(pageable, CountrySortField::from, CountrySortField.DEFAULT_NAME);


        PageResult<Country> countries = getCountriesUseCase.searchCountries(
                countrySearchQuery, pageQuery
        );

        PageResponse<CountryResponse> response = PageResultMapper.map(
                countries,
                countryWebMapper::toResponse
        );

        return ResponseEntity.ok(response);

    }

    @PatchMapping("/{iso2}/travelstatus")
    public void updateTravelStatus(
            @Pattern(regexp = "^[A-Za-z]{2}$", message = "{country.iso2.invalid}")
            @PathVariable String iso2,
            @Valid @RequestBody CountryTravelStatusUpdateRequest request
    ) {

        updateCountryTravelStatusUseCase.updateCountryTravelStatus(iso2, request.travelStatus());

    }

}
