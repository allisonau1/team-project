package use_case.sort_bookshelf;

/**
 * The ways the bookshelf can be sorted.
 * CUSTOM leaves the books in the user's own order.
 */
public enum SortOption {
    CUSTOM,
    TITLE,
    AUTHOR,
    RATING_HIGH_TO_LOW,
    RATING_LOW_TO_HIGH,
    DATE_NEWEST_FIRST,
    DATE_OLDEST_FIRST
}