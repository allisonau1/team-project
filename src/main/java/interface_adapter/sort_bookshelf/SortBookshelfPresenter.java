package interface_adapter.sort_bookshelf;

import use_case.sort_bookshelf.SortBookshelfOutputBoundary;
import use_case.sort_bookshelf.SortBookshelfOutputData;

/**
 * The Presenter for the Sort Bookshelf use case.
 */
public class SortBookshelfPresenter implements SortBookshelfOutputBoundary {
    private final SortBookshelfViewModel sortBookshelfViewModel;

    public SortBookshelfPresenter(SortBookshelfViewModel sortBookshelfViewModel) {
        this.sortBookshelfViewModel = sortBookshelfViewModel;
    }

    @Override
    public void prepareSuccessView(SortBookshelfOutputData outputData) {
        final SortBookshelfState state = sortBookshelfViewModel.getState();
        state.setBooks(outputData.getBooks());
        state.setSortOption(outputData.getSortOption());
        state.setError(null);
        sortBookshelfViewModel.notifyStateChanged();
    }

    @Override
    public void prepareFailView(String errorMessage) {
        final SortBookshelfState state = sortBookshelfViewModel.getState();
        state.setError(errorMessage);
        sortBookshelfViewModel.notifyStateChanged();
    }
}