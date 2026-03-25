package com.lmp.flightbookingcommon.infrastructure.pagination;

public final class PageQueryConfig<T> {

    private final int defaultPage;

    private final int defaultSize;

    private final int maxSize;

    private final T defaultSortBy;

    private final SortDirection defaultSortDirection;

    public PageQueryConfig(int defaultPage, int defaultSize, int maxSize,
                           T defaultSortBy, SortDirection defaultSortDirection) {
        this.defaultPage = defaultPage;
        this.defaultSize = defaultSize;
        this.maxSize = maxSize;
        this.defaultSortBy = defaultSortBy;
        this.defaultSortDirection = defaultSortDirection;
    }

    public int getDefaultPage() {
        return defaultPage;
    }

    public int getDefaultSize() {
        return defaultSize;
    }

    public int getMaxSize() {
        return maxSize;
    }

    public T getDefaultSortBy() {
        return defaultSortBy;
    }

    public SortDirection getDefaultSortDirection() {
        return defaultSortDirection;
    }

}
