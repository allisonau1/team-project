package use_case.sort_bookshelf;

/**
 * The input boundary for the Sort Bookshelf use case.
 */
public interface SortBookshelfInputBoundary {

    /**
     * Executes the Sort Bookshelf use case.
     * @param inputData the input data
     */
    void execute(SortBookshelfInputData inputData);
}