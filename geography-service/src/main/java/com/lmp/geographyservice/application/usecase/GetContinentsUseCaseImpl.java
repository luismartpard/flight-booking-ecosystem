package com.lmp.geographyservice.application.usecase;

import com.lmp.flightbookingcommon.infrastructure.pagination.PageQuery;
import com.lmp.flightbookingcommon.infrastructure.pagination.PageQueryConfig;
import com.lmp.flightbookingcommon.infrastructure.pagination.PageQueryNormalizer;
import com.lmp.flightbookingcommon.infrastructure.pagination.PageResult;
import com.lmp.flightbookingcommon.infrastructure.pagination.SortDirection;
import com.lmp.geographyservice.application.exception.InvalidContinentPageQueryException;
import com.lmp.geographyservice.application.port.in.GetContinentsUseCase;
import com.lmp.geographyservice.application.port.out.ContinentRepositoryPort;
import com.lmp.geographyservice.application.query.ContinentSortField;
import com.lmp.geographyservice.domain.model.Continent;

public class GetContinentsUseCaseImpl implements GetContinentsUseCase {

    private static final PageQueryConfig<ContinentSortField> CONFIG =
            new PageQueryConfig<>(
                    0,
                    20,
                    100,
                    ContinentSortField.CODE,
                    SortDirection.ASC
            );

    private final ContinentRepositoryPort continentRepositoryPort;

    public GetContinentsUseCaseImpl(ContinentRepositoryPort continentRepositoryPort) {
        this.continentRepositoryPort = continentRepositoryPort;
    }

    @Override
    public PageResult<Continent> getContinents(PageQuery<ContinentSortField> pageQuery) {
        PageQuery<ContinentSortField> normalizedPageQuery = PageQueryNormalizer.normalize(
                pageQuery,
                CONFIG.getDefaultPage(),
                CONFIG.getDefaultSize(),
                CONFIG.getDefaultSortBy(),
                CONFIG.getDefaultSortDirection(),
                CONFIG.getMaxSize(),
                InvalidContinentPageQueryException::new);

        return continentRepositoryPort.findAll(normalizedPageQuery);
    }

}
