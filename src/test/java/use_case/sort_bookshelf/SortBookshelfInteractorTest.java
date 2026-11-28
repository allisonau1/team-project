package use_case.sort_bookshelf;

import entity.Book;
import entity.Bookshelf;
import entity.Review;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class SortBookshelfInteractorTest {

    /**
     * Fake bookshelf data access object for testing.
     * Holds the bookshelf in memory.
     */
    private static class FakeSortBookshelfDataAccessObject
            implements SortBookshelfDataAccessInterface {

        private final Bookshelf bookshelf;

        public FakeSortBookshelfDataAccessObject(Bookshelf bookshelf) {
            this.bookshelf = bookshelf;
        }

        @Override
        public Bookshelf getBookshelf() {
            return bookshelf;
        }
    }

    /**
     * This class acts as our presenter for the test.
     * Records whether the interactor prepares a success or failure view.
     */
    private static class OutputRecorder implements SortBookshelfOutputBoundary {

        private SortBookshelfOutputData success;
        private String failure;

        @Override
        public void prepareSuccessView(SortBookshelfOutputData outputData) {
            this.success = outputData;
        }

        @Override
        public void prepareFailView(String errorMessage) {
            this.failure = errorMessage;
        }
    }

    /**
     * Creates a book with the given ID, title and author.
     */
    private static Book createBook(String id, String title, String author) {
        return new Book(id, title, author, 2000, null, null);
    }

    /**
     * Creates a book that has been reviewed with the given rating.
     */
    private static Book createRatedBook(String id, String title, int rating) {
        Book book = createBook(id, title, "Some Author");
        book.setReview(new Review(id, rating, "review text"));
        return book;
    }

    /**
     * Creates a book that was added to the bookshelf at the given time.
     */
    private static Book createBookAddedOn(String id, String title, LocalDateTime dateAdded) {
        Book book = createBook(id, title, "Some Author");
        book.setDateAdded(dateAdded);
        return book;
    }

    /**
     * Returns the Open Library IDs of the given books, in order.
     */
    private static List<String> idsOf(List<Book> books) {
        List<String> ids = new ArrayList<>();
        for (Book book : books) {
            ids.add(book.getOpenLibraryId());
        }
        return ids;
    }

    /**
     * Runs the interactor on the given bookshelf with the given sort option
     * and returns the recorder holding what the presenter received.
     */
    private static OutputRecorder runSort(Bookshelf bookshelf, SortOption option) {
        OutputRecorder recorder = new OutputRecorder();
        SortBookshelfInteractor interactor = new SortBookshelfInteractor(
                new FakeSortBookshelfDataAccessObject(bookshelf),
                recorder
        );
        interactor.execute(new SortBookshelfInputData(option));
        return recorder;
    }

    /**
     * Tests that books are sorted alphabetically by title, ignoring case.
     */
    @Test
    void sortByTitleIgnoresCaseTest() {

        // Arrange
        Bookshelf bookshelf = new Bookshelf();
        bookshelf.addBook(createBook("OL1W", "banana", "A"));
        bookshelf.addBook(createBook("OL2W", "Apple", "B"));
        bookshelf.addBook(createBook("OL3W", "cherry", "C"));

        // Act
        OutputRecorder recorder = runSort(bookshelf, SortOption.TITLE);

        // Assert
        assertNotNull(recorder.success);
        assertNull(recorder.failure);

        assertEquals(List.of("OL2W", "OL1W", "OL3W"),
                idsOf(recorder.success.getBooks()));
    }

    /**
     * Tests that books are sorted by author, with books by the same author ordered by title.
     */
    @Test
    void sortByAuthorBreaksTiesByTitleTest() {

        // Arrange: the same author written with different capitalization
        Bookshelf bookshelf = new Bookshelf();
        bookshelf.addBook(createBook("OL1W", "Zebra Book", "Orwell"));
        bookshelf.addBook(createBook("OL2W", "Alpha Book", "orwell"));
        bookshelf.addBook(createBook("OL3W", "Middle Book", "Austen"));

        // Act
        OutputRecorder recorder = runSort(bookshelf, SortOption.AUTHOR);

        // Assert
        assertNotNull(recorder.success);
        assertNull(recorder.failure);

        assertEquals(List.of("OL3W", "OL2W", "OL1W"),
                idsOf(recorder.success.getBooks()));
    }

    /**
     * Tests that books are sorted from highest to lowest rating,
     * with ties ordered by title and unreviewed books last.
     */
    @Test
    void sortByRatingHighToLowTest() {

        // Arrange: Alpha and Charlie tie, Delta and Echo are unreviewed
        Bookshelf bookshelf = new Bookshelf();
        bookshelf.addBook(createBook("OL4W", "Delta", "A"));
        bookshelf.addBook(createRatedBook("OL2W", "Bravo", 3));
        bookshelf.addBook(createRatedBook("OL3W", "Charlie", 5));
        bookshelf.addBook(createBook("OL5W", "Echo", "A"));
        bookshelf.addBook(createRatedBook("OL1W", "Alpha", 5));

        // Act
        OutputRecorder recorder = runSort(bookshelf, SortOption.RATING_HIGH_TO_LOW);

        // Assert
        assertNotNull(recorder.success);
        assertNull(recorder.failure);

        assertEquals(List.of("OL1W", "OL3W", "OL2W", "OL4W", "OL5W"),
                idsOf(recorder.success.getBooks()));
    }

    /**
     * Tests that books are sorted from lowest to highest rating
     * and that unreviewed books still come last.
     */
    @Test
    void sortByRatingLowToHighKeepsUnreviewedLastTest() {

        // Arrange
        Bookshelf bookshelf = new Bookshelf();
        bookshelf.addBook(createBook("OL4W", "Delta", "A"));
        bookshelf.addBook(createRatedBook("OL2W", "Bravo", 3));
        bookshelf.addBook(createRatedBook("OL3W", "Charlie", 5));
        bookshelf.addBook(createBook("OL5W", "Echo", "A"));
        bookshelf.addBook(createRatedBook("OL1W", "Alpha", 5));

        // Act
        OutputRecorder recorder = runSort(bookshelf, SortOption.RATING_LOW_TO_HIGH);

        // Assert
        assertNotNull(recorder.success);
        assertNull(recorder.failure);

        assertEquals(List.of("OL2W", "OL1W", "OL3W", "OL4W", "OL5W"),
                idsOf(recorder.success.getBooks()));
    }

    /**
     * Tests that books are sorted with the most recently added book first.
     */
    @Test
    void sortByDateNewestFirstTest() {

        // Arrange
        LocalDateTime base = LocalDateTime.of(2026, 11, 1, 12, 0);
        Bookshelf bookshelf = new Bookshelf();
        bookshelf.addBook(createBookAddedOn("OL2W", "Middle", base.plusDays(1)));
        bookshelf.addBook(createBookAddedOn("OL3W", "Newest", base.plusDays(2)));
        bookshelf.addBook(createBookAddedOn("OL1W", "Oldest", base));

        // Act
        OutputRecorder recorder = runSort(bookshelf, SortOption.DATE_NEWEST_FIRST);

        // Assert
        assertNotNull(recorder.success);
        assertNull(recorder.failure);

        assertEquals(List.of("OL3W", "OL2W", "OL1W"),
                idsOf(recorder.success.getBooks()));
    }

    /**
     * Tests that books are sorted with the oldest added book first.
     */
    @Test
    void sortByDateOldestFirstTest() {

        // Arrange
        LocalDateTime base = LocalDateTime.of(2026, 11, 1, 12, 0);
        Bookshelf bookshelf = new Bookshelf();
        bookshelf.addBook(createBookAddedOn("OL2W", "Middle", base.plusDays(1)));
        bookshelf.addBook(createBookAddedOn("OL3W", "Newest", base.plusDays(2)));
        bookshelf.addBook(createBookAddedOn("OL1W", "Oldest", base));

        // Act
        OutputRecorder recorder = runSort(bookshelf, SortOption.DATE_OLDEST_FIRST);

        // Assert
        assertNotNull(recorder.success);
        assertNull(recorder.failure);

        assertEquals(List.of("OL1W", "OL2W", "OL3W"),
                idsOf(recorder.success.getBooks()));
    }

    /**
     * Tests that the custom option leaves the books in the order they are stored.
     */
    @Test
    void customOptionKeepsStoredOrderTest() {

        // Arrange: deliberately not in any sorted order
        Bookshelf bookshelf = new Bookshelf();
        bookshelf.addBook(createBook("OL3W", "Charlie", "Z"));
        bookshelf.addBook(createBook("OL1W", "Alpha", "Y"));
        bookshelf.addBook(createBook("OL2W", "Bravo", "X"));

        // Act
        OutputRecorder recorder = runSort(bookshelf, SortOption.CUSTOM);

        // Assert
        assertNotNull(recorder.success);
        assertNull(recorder.failure);

        assertEquals(List.of("OL3W", "OL1W", "OL2W"),
                idsOf(recorder.success.getBooks()));
        assertEquals(SortOption.CUSTOM, recorder.success.getSortOption());
    }

    /**
     * Tests that sorting does not change the order of the books stored on the bookshelf.
     */
    @Test
    void sortingDoesNotChangeStoredOrderTest() {

        // Arrange
        Bookshelf bookshelf = new Bookshelf();
        bookshelf.addBook(createBook("OL3W", "Charlie", "Z"));
        bookshelf.addBook(createBook("OL1W", "Alpha", "Y"));
        bookshelf.addBook(createBook("OL2W", "Bravo", "X"));

        // Act
        OutputRecorder recorder = runSort(bookshelf, SortOption.TITLE);

        // Assert: the presented list is sorted, but the bookshelf keeps the user's order
        assertEquals(List.of("OL1W", "OL2W", "OL3W"),
                idsOf(recorder.success.getBooks()));
        assertEquals(List.of("OL3W", "OL1W", "OL2W"),
                idsOf(bookshelf.getBooks()));
    }

    /**
     * Tests that the output reports which sort option was applied.
     */
    @Test
    void outputContainsSelectedSortOptionTest() {

        // Arrange
        Bookshelf bookshelf = new Bookshelf();
        bookshelf.addBook(createBook("OL1W", "Alpha", "Y"));

        // Act
        OutputRecorder recorder = runSort(bookshelf, SortOption.AUTHOR);

        // Assert
        assertNotNull(recorder.success);
        assertEquals(SortOption.AUTHOR, recorder.success.getSortOption());
    }

    /**
     * Tests that sorting an empty bookshelf succeeds with an empty list.
     */
    @Test
    void emptyBookshelfTest() {

        // Arrange
        Bookshelf bookshelf = new Bookshelf();

        // Act
        OutputRecorder recorder = runSort(bookshelf, SortOption.TITLE);

        // Assert
        assertNotNull(recorder.success);
        assertNull(recorder.failure);

        assertTrue(recorder.success.getBooks().isEmpty());
    }
}