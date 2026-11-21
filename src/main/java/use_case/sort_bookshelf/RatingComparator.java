package use_case.sort_bookshelf;

import entity.Book;
import entity.Review;
import java.util.Comparator;

public class RatingComparator implements Comparator<Book> {

    private final TitleComparator titleComparator = new TitleComparator();
    private final boolean highestFirst;

    public RatingComparator(boolean highestFirst) {
        this.highestFirst = highestFirst;
    }

    @Override
    public int compare(Book book1, Book book2) {
        Review r1 = book1.getReview();
        Review r2 = book2.getReview();

        // Unreviewed books always go last, in either direction
        if (r1 == null && r2 == null) {
            return titleComparator.compare(book1, book2);
        } else if (r1 == null) {
            return 1;
        } else if (r2 == null) {
            return -1;
        }

        int ratingOrder;
        if (highestFirst) {
            ratingOrder = Integer.compare(r2.getRating(), r1.getRating());
        } else {
            ratingOrder = Integer.compare(r1.getRating(), r2.getRating());
        }

        // Ties broken by title
        if (ratingOrder != 0) {
            return ratingOrder;
        }
        return titleComparator.compare(book1, book2);
    }
}