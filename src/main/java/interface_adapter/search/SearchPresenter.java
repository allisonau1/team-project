package interface_adapter.search;

import interface_adapter.ViewManagerModel;
import use_case.search.SearchOutputBoundary;
import use_case.search.SearchOutputData;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

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
    public void prepareSuccessView(SearchOutputData searchOutputData) {
        final SearchState searchState = searchViewModel.getState();

        searchState.setResult(searchOutputData.getResult());
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
