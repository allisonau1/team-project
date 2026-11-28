package use_case.sort_bookshelf;

import entity.Book;
import java.util.Comparator;

/**
 * Orders books by the date they were added to the bookshelf.
 * Books added at the same time are ordered by title.
 */
public class DateAddedComparator implements Comparator<Book> {

    private final TitleComparator titleComparator = new TitleComparator();
    private final boolean mostRecentFirst;

    /**
     * Creates a date added comparator.
     * @param mostRecentFirst true to put the most recently added book first,
     *                        false to put the oldest first
     */
    public DateAddedComparator(boolean mostRecentFirst) {
        this.mostRecentFirst = mostRecentFirst;
    }

    @Override
    public int compare(Book book1, Book book2) {
        int dateOrder;
        if (mostRecentFirst) {
            dateOrder = book2.getDateAdded().compareTo(book1.getDateAdded());
        } else {
            dateOrder = book1.getDateAdded().compareTo(book2.getDateAdded());
        }

        // Ties broken by title
        if (dateOrder != 0) {
            return dateOrder;
        }
        return titleComparator.compare(book1, book2);
    }
}