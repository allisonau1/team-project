package use_case.sort_bookshelf;

import entity.Book;
import entity.Review;
import java.util.Comparator;

public class RatingComparator implements Comparator<Book> {

    private final TitleComparator titleComparator = new TitleComparator();

    @Override
    public int compare(Book book1, Book book2) {
        Review r1 = book1.getReview();
        Review r2 = book2.getReview();

        // Put all unreviewed books after reviewed books
        if (r1 == null && r2 == null) {
            return titleComparator.compare(book1, book2);
        }
        else if (r1 == null) {
            return 1;
        }
        else if (r2 == null) {
            return -1;
        }

        // If both books have ratings, highest comes first
        // Ties broken by title
        if (r1.getRating() == r2.getRating()) {
            return titleComparator.compare(book1, book2);
        }
        return Integer.compare(r2.getRating(), r1.getRating());
    }
}
