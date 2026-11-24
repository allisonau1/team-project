package use_case.addBook;

import entity.Book;
import entity.Bookshelf;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;

class AddBookInteractorTest {

    private FakeBookshelfDataAccessObject dao;
    private TestPresenter presenter;
    private AddBookInteractor interactor;

    @BeforeEach
    void setUp() {
        dao = new FakeBookshelfDataAccessObject();
        presenter = new TestPresenter();

        interactor = new AddBookInteractor(dao, presenter);
    }

    @Test
    void testAddBookSuccessfully() {

        AddBookInputData inputData = new AddBookInputData(
                new Book("OL12345W",
                        "The Great Gatsby",
                        "F. Scott Fitzgerald",
                        1925,
                        "9780743273565",
                        123456
                ));

        interactor.execute(inputData);

        // Check that the book was added
        assertEquals(1, dao.getBookshelf().getBooks().size());

        Book addedBook = dao.getBookshelf().getBooks().get(0);

        assertEquals("The Great Gatsby", addedBook.getTitle());
        assertEquals("F. Scott Fitzgerald", addedBook.getAuthor());
        assertEquals("9780743273565", addedBook.getIsbn());

        // Check that the bookshelf was saved
        assertTrue(dao.saveCalled);

        // Check that the presenter was called
        assertTrue(presenter.successCalled);
    }

    @Test
    void testAddBookPreservesExistingBooks() {

        // Add an existing book before executing the use case
        Book existingBook = new Book(
                "OL12345W",
                "The Great Gatsby",
                "F. Scott Fitzgerald",
                1925,
                "9780743273565",
                123456
        );

        dao.getBookshelf().addBook(existingBook);

        // Add another book
        AddBookInputData inputData = new AddBookInputData(new Book(
                "OL12345W",
                "The Great Gatsby",
                "F. Scott Fitzgerald",
                1925,
                "9780743273565",
                123456
        ));

        interactor.execute(inputData);

        // Both books should still exist
        assertEquals(2, dao.getBookshelf().getBooks().size());

        assertSame(existingBook, dao.getBookshelf().getBooks().get(0));

        assertEquals(
                "The Great Gatsby",
                dao.getBookshelf().getBooks().get(1).getTitle()
        );
    }

    @Test
    void testExistingBookshelfIsSaved() {

        Bookshelf originalBookshelf = dao.getBookshelf();

        AddBookInputData inputData = new AddBookInputData(
                new Book("OL12345W",
                "The Great Gatsby",
                "F. Scott Fitzgerald",
                1925,
                "9780743273565",
                123456
                ));

        interactor.execute(inputData);

        // The DAO should save the existing bookshelf,
        // not replace it with a new empty bookshelf.
        assertSame(originalBookshelf, dao.savedBookshelf);
    }

    // Fake DAO for testing
    private static class FakeBookshelfDataAccessObject
            implements BookshelfDataAccessInterface {

        private Bookshelf bookshelf = new Bookshelf();

        private boolean saveCalled = false;
        private Bookshelf savedBookshelf;

        @Override
        public Bookshelf getBookshelf() {
            return bookshelf;
        }

        @Override
        public void saveBookshelf(Bookshelf bookshelf) {
            this.bookshelf = bookshelf;
            this.savedBookshelf = bookshelf;
            this.saveCalled = true;
        }

        @Override
        public boolean existsOnBookshelf(Book book){
            boolean bookNull = book != null;
            boolean exists = false;
            for (Book b: this.bookshelf.getBooks()){
                if (b.equals(book)) {
                    exists = true;
                    break;
                }
            }
            return bookNull && exists;
        }
    } 

    // Fake presenter for testing
    private static class TestPresenter implements AddBookOutputBoundary {

        private boolean successCalled = false;

        @Override
        public void prepareSuccessView(AddBookOutputData outputData) {
            successCalled = true;
        }

        @Override
        public void prepareFailView(String errorMessage) {
            successCalled = false;
        }
    }
}