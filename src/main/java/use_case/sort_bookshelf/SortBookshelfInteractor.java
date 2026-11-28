package use_case.sort_bookshelf;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import entity.Book;

/**
 * The interactor for the Sort Bookshelf use case.
 * Sorts a copy of the bookshelf so the user's custom order is never modified.
 */
public class SortBookshelfInteractor implements SortBookshelfInputBoundary {
    private final SortBookshelfDataAccessInterface dataAccess;
    private final SortBookshelfOutputBoundary presenter;

    /**
     * Creates the interactor.
     * @param dataAccess the data access object used to read the bookshelf
     * @param presenter the presenter that receives the sorted books
     */
    public SortBookshelfInteractor(SortBookshelfDataAccessInterface dataAccess,
                                   SortBookshelfOutputBoundary presenter) {
        this.dataAccess = dataAccess;
        this.presenter = presenter;
    }

    @Override
    public void execute(SortBookshelfInputData inputData) {
        // Copy so the stored (custom) order is never modified
        List<Book> books = new ArrayList<>(dataAccess.getBookshelf().getBooks());
        Comparator<Book> comparator = getComparator(inputData.getSortOption());
        if (comparator != null) {
            books.sort(comparator);
        }

        presenter.prepareSuccessView(
                new SortBookshelfOutputData(books, inputData.getSortOption()));
    }

    /**
     * Returns the comparator for a sort option.
     * @param option the sort option
     * @return the comparator, or null for CUSTOM (no sorting)
     */
    private Comparator<Book> getComparator(SortOption option) {
        switch (option) {
            case TITLE:
                return new TitleComparator();
            case AUTHOR:
                return new AuthorComparator();
            case RATING_HIGH_TO_LOW:
                return new RatingComparator(true);
            case RATING_LOW_TO_HIGH:
                return new RatingComparator(false);
            case DATE_NEWEST_FIRST:
                return new DateAddedComparator(true);
            case DATE_OLDEST_FIRST:
                return new DateAddedComparator(false);
            default:
                return null; // CUSTOM: leave the list in the user's order
        }
    }
}