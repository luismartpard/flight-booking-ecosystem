package com.lmp.flightbookingcommon.infrastructure.pagination;

import java.util.function.Function;

public final class PageQueryNormalizer {

    private PageQueryNormalizer() {}

    public static <T> PageQuery<T> toPageNormalize(
            PageQuery<T> pageQuery,
            int defaultPage,
            int defaultSize,
            T defaultSortBy,
            SortDirection defaultSortDirection,
            int maxSize,
            Function<String, RuntimeException> exceptionFactory
    ) {

        if (pageQuery == null) {
            return new PageQuery<>(
                    defaultPage,
                    defaultSize,
                    defaultSortBy,
                    defaultSortDirection
            );
        }

        int page = pageQuery.page();
        int size = pageQuery.size();
        T sortBy = pageQuery.sortBy();
        SortDirection sortDirection = pageQuery.sortDirection();

        if(page < 0) {
            throw exceptionFactory.apply("global.page.invalid.page");
        }

        if(size <= 0) {
            throw exceptionFactory.apply("global.page.invalid.size");
        }

        if(size > maxSize) {
            throw exceptionFactory.apply("global.page.invalid.maxSize");
        }

        if(sortBy == null) {
            sortBy = defaultSortBy;
        }

        if (sortDirection == null) {
            sortDirection = defaultSortDirection;
        }

        return new PageQuery<>(page, size, sortBy, sortDirection);

    }

}
