package use_case.sort_bookshelf;

import entity.Book;
import java.util.Comparator;

public class DateAddedComparator implements Comparator<Book> {
    public int compare(Book book1, Book book2) {
        return book1.getDateAdded().compareToIgnoreCase(book2.getDateAdded());
    }

}
