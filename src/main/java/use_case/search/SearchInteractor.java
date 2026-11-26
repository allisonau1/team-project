package use_case.search;

import entity.Book;

import java.util.List;

/**
 * The Search Interactor.
 */
public class SearchInteractor implements SearchInputBoundary {
    private final SearchDataAccessInterface searchDataAccessObject;
    private final SearchOutputBoundary presenter;

    public SearchInteractor(SearchDataAccessInterface searchDataAccessInterface,
                            SearchOutputBoundary presenter) {
        this.searchDataAccessObject = searchDataAccessInterface;
        this.presenter = presenter;
    }

    @Override
    public void execute(SearchInputData inputData) {
        List<Book> results = searchDataAccessObject.search(inputData.getQuery());
        if (results == null) {
            presenter.prepareFailView("Couldn't load search results. Please try again.");
            return;
        }
        presenter.prepareSuccessView(results);
    }
}
