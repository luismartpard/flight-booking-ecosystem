package com.lmp.flightbookingcommon.infrastructure.pagination;

public record PageQuery<T>(

        int page,
        int size,
        T sortBy,
        SortDirection sortDirection

) {
}
