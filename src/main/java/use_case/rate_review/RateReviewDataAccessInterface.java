package use_case.rate_review;
import entity.Book;
import entity.Bookshelf;


/**
 * DAO interface for the Rate and Review Use Case.
 */
public interface RateReviewDataAccessInterface {

    /**
     * Returns the book with the given openLibraryID.
     * @param openLibraryID the openLibraryID to look up
     * @return the book with the given openLibraryID
     */
    Book getBook(String openLibraryID);

    /**
     * Returns the user's bookshelf.
     * @return the bookshelf
     */
    Bookshelf getBookshelf();

    /**
     * Saves the bookshelf, including every book's rating and review.
     * @param bookshelf the bookshelf to save
     */
    void saveBookshelf(Bookshelf bookshelf);
}
