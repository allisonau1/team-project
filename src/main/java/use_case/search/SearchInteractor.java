package use_case.search;

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
