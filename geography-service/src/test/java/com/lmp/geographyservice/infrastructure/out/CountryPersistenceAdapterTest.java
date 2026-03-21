package com.lmp.geographyservice.infrastructure.out;

import com.lmp.flightbookingcommon.infrastructure.pagination.PageQuery;
import com.lmp.flightbookingcommon.infrastructure.pagination.PageResult;
import com.lmp.flightbookingcommon.infrastructure.pagination.SortDirection;
import com.lmp.geographyservice.application.query.CountrySearchQuery;
import com.lmp.geographyservice.application.query.CountrySortField;
import com.lmp.geographyservice.domain.enums.TravelStatus;
import com.lmp.geographyservice.domain.model.Country;
import com.lmp.geographyservice.fixtures.CountryJpaEntityTestFactory;
import com.lmp.geographyservice.infrastructure.out.persistence.adapter.CountryPersistenceAdapter;
import com.lmp.geographyservice.infrastructure.out.persistence.entity.CountryJpaEntity;
import com.lmp.geographyservice.infrastructure.out.persistence.repository.CountryRepositoryJpa;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.*;
import org.springframework.data.jpa.domain.Specification;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CountryPersistenceAdapterTest {

    @Mock
    private CountryRepositoryJpa countryRepositoryJpa;

    @InjectMocks
    private CountryPersistenceAdapter countryPersistenceAdapter;

    CountryJpaEntity countryJpaEntity;

    @BeforeEach
    void setUp() {
        countryJpaEntity = CountryJpaEntityTestFactory.builder().build();
    }

    @Test
    void findByIso2_whenCountryExists_shouldReturnCountry() {

        when(countryRepositoryJpa.findByIso2("ES"))
                .thenReturn(Optional.of(countryJpaEntity));

        Optional<Country> country = countryPersistenceAdapter.findByIso2("ES");

        assertTrue(country.isPresent());
        assertEquals("ES", country.get().getIso2());

        verify(countryRepositoryJpa).findByIso2("ES");
        verifyNoMoreInteractions(countryRepositoryJpa);

    }

    @Test
    void findByIso3_whenCountryExists_shouldReturnCountry() {

        when(countryRepositoryJpa.findByIso3("ESP"))
                .thenReturn(Optional.of(countryJpaEntity));

        Optional<Country> country = countryPersistenceAdapter.findByIso3("ESP");

        assertTrue(country.isPresent());
        assertEquals("ESP", country.get().getIso3());

        verify(countryRepositoryJpa).findByIso3("ESP");
        verifyNoMoreInteractions(countryRepositoryJpa);

    }

    @Test
    void findAll_whenCalled_shouldReturnPageResult() {

        CountrySearchQuery countrySearchQuery = new CountrySearchQuery(
                "EU", "EUR", TravelStatus.AVAILABLE
        );

        PageQuery<CountrySortField> pageQuery = new PageQuery<>(
                0,
                10,
                CountrySortField.DEFAULT_NAME,
                SortDirection.ASC
        );

        List<CountryJpaEntity> countries = List.of(countryJpaEntity);

        Page<CountryJpaEntity> countryPage = new PageImpl<>(
                countries,
                PageRequest.of(0, 10, Sort.by(Sort.Direction.ASC, "iso2")),
                1
        );

        when(countryRepositoryJpa.findAll(any(Specification.class), any(Pageable.class)))
                .thenReturn(countryPage);


        PageResult<Country> result = countryPersistenceAdapter.findAll(countrySearchQuery, pageQuery);

        assertThat(result).isNotNull();
        assertThat(result.content()).hasSize(1);
        assertThat(result.page()).isZero();
        assertThat(result.size()).isEqualTo(10);
        assertThat(result.totalElements()).isEqualTo(1);
        assertThat(result.totalPages()).isEqualTo(1);
        assertThat(result.first()).isTrue();
        assertThat(result.last()).isTrue();
        assertThat(result.hasNext()).isFalse();
        assertThat(result.hasPrevious()).isFalse();

        verify(countryRepositoryJpa).findAll(any(Specification.class), any(Pageable.class));

    }

    @Test
    void updateCountryTravelStatus_whenCalled_shouldDelegateToJpaRepository() {

        countryPersistenceAdapter.updateCountryTravelStatus("ES", TravelStatus.AVAILABLE);

        verify(countryRepositoryJpa).updateCountryTravelStatus("ES", TravelStatus.AVAILABLE);
        verifyNoMoreInteractions(countryRepositoryJpa);

    }

    @Test
    void findAll_whenSortDirectionIsDesc_shouldPassDescendingSortToRepository() {
        CountrySearchQuery countrySearchQuery = new CountrySearchQuery(null, null, null);
        PageQuery<CountrySortField> pageQuery = new PageQuery<>(
                0,
                10,
                CountrySortField.ISO2,
                SortDirection.DESC
        );

        when(countryRepositoryJpa.findAll(any(Specification.class), any(Pageable.class)))
                .thenReturn(Page.empty());

        countryPersistenceAdapter.findAll(countrySearchQuery, pageQuery);

        ArgumentCaptor<Pageable> pageableCaptor = ArgumentCaptor.forClass(Pageable.class);

        verify(countryRepositoryJpa).findAll(any(Specification.class), pageableCaptor.capture());

        Pageable pageable = pageableCaptor.getValue();

        assertThat(pageable.getPageNumber()).isZero();
        assertThat(pageable.getPageSize()).isEqualTo(10);
        assertThat(pageable.getSort().getOrderFor(CountrySortField.ISO2.getProperty())).isNotNull();
        assertThat(Objects.requireNonNull(pageable.getSort().getOrderFor(CountrySortField.ISO2.getProperty())).getDirection())
                .isEqualTo(Sort.Direction.DESC);
    }

    @Test
    void findAll_whenSortFieldIsNull_shouldUseDefaultSortField() {
        CountrySearchQuery countrySearchQuery = new CountrySearchQuery(null, null, null);
        PageQuery<CountrySortField> pageQuery = new PageQuery<>(
                0,
                10,
                null,
                SortDirection.ASC
        );

        when(countryRepositoryJpa.findAll(any(Specification.class), any(Pageable.class)))
                .thenReturn(Page.empty());

        countryPersistenceAdapter.findAll(countrySearchQuery, pageQuery);

        ArgumentCaptor<Pageable> pageableCaptor = ArgumentCaptor.forClass(Pageable.class);

        verify(countryRepositoryJpa).findAll(any(Specification.class), pageableCaptor.capture());

        Pageable pageable = pageableCaptor.getValue();

        assertThat(pageable.getSort().getOrderFor(CountrySortField.DEFAULT_NAME.getProperty())).isNotNull();
        assertThat(pageable.getSort().getOrderFor(CountrySortField.DEFAULT_NAME.getProperty()).getDirection())
                .isEqualTo(Sort.Direction.ASC);
    }

}
