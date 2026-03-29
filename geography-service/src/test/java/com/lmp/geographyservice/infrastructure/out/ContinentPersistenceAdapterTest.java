package com.lmp.geographyservice.infrastructure.out;

import com.lmp.flightbookingcommon.infrastructure.pagination.PageQuery;
import com.lmp.flightbookingcommon.infrastructure.pagination.PageResult;
import com.lmp.flightbookingcommon.infrastructure.pagination.SortDirection;
import com.lmp.geographyservice.application.query.ContinentSortField;
import com.lmp.geographyservice.domain.model.Continent;
import com.lmp.geographyservice.infrastructure.out.persistence.adapter.ContinentPersistenceAdapter;
import com.lmp.geographyservice.infrastructure.out.persistence.entity.ContinentJpaEntity;
import com.lmp.geographyservice.infrastructure.out.persistence.repository.ContinentRepositoryJpa;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ContinentPersistenceAdapterTest {

    @Mock
    private ContinentRepositoryJpa continentRepositoryJpa;

    @InjectMocks
    private ContinentPersistenceAdapter continentPersistenceAdapter;

    @Test
    void should_return_continent_code() {

        ContinentJpaEntity continentJpaEntity = ContinentJpaEntity.of(
                "EU", "Europe"
        );

        when(continentRepositoryJpa.findByCode("EU"))
                .thenReturn(Optional.of(continentJpaEntity));

        Optional<Continent> continent = continentPersistenceAdapter.findByCode("EU");

        assertTrue(continent.isPresent());
        assertEquals("EU", continent.get().getCode());
        assertEquals("Europe", continent.get().getName());

    }

    @Test
    void should_return_page_of_continents() {

        ContinentJpaEntity continentJpaEntity = ContinentJpaEntity.of(
                "EU", "Europe"
        );

        Page<ContinentJpaEntity> page =
                new PageImpl<>(List.of(continentJpaEntity));

        when(continentRepositoryJpa.findAll(any(Pageable.class)))
                .thenReturn(page);

        PageQuery<ContinentSortField> query = new PageQuery<>(
                0,
                10,
                ContinentSortField.CODE, SortDirection.ASC
        );

        PageResult<Continent> result = continentPersistenceAdapter.findAll(query);

        assertEquals(1, result.content().size());
        assertEquals("EU", result.content().getFirst().getCode());
        assertEquals("Europe", result.content().getFirst().getName());

    }

    @Test
    void should_use_default_sort_when_sortBy_is_desc() {

        ContinentJpaEntity continentJpaEntity = ContinentJpaEntity.of(
                "EU", "Europe"
        );

        Page<ContinentJpaEntity> page = new PageImpl<>(
                List.of(continentJpaEntity)
        );

        when(continentRepositoryJpa.findAll(any(Pageable.class)))
                .thenReturn(page);

        PageQuery<ContinentSortField> query = new PageQuery<>(
                0,10,
                ContinentSortField.CODE, SortDirection.DESC
        );

        continentPersistenceAdapter.findAll(query);
        verify(continentRepositoryJpa).findAll(any(Pageable.class));

    }

    @Test
    void should_use_default_sort_when_sortBy_is_asc() {

        ContinentJpaEntity continentJpaEntity = ContinentJpaEntity.of(
                "EU", "Europe"
        );

        Page<ContinentJpaEntity> page = new PageImpl<>(
                List.of(continentJpaEntity)
        );

        when(continentRepositoryJpa.findAll(any(Pageable.class)))
                .thenReturn(page);

        PageQuery<ContinentSortField> query = new PageQuery<>(
                0,10,
                ContinentSortField.CODE, SortDirection.ASC
        );

        continentPersistenceAdapter.findAll(query);
        verify(continentRepositoryJpa).findAll(any(Pageable.class));

    }

}
