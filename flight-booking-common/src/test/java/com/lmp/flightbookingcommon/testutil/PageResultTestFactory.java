package com.lmp.flightbookingcommon.testutil;

import com.lmp.flightbookingcommon.infrastructure.pagination.PageResult;

import java.util.List;

public final class PageResultTestFactory {

    private PageResultTestFactory() {
    }

    public static <T> PageResult<T> page(List<T> content) {

        return new PageResult<>(
                content,
                0,
                content.size(),
                content.size(),
                1,
                true,
                true,
                false,
                false
        );

    }

    public static <T> PageResult<T> page(List<T> content, int page, int size, long total) {

        return new PageResult<>(
                content,
                page,
                size,
                total,
                1,
                true,
                true,
                false,
                false
        );

    }

}
