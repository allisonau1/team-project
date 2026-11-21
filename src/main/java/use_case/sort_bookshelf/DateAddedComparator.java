package use_case.sort_bookshelf;

import entity.Book;
import java.util.Comparator;

public class DateAddedComparator implements Comparator<Book> {

    private final TitleComparator titleComparator = new TitleComparator();

    @Override
    public int compare(Book book1, Book book2) {
        if (book1.getDateAdded().isAfter(book2.getDateAdded())) {
            return -1;
        } else if (book1.getDateAdded().isBefore(book2.getDateAdded())) {
            return 1;
        } else {
            // Ties are handled by title
            return titleComparator.compare(book1, book2);
        }
    }
}
