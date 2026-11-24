package use_case.sort_bookshelf;

import java.util.List;
import entity.Book;

public class SortBookshelfOutputData {
    private final List<Book> books;
    private final SortOption sortOption;

    public SortBookshelfOutputData(List<Book> books, SortOption sortOption) {
        this.books = books;
        this.sortOption = sortOption;
    }

    public List<Book> getBooks() {
        return books;
    }

    public SortOption getSortOption() {
        return sortOption;
    }
}