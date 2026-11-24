package use_case.sort_bookshelf;

import java.util.List;
import entity.Book;

public interface SortBookshelfDataAccessInterface {
    List<Book> getBooks();
}