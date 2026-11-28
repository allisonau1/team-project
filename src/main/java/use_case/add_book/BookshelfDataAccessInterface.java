package use_case.add_book;

import entity.Bookshelf;

/**
 * Data Access Interface to handle the retrieving and saving of a bookshelf
 */
public interface BookshelfDataAccessInterface {

    Bookshelf getBookshelf();

    boolean existsOnBookshelf(String openLibraryId);

    void saveBookshelf(Bookshelf bookshelf);
}