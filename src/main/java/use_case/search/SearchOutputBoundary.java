package use_case.search;

import entity.Book;

import java.util.List;
import java.util.Map;

/**
 * The output boundary for the Search Use Case.
 */
public interface SearchOutputBoundary {
    /**
     * Prepares the success view for the Search Use Case.
     * @param searchOutputData for the output data
     */
    void prepareSuccessView(SearchOutputData searchOutputData);
    /**
     * Prepares the failure view for the Search Use Case.
     * @param error the explanation of the failure
     */
    void prepareFailView(String error);
}
