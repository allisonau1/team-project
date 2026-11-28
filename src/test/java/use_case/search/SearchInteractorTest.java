package use_case.search;

import data_access.InMemoryBookDataAccessObject;
import entity.Book;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class SearchInteractorTest {
    @Test
    void forwardsQueryToDaoAndResultToPresenter() {
        Book book = new Book(
                "/works/OL82563W",
                "Harry Potter and the Philosopher's Stone",
                "J. K. Rowling",
                1997,
                null,       // ISBN
                15155833    // Cover ID
        );
        List<Book> expectedResult = List.of(book);
        List<String> receivedQueries = new ArrayList<>();
        List<Map<String, Object>> presentedResults = new ArrayList<>();

        SearchDataAccessInterface fakeDao = query -> {
            receivedQueries.add(query);
            return expectedResult;
        };

        SearchOutputBoundary fakePresenter = new SearchOutputBoundary() {
            @Override
            public void prepareSuccessView(SearchOutputData searchOutputData) {
                presentedResults.addAll(searchOutputData.getResult());
            }
            @Override
            public void prepareFailView(String error) {
                throw new AssertionError("Expected search to succeed: " + error);
            }
        };

        InMemoryBookDataAccessObject inMemoryDao = new InMemoryBookDataAccessObject();

        SearchInteractor interactor = new SearchInteractor(fakeDao, fakePresenter, inMemoryDao);
        interactor.execute(new SearchInputData("Harry Potter"));

        assertEquals(List.of("Harry Potter"), receivedQueries);
        assertEquals(List.of(expectedResult), presentedResults);
    }
}
