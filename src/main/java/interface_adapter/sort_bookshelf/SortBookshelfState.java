package interface_adapter.sort_bookshelf;

import java.util.ArrayList;
import java.util.List;

import entity.Book;
import use_case.sort_bookshelf.SortOption;

/**
 * The state for the Sort Bookshelf use case.
 */
public class SortBookshelfState {
    private List<Book> books = new ArrayList<>();
    private SortOption sortOption = SortOption.CUSTOM;
    private String error;

    /**
     * Returns the books to display.
     * @return the books in display order
     */
    public List<Book> getBooks() {
        return books;
    }

    /**
     * Sets the books to display.
     * @param books the books in display order
     */
    public void setBooks(List<Book> books) {
        this.books = books;
    }

    /**
     * Returns the sort that is currently applied.
     * @return the current sort option
     */
    public SortOption getSortOption() {
        return sortOption;
    }

    /**
     * Sets the sort that is currently applied.
     * @param sortOption the current sort option
     */
    public void setSortOption(SortOption sortOption) {
        this.sortOption = sortOption;
    }

    /**
     * Returns the error message, if any.
     * @return the error message, or null if there is none
     */
    public String getError() {
        return error;
    }

    /**
     * Sets the error message.
     * @param error the error message, or null to clear it
     */
    public void setError(String error) {
        this.error = error;
    }
}