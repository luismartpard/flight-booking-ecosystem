package com.lmp.geographyservice.infrastructure.out.persistence.adapter;

import com.lmp.flightbookingcommon.infrastructure.pagination.PageQuery;
import com.lmp.flightbookingcommon.infrastructure.pagination.PageResult;
import com.lmp.flightbookingcommon.infrastructure.pagination.SortDirection;
import com.lmp.geographyservice.application.port.out.ContinentRepositoryPort;
import com.lmp.geographyservice.application.query.ContinentSortField;
import com.lmp.geographyservice.domain.model.Continent;
import com.lmp.geographyservice.infrastructure.out.persistence.mapper.ContinentPersistenceMapper;
import com.lmp.geographyservice.infrastructure.out.persistence.repository.ContinentRepositoryJpa;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class ContinentPersistenceAdapter implements ContinentRepositoryPort {

    private final ContinentRepositoryJpa continentRepository;

    public ContinentPersistenceAdapter(ContinentRepositoryJpa continentRepository) {
        this.continentRepository = continentRepository;
    }

    @Override
    public Optional<Continent> findByCode(String code) {
        return continentRepository.findByCode(code)
                .map(ContinentPersistenceMapper::toDomain);
    }

    @Override
    public PageResult<Continent> findAll(PageQuery<ContinentSortField> pageQuery) {

        Pageable pageable = PageRequest.of(
                pageQuery.page(),
                pageQuery.size(),
                buildSort(pageQuery)
        );

        Page<Continent> page = continentRepository.findAll(pageable)
                .map(ContinentPersistenceMapper::toDomain);

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

    private Sort buildSort(PageQuery<ContinentSortField> pageQuery) {

        Sort.Direction direction = pageQuery.sortDirection() == SortDirection.DESC
                ? Sort.Direction.DESC
                : Sort.Direction.ASC;

        return Sort.by(direction, mapSortField(pageQuery.sortBy()));

    }

    private String mapSortField(ContinentSortField sortField) {
        return switch (sortField) {
            case CODE -> "code";
        };
    }

}
