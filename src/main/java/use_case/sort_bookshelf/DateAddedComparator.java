package use_case.sort_bookshelf;

import entity.Book;
import java.util.Comparator;

public class DateAddedComparator implements Comparator<Book> {

    private final TitleComparator titleComparator = new TitleComparator();
    private final boolean mostRecentFirst;

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