package use_case.search;

import data_access.InMemoryBookDataAccessObject;
import entity.Book;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * The Search Interactor.
 */
public class SearchInteractor implements SearchInputBoundary {
    private final SearchDataAccessInterface searchDataAccessObject;
    private final SearchOutputBoundary presenter;
    private final InMemoryBookDataAccessObject searchResultsDataAccessObject;

    public SearchInteractor(SearchDataAccessInterface searchDataAccessInterface,
                            SearchOutputBoundary presenter,
                            InMemoryBookDataAccessObject searchResultsDataAccessObject) {
        this.searchDataAccessObject = searchDataAccessInterface;
        this.presenter = presenter;
        this.searchResultsDataAccessObject = searchResultsDataAccessObject;
    }

    @Override
    public void execute(SearchInputData inputData) {
        List<Book> results = searchDataAccessObject.search(inputData.getQuery());
        searchResultsDataAccessObject.saveSearchResults(results);
        if (results == null) {
            presenter.prepareFailView("Couldn't load search results. Please try again.");
            return;
        }
        List<Map<String, Object>> resultInfo = new ArrayList<>();
        for (Book book : results) {
            Map<String, Object> info = new HashMap<>();
            info.put("openLibraryId", book.getOpenLibraryId());
            info.put("title", book.getTitle());
            info.put("author", book.getAuthor());
            info.put("isbn", book.getIsbn());
            info.put("publicationYear", book.getPublicationYear());
            info.put("coverID", book.getCoverID());

            resultInfo.add(info);
        }
        SearchOutputData outputData = new SearchOutputData(resultInfo);
        presenter.prepareSuccessView(outputData);
    }
}
