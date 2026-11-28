package use_case.sort_bookshelf;

import entity.Bookshelf;

/**
 * The data access interface for the Sort Bookshelf use case.
 */
public interface SortBookshelfDataAccessInterface {
    /**
     * Returns the user's bookshelf.
     * @return the bookshelf, with its books in the user's custom order
     */
    Bookshelf getBookshelf();
}