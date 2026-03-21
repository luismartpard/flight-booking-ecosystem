package com.lmp.geographyservice.infrastructure.out.persistence.adapter;

import com.lmp.flightbookingcommon.infrastructure.pagination.PageQuery;
import com.lmp.flightbookingcommon.infrastructure.pagination.PageResult;
import com.lmp.flightbookingcommon.infrastructure.pagination.SortDirection;
import com.lmp.geographyservice.application.port.out.CountryRepositoryPort;
import com.lmp.geographyservice.application.query.CountrySearchQuery;
import com.lmp.geographyservice.application.query.CountrySortField;
import com.lmp.geographyservice.domain.enums.TravelStatus;
import com.lmp.geographyservice.domain.model.Country;
import com.lmp.geographyservice.infrastructure.out.persistence.entity.CountryJpaEntity;
import com.lmp.geographyservice.infrastructure.out.persistence.mapper.CountryPersistenceMapper;
import com.lmp.geographyservice.infrastructure.out.persistence.repository.CountryRepositoryJpa;
import com.lmp.geographyservice.infrastructure.out.persistence.specification.CountrySpecification;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class CountryPersistenceAdapter implements CountryRepositoryPort {

    private final CountryRepositoryJpa countryRepositoryJpa;

    public CountryPersistenceAdapter(CountryRepositoryJpa countryRepositoryJpa) {
        this.countryRepositoryJpa = countryRepositoryJpa;
    }

    @Override
    public Optional<Country> findByIso2(String iso2) {
        return countryRepositoryJpa.findByIso2(iso2)
                .map(CountryPersistenceMapper::toDomain);
    }

    @Override
    public Optional<Country> findByIso3(String iso3) {
        return countryRepositoryJpa.findByIso3(iso3)
                .map(CountryPersistenceMapper::toDomain);
    }

    @Override
    public PageResult<Country> findAll(CountrySearchQuery countrySearchQuery, PageQuery<CountrySortField> pageQuery) {

        Specification<CountryJpaEntity> specification = Specification
                .where(CountrySpecification.hasContinentCode(countrySearchQuery.continentCode()))
                .and(CountrySpecification.hasCurrencyCode(countrySearchQuery.currencyCode()))
                .and(CountrySpecification.hasTravelStatus(countrySearchQuery.travelStatus()));

        Pageable pageable = PageRequest.of(
                pageQuery.page(),
                pageQuery.size(),
                buildSort(pageQuery)
        );

        Page<Country> page = countryRepositoryJpa.findAll(specification, pageable)
                .map(CountryPersistenceMapper::toDomain);

        return new PageResult<>(
                page.getContent(),
                page.getNumber(),
                page.getSize(),
                page.getTotalElements(),
                page.getTotalPages(),
                page.isFirst(),
                page.isLast(),
                page.hasNext(),
                page.hasPrevious()
        );

    }

    @Override
    public void updateCountryTravelStatus(String iso2, TravelStatus travelStatus) {
        countryRepositoryJpa.updateCountryTravelStatus(
                iso2,
                travelStatus
        );
    }

    private Sort buildSort(PageQuery<CountrySortField> pageQuery) {
        Sort.Direction direction = pageQuery.sortDirection() == SortDirection.DESC
                ? Sort.Direction.DESC
                : Sort.Direction.ASC;

        CountrySortField sortField = pageQuery.sortBy() == null
                ? CountrySortField.DEFAULT_NAME
                : pageQuery.sortBy();

        return Sort.by(direction, sortField.getProperty());
    }

}