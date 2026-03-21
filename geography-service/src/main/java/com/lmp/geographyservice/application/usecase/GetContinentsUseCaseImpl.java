package com.lmp.geographyservice.application.usecase;

import com.lmp.flightbookingcommon.infrastructure.pagination.PageQuery;
import com.lmp.flightbookingcommon.infrastructure.pagination.PageResult;
import com.lmp.flightbookingcommon.infrastructure.pagination.SortDirection;
import com.lmp.geographyservice.application.exception.InvalidContinentPageQueryException;
import com.lmp.geographyservice.application.port.in.GetContinentsUseCase;
import com.lmp.geographyservice.application.port.out.ContinentRepositoryPort;
import com.lmp.geographyservice.application.query.ContinentSortField;
import com.lmp.geographyservice.domain.model.Continent;

public class GetContinentsUseCaseImpl implements GetContinentsUseCase {

    private static final int DEFAULT_PAGE = 0;
    private static final int DEFAULT_SIZE = 20;
    private static final int MAX_SIZE = 100;
    private static final ContinentSortField DEFAULT_SORT_BY = ContinentSortField.CODE;
    private static final SortDirection DEFAULT_SORT_DIRECTION = SortDirection.ASC;

    private final ContinentRepositoryPort continentRepositoryPort;

    public GetContinentsUseCaseImpl(ContinentRepositoryPort continentRepositoryPort) {
        this.continentRepositoryPort = continentRepositoryPort;
    }

    @Override
    public PageResult<Continent> getContinents(PageQuery<ContinentSortField> pageQuery) {
        PageQuery<ContinentSortField> normalizedPageQuery = normalizePageQuery(pageQuery);
        return continentRepositoryPort.findAll(normalizedPageQuery);
    }

    private PageQuery<ContinentSortField> normalizePageQuery(PageQuery<ContinentSortField> pageQuery) {

        if (pageQuery == null) {
            return new PageQuery<>(DEFAULT_PAGE, DEFAULT_SIZE, DEFAULT_SORT_BY, DEFAULT_SORT_DIRECTION);
        }

        int page = pageQuery.page();
        int size = pageQuery.size();
        ContinentSortField sortBy = pageQuery.sortBy();
        SortDirection sortDirection = pageQuery.sortDirection();

        if (page < 0) {
            throw new InvalidContinentPageQueryException("global.page.invalid.page", page);
        }

        if (size <= 0) {
            throw new InvalidContinentPageQueryException("global.page.invalid.size", size);
        }

        if (size > MAX_SIZE) {
            throw new InvalidContinentPageQueryException("global.page.invalid.maxSize", size, MAX_SIZE);
        }

        if (sortBy == null) {
            sortBy = DEFAULT_SORT_BY;
        }

        if (sortDirection == null) {
            sortDirection = DEFAULT_SORT_DIRECTION;
        }

        return new PageQuery<>(page, size, sortBy, sortDirection);

    }

}
