package use_case.sort_bookshelf;

public class SortBookshelfInputData {
    private final SortOption sortOption;

    public SortBookshelfInputData(SortOption sortOption) {
        this.sortOption = sortOption;
    }

    public SortOption getSortOption() {
        return sortOption;
    }
}