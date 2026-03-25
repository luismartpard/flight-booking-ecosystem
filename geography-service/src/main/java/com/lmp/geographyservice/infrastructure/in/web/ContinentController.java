package com.lmp.geographyservice.infrastructure.in.web;

import com.lmp.flightbookingcommon.infrastructure.pagination.PageQuery;
import com.lmp.flightbookingcommon.infrastructure.pagination.PageResult;
import com.lmp.flightbookingcommon.infrastructure.pagination.SortDirection;
import com.lmp.flightbookingcommon.infrastructure.web.mapper.PageResultMapper;
import com.lmp.flightbookingcommon.infrastructure.web.response.PageResponse;
import com.lmp.geographyservice.application.port.in.GetContinentByCodeUseCase;
import com.lmp.geographyservice.application.port.in.GetContinentsUseCase;
import com.lmp.geographyservice.application.query.ContinentSortField;
import com.lmp.geographyservice.domain.model.Continent;
import com.lmp.geographyservice.infrastructure.in.web.dto.ContinentResponse;
import com.lmp.geographyservice.infrastructure.in.web.mapper.ContinentWebMapper;
import jakarta.validation.constraints.Pattern;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@Validated
@RestController
@RequestMapping("/api/v1/continents")
public class ContinentController {

    private final GetContinentsUseCase getContinentsUseCase;

    private final GetContinentByCodeUseCase getContinentByCodeUseCase;

    private final ContinentWebMapper continentWebMapper;

    public ContinentController(GetContinentsUseCase getContinentsUseCase, GetContinentByCodeUseCase getContinentByCodeUseCase, ContinentWebMapper continentWebMapper) {
        this.getContinentsUseCase = getContinentsUseCase;
        this.getContinentByCodeUseCase = getContinentByCodeUseCase;
        this.continentWebMapper = continentWebMapper;
    }


    @GetMapping("/code/{code}")
    public ResponseEntity<ContinentResponse> getContinentByCode(
            @Pattern(regexp = "^[A-Za-z]{2}$", message = "{continent.code.invalid}")
            @PathVariable String code) {

        Continent continent = getContinentByCodeUseCase.getContinentByCode(code);
        ContinentResponse response = continentWebMapper.toResponse(continent);

        return ResponseEntity.ok(response);

    }

    @GetMapping
    public ResponseEntity<PageResponse<ContinentResponse>> getContinents(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "20") int size,
            @RequestParam(defaultValue = "CODE") String sortBy,
            @RequestParam(defaultValue = "ASC") SortDirection sortDirection
    ) {

        PageQuery<ContinentSortField> pageQuery = new PageQuery<>(
                page, size, ContinentSortField.from(sortBy), sortDirection
        );

        PageResult<Continent> continents = getContinentsUseCase.getContinents(pageQuery);
        PageResponse<ContinentResponse> response = PageResultMapper.map(
                continents, continentWebMapper::toResponse
        );
        return ResponseEntity.ok(response);

    }

}
