package use_case.addBook;

/**
 * Stores the output data produced after successfully
 * adding a book to the user's bookshelf.
 */
public class AddBookOutputData {

    private final String title;
    private final String author;

    /**
     * Creates an AddBookOutputData object.
     *
     * @param title the title of the book added to shelf.
     * @param author the author of the book added to shelf.
     */
    public AddBookOutputData(String title, String author) {
        this.title = title;
        this.author = author;
    }

    /**
     * Returns the title of the book that was added.
     *
     * @return the added book title
     */
    public String getTitle() {
        return this.title;
    }

    /**
     * Returns the author of the book that was added.
     *
     * @return the added book author
     */
    public String getAuthor() {
        return this.author;
    }
}