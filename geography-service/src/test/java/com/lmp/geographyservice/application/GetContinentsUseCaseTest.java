package com.lmp.geographyservice.application;

import com.lmp.flightbookingcommon.infrastructure.pagination.PageQuery;
import com.lmp.flightbookingcommon.infrastructure.pagination.PageResult;
import com.lmp.flightbookingcommon.infrastructure.pagination.SortDirection;
import com.lmp.flightbookingcommon.testutil.PageResultTestFactory;
import com.lmp.geographyservice.application.port.out.ContinentRepositoryPort;
import com.lmp.geographyservice.application.query.ContinentSortField;
import com.lmp.geographyservice.application.usecase.GetContinentsUseCaseImpl;
import com.lmp.geographyservice.domain.model.Continent;
import com.lmp.geographyservice.fixtures.ContinentTestFactory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class GetContinentsUseCaseTest {

    @Mock
    private ContinentRepositoryPort continentRepositoryPort;
    
    @InjectMocks
    private GetContinentsUseCaseImpl getContinentsUseCaseImpl;

    Continent europe;
    
    @BeforeEach
    void setUp() {
        europe = ContinentTestFactory.builder().build();
    }

    @Test
    void getContinent_returnPagedResponse() {

        PageQuery<ContinentSortField> pageQuery = new PageQuery<>(
                0,
                20,
                ContinentSortField.CODE,
                SortDirection.ASC
        );

        PageResult<Continent> page =
                PageResultTestFactory.page(List.of(europe));

        when(continentRepositoryPort.findAll(pageQuery)).thenReturn(page);

        PageResult<Continent> result =
                getContinentsUseCaseImpl.getContinents(pageQuery);

        assertThat(result).isNotNull();
        assertThat(result.totalElements()).isOne();
        assertThat(result.content().getFirst().getCode()).isEqualTo("EU");

        verify(continentRepositoryPort).findAll(pageQuery);
        verifyNoMoreInteractions(continentRepositoryPort);

    }
    
}
