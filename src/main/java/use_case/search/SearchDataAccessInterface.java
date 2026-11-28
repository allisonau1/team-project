package use_case.search;

import entity.Book;

import java.util.List;

/**
 * DAO interface for the Search Use Case.
 */
public interface SearchDataAccessInterface {
    /**
     * Search with query and return the result as a List of Book.
     * @param query the query to search for
     * @return {@code List<Book>} of the list of books
     */
    List<Book> search(String query);
}
