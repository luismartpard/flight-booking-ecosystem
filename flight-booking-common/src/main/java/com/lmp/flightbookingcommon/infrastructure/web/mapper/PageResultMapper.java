package com.lmp.flightbookingcommon.infrastructure.web.mapper;

import com.lmp.flightbookingcommon.infrastructure.pagination.PageResult;
import com.lmp.flightbookingcommon.infrastructure.web.response.PageResponse;

import java.util.function.Function;

public final class PageResultMapper {

    private PageResultMapper() {}

    public static <T,R>PageResponse<R> map(PageResult<T> source, Function<T,R> mapper) {

        return new PageResponse<>(
            source.content().stream().map(mapper).toList(),
            source.page(),
            source.size(),
            source.totalElements(),
            source.totalPages(),
            source.first(),
            source.last(),
            source.hasNext(),
            source.hasPrevious()
        );

    }

}
