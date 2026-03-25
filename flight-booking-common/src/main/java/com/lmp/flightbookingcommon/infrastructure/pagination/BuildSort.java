package com.lmp.flightbookingcommon.infrastructure.pagination;

import org.springframework.data.domain.Sort;

public final class BuildSort {

    private BuildSort() {
    }

    public static <T extends SortField> Sort toBuildSort(
            PageQuery<T> pageQuery, T
                    defaultSortField) {

        Sort.Direction direction = pageQuery.sortDirection() == SortDirection.DESC
                ? Sort.Direction.DESC
                : Sort.Direction.ASC;

        T sortField = pageQuery.sortBy() == null
                ? defaultSortField
                : pageQuery.sortBy();

        return Sort.by(direction, sortField.getProperty());

    }

}
