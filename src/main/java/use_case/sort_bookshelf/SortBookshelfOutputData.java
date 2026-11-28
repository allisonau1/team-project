package use_case.sort_bookshelf;

import java.util.List;
import entity.Book;

/**
 * The output data for the Sort Bookshelf use case.
 */
public class SortBookshelfOutputData {
    private final List<Book> books;
    private final SortOption sortOption;

    /**
     * Creates the output data.
     * @param books the books in the order they should be displayed
     * @param sortOption the sort that was applied
     */
    public SortBookshelfOutputData(List<Book> books, SortOption sortOption) {
        this.books = books;
        this.sortOption = sortOption;
    }

    /**
     * Returns the books in display order.
     * @return the sorted books
     */
    public List<Book> getBooks() {
        return books;
    }

    /**
     * Returns the sort that was applied.
     * @return the applied sort option
     */
    public SortOption getSortOption() {
        return sortOption;
    }
}