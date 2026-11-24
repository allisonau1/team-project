package interface_adapter.search;

import entity.Book;
import interface_adapter.ViewManagerModel;
import use_case.search.SearchOutputBoundary;

import java.util.List;

/**
 * The Presenter for the Search Use Case.
 */
public class SearchPresenter implements SearchOutputBoundary {
    private final SearchViewModel searchViewModel;
    private final ViewManagerModel viewManagerModel;

    public SearchPresenter(ViewManagerModel viewManagerModel,
                           SearchViewModel searchViewModel) {
        this.viewManagerModel = viewManagerModel;
        this.searchViewModel = searchViewModel;
    }

    @Override
    public void prepareSuccessView(List<Book> response) {
        final SearchState searchState = searchViewModel.getState();
        searchState.setResult(response);
        searchViewModel.notifyStateChanged();

        viewManagerModel.setState(SearchViewModel.VIEW_NAME);
        viewManagerModel.notifyViewChanged();
    }

    @Override
    public void prepareFailView(String error) {
        final SearchState searchState = searchViewModel.getState();
        searchState.setResultError(error);
        searchViewModel.notifyStateChanged();
    }
}
