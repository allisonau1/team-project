package use_case.sort_bookshelf;

import entity.Book;
import java.util.Comparator;

/**
 * Orders books alphabetically by title, ignoring case.
 */
public class TitleComparator implements Comparator<Book> {
    @Override
    public int compare(Book book1, Book book2) {
        return book1.getTitle().compareToIgnoreCase(book2.getTitle());
    }
}
