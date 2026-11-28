package use_case.sort_bookshelf;

/**
 * The output boundary for the Sort Bookshelf use case.
 */
public interface SortBookshelfOutputBoundary {

    /**
     * Prepares the success view for the Sort Bookshelf use case.
     * @param outputData the sorted books and the sort that was applied
     */
    void prepareSuccessView(SortBookshelfOutputData outputData);

    /**
     * Prepares the failure view for the Sort Bookshelf use case.
     * @param errorMessage the explanation of the failure
     */
    void prepareFailView(String errorMessage);
}