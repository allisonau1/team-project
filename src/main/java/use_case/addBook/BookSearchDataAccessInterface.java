package use_case.addBook;

import entity.Book;

/**
 * Data access interface for retrieving books
 * that have previously been searched.
 */
public interface BookSearchDataAccessInterface {

    /**
     * Retrieves a previously searched book.
     *
     * @param openLibraryID the book to retrieve, identified by its Open Library ID
     * @return the stored book, or null if it does not exist
     */
    Book getBook(String openLibraryID);
}