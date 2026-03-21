package com.lmp.flightbookingcommon.infrastructure.pagination;

import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import java.util.function.Function;

public final class PageQueryMapper {

    private PageQueryMapper() {}

    public static <T> PageQuery<T> toPageQuery(
            Pageable pageable,
            Function<String, T> sortMapper,
            T defaultSort
    ) {

        T sortBy = pageable.getSort().stream()
                .findFirst()
                .map(Sort.Order::getProperty)
                .map(sortMapper)
                .orElse(defaultSort);

        SortDirection direction = pageable.getSort().stream()
                .findFirst()
                .filter(order -> order.getDirection().isDescending())
                .map(order -> SortDirection.DESC)
                .orElse(SortDirection.ASC);

        return new PageQuery<>(
                pageable.getPageNumber(),
                pageable.getPageSize(),
                sortBy,
                direction
        );

    }

}
