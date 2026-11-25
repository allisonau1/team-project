package use_case.addBook;

import entity.Book;
import entity.Bookshelf;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

class AddBookInteractorTest {

    /**
     * Fake book search data access object for testing.
     * Stores books in memory using their Open Library IDs.
     */
    private static class FakeBookSearchDataAccessObject
            implements BookSearchDataAccessInterface {

        private final Map<String, Book> books = new HashMap<>();

        public void save(Book book) {
            books.put(book.getOpenLibraryId(), book);
        }

        @Override
        public Book getBook(String openLibraryId) {
            return books.get(openLibraryId);
        }
    }

    /**
     * Fake bookshelf data access object for testing.
     * Stores the bookshelf in memory.
     */
    private static class FakeBookshelfDataAccessObject
            implements BookshelfDataAccessInterface {

        private Bookshelf bookshelf;

        public FakeBookshelfDataAccessObject(Bookshelf bookshelf) {
            this.bookshelf = bookshelf;
        }

        @Override
        public Bookshelf getBookshelf() {
            return bookshelf;
        }

        @Override
        public boolean existsOnBookshelf(String openLibraryId) {
            return bookshelf.getBooks().stream()
                    .anyMatch(book ->
                            book.getOpenLibraryId().equals(openLibraryId));
        }

        @Override
        public void saveBookshelf(Bookshelf bookshelf) {
            this.bookshelf = bookshelf;
        }
    }

    /**
     * This class acts as our presenter for the test.
     * Records whether the interactor prepares a success or failure view.
     */
    private static class OutputRecorder implements AddBookOutputBoundary {

        private AddBookOutputData success;
        private String failure;

        @Override
        public void prepareSuccessView(AddBookOutputData data) {
            this.success = data;
        }

        @Override
        public void prepareFailView(String error) {
            this.failure = error;
        }
    }

    /**
     * Tests that a book is successfully added to the bookshelf.
     */
    @Test
    void successTest() {

        // Arrange
        Book book = new Book(
                "OL12345W",
                "The Great Gatsby",
                "F. Scott Fitzgerald",
                1925,
                "9780743273565",
                12345
        );

        FakeBookSearchDataAccessObject bookSearchDAO =
                new FakeBookSearchDataAccessObject();
        bookSearchDAO.save(book);

        Bookshelf bookshelf = new Bookshelf();

        FakeBookshelfDataAccessObject bookshelfDAO =
                new FakeBookshelfDataAccessObject(bookshelf);

        OutputRecorder recorder = new OutputRecorder();

        AddBookInputData inputData = new AddBookInputData("OL12345W");

        AddBookInteractor interactor = new AddBookInteractor(
                bookshelfDAO,
                recorder,
                bookSearchDAO
        );

        // Act
        interactor.execute(inputData);

        // Assert
        assertNotNull(recorder.success);
        assertNull(recorder.failure);

        assertEquals(book, recorder.success.getBook());
        assertTrue(bookshelfDAO.existsOnBookshelf("OL12345W"));
        assertEquals(1, bookshelfDAO.getBookshelf().getBooks().size());
    }

    /**
     * Tests that an error is returned when the requested book cannot be found.
     */
    @Test
    void failureBookNotFoundTest() {

        // Arrange
        FakeBookSearchDataAccessObject bookSearchDAO =
                new FakeBookSearchDataAccessObject();

        Bookshelf bookshelf = new Bookshelf();

        FakeBookshelfDataAccessObject bookshelfDAO =
                new FakeBookshelfDataAccessObject(bookshelf);

        OutputRecorder recorder = new OutputRecorder();

        AddBookInputData inputData = new AddBookInputData("OL12345W");

        AddBookInteractor interactor = new AddBookInteractor(
                bookshelfDAO,
                recorder,
                bookSearchDAO
        );

        // Act
        interactor.execute(inputData);

        // Assert
        assertNull(recorder.success);
        assertNotNull(recorder.failure);

        assertEquals("Book could not be found.", recorder.failure);
        assertFalse(bookshelfDAO.existsOnBookshelf("OL12345W"));
    }

    /**
     * Tests that an error is returned when the book is already on the bookshelf.
     */
    @Test
    void failureBookAlreadyExistsTest() {

        // Arrange
        Book book = new Book(
                "OL12345W",
                "The Great Gatsby",
                "F. Scott Fitzgerald",
                1925,
                "9780743273565",
                12345
        );

        FakeBookSearchDataAccessObject bookSearchDAO =
                new FakeBookSearchDataAccessObject();
        bookSearchDAO.save(book);

        Bookshelf bookshelf = new Bookshelf();
        bookshelf.addBook(book);

        FakeBookshelfDataAccessObject bookshelfDAO =
                new FakeBookshelfDataAccessObject(bookshelf);

        OutputRecorder recorder = new OutputRecorder();

        AddBookInputData inputData = new AddBookInputData("OL12345W");

        AddBookInteractor interactor = new AddBookInteractor(
                bookshelfDAO,
                recorder,
                bookSearchDAO
        );

        // Act
        interactor.execute(inputData);

        // Assert
        assertNull(recorder.success);
        assertNotNull(recorder.failure);

        assertEquals(
                "This book is already on your bookshelf.",
                recorder.failure
        );

        assertTrue(bookshelfDAO.existsOnBookshelf("OL12345W"));
        assertEquals(1, bookshelfDAO.getBookshelf().getBooks().size());
    }
}