package use_case.addBook;

import entity.Book;

/**
 * Stores the input data required to add a book
 * to the user's personal bookshelf.
 */
public class AddBookInputData {
    private final String openLibraryID;
    /**
     * Creates an AddBookInputData object.
     * @param openLibraryID the selected book's open library ID
     */
    public AddBookInputData(String openLibraryID) {
        this.openLibraryID = openLibraryID;
    }

    /**
    * Returns the selected book.
    *
    * @return the selected book by its open library ID.
     */
    public String getOpenLibraryID() {
            return this.openLibraryID;
        }
}
