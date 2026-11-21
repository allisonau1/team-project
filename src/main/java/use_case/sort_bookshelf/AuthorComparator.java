package use_case.sort_bookshelf;

import entity.Book;
import java.util.Comparator;

public class AuthorComparator implements Comparator<Book> {

    private final TitleComparator titleComparator = new TitleComparator();

    @Override
    public int compare(Book book1, Book book2) {
        int order = book1.getAuthor().compareToIgnoreCase(book2.getAuthor());
        // Use title as tiebreaker
        if (order == 0) {
            return titleComparator.compare(book1, book2);
        }
        return order;
    }
}
