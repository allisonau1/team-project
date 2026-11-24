package use_case.sort_bookshelf;

public interface SortBookshelfOutputBoundary {
    void prepareSuccessView(SortBookshelfOutputData outputData);
    void prepareFailView(String errorMessage);
}