package com.lmp.flightbookingcommon.infrastructure.pagination;

import java.util.List;

public record PageResult<T>(

        List<T> content,
        int page,
        int size,
        long totalElements,
        int totalPages,
        boolean first,
        boolean last,
        boolean hasNext,
        boolean hasPrevious

) {
}
