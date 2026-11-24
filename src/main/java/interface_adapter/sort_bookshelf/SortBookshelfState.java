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

    public List<Book> getBooks() {
        return books;
    }

    public void setBooks(List<Book> books) {
        this.books = books;
    }

    public SortOption getSortOption() {
        return sortOption;
    }

    public void setSortOption(SortOption sortOption) {
        this.sortOption = sortOption;
    }

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }
}