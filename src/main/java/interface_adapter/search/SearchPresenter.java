package interface_adapter.search;

import entity.Book;
import interface_adapter.ViewManagerModel;
import use_case.search.SearchOutputBoundary;

import java.util.ArrayList;
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
        List<String> titles = new ArrayList<>();
        List<String> authors = new ArrayList<>();
        List<Integer> coverIds = new ArrayList<>();
        List<String> openLibraryIds = new ArrayList<>();

        for (Book book : response) {
            titles.add(book.getTitle());
            authors.add(book.getAuthor());
            coverIds.add(book.getCoverID());
            openLibraryIds.add(book.getOpenLibraryId());
        }

        searchState.setResult(titles, authors, coverIds, openLibraryIds);
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
