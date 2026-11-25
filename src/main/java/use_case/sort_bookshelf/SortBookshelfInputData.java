package use_case.sort_bookshelf;

/**
 * The input data for the Sort Bookshelf use case.
 */
public class SortBookshelfInputData {
    private final SortOption sortOption;

    /**
     * Creates the input data.
     * @param sortOption the sort the user selected
     */
    public SortBookshelfInputData(SortOption sortOption) {
        this.sortOption = sortOption;
    }

    /**
     * Returns the sort the user selected.
     * @return the selected sort option
     */
    public SortOption getSortOption() {
        return sortOption;
    }
}