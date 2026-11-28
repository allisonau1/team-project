package use_case.removeBook;

import entity.Book;
import entity.Bookshelf;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class RemoveBookInteractorTest {
    private static final String DUNE_ID = "OL893415W";
    private static final String EMMA_ID = "OL66554W";

    // This class acts as our "presenter" for our test.
    // It records what data is passed into its method calls,
    // then the test makes assertions using this information.
    private static class OutputRecorder implements RemoveBookOutputBoundary {
        RemoveBookOutputData success;
        String failure;

        @Override
        public void prepareSuccessView(RemoveBookOutputData data) {
            success = data;
        }

        @Override
        public void prepareFailView(String error) {
            failure = error;
        }
    }

    // This class acts as our bookshelf DAO for our test.
    // It keeps the bookshelf in memory instead of a file,
    // and counts how many times the bookshelf is saved.
    private static class InMemoryBookshelfDataAccessObject implements RemoveBookDataAccessInterface {
        Bookshelf bookshelf;
        int saveCount = 0;

        InMemoryBookshelfDataAccessObject(Bookshelf bookshelf) {
            this.bookshelf = bookshelf;
        }

        @Override
        public Book getBook(String openLibraryId) {
            for (Book book : bookshelf.getBooks()) {
                if (book.getOpenLibraryId().equals(openLibraryId)) {
                    return book;
                }
            }
            return null;
        }

        @Override
        public Bookshelf getBookshelf() {
            return bookshelf;
        }

        @Override
        public void saveBookshelf(Bookshelf bookshelf) {
            this.bookshelf = bookshelf;
            saveCount++;
        }
    }

    // Returns a bookshelf with Dune and Emma on it.
    private static Bookshelf bookshelfWithDuneAndEmma() {
        Bookshelf bookshelf = new Bookshelf();
        bookshelf.addBook(new Book(DUNE_ID, "Dune", "Frank Herbert", 1965,
                "9780441013593", 11481354));
        bookshelf.addBook(new Book(EMMA_ID, "Emma", "Jane Austen", 1815,
                null, null));
        return bookshelf;
    }

    @Test
    void successTest() {
        RemoveBookInputData inputData = new RemoveBookInputData(DUNE_ID);
        InMemoryBookshelfDataAccessObject bookshelfRepository =
                new InMemoryBookshelfDataAccessObject(bookshelfWithDuneAndEmma());
        OutputRecorder recorder = new OutputRecorder();

        RemoveBookInputBoundary interactor = new RemoveBookInteractor(bookshelfRepository, recorder);
        interactor.execute(inputData);

        // assertions of correctness using the recorder values
        assertNotNull(recorder.success);
        assertEquals(DUNE_ID, recorder.success.getOpenLibraryId());
        assertEquals("Dune", recorder.success.getTitle());
        assertNull(recorder.failure);
        // checking that Dune was actually removed and the bookshelf was saved,
        // and that Emma is still on the bookshelf
        assertNull(bookshelfRepository.getBook(DUNE_ID));
        assertNotNull(bookshelfRepository.getBook(EMMA_ID));
        assertEquals(1, bookshelfRepository.getBookshelf().getBooks().size());
        assertEquals(1, bookshelfRepository.saveCount);
    }

    @Test
    void failureBookNotOnBookshelfTest() {
        RemoveBookInputData inputData = new RemoveBookInputData("OL999999W");
        InMemoryBookshelfDataAccessObject bookshelfRepository =
                new InMemoryBookshelfDataAccessObject(bookshelfWithDuneAndEmma());
        OutputRecorder recorder = new OutputRecorder();

        RemoveBookInputBoundary interactor = new RemoveBookInteractor(bookshelfRepository, recorder);
        interactor.execute(inputData);

        // assertions of correctness using the recorder values
        assertNull(recorder.success);
        assertEquals("This book is not on your bookshelf.", recorder.failure);
        // checking that nothing was removed or saved
        assertEquals(2, bookshelfRepository.getBookshelf().getBooks().size());
        assertEquals(0, bookshelfRepository.saveCount);
    }

    @Test
    void failureEmptyBookshelfTest() {
        RemoveBookInputData inputData = new RemoveBookInputData(DUNE_ID);
        InMemoryBookshelfDataAccessObject bookshelfRepository =
                new InMemoryBookshelfDataAccessObject(new Bookshelf());
        OutputRecorder recorder = new OutputRecorder();

        RemoveBookInputBoundary interactor = new RemoveBookInteractor(bookshelfRepository, recorder);
        interactor.execute(inputData);

        // assertions of correctness using the recorder values
        assertNull(recorder.success);
        assertEquals("This book is not on your bookshelf.", recorder.failure);
        assertEquals(0, bookshelfRepository.saveCount);
    }

    @Test
    void failureBookAlreadyRemovedTest() {
        InMemoryBookshelfDataAccessObject bookshelfRepository =
                new InMemoryBookshelfDataAccessObject(bookshelfWithDuneAndEmma());

        // Remove Dune once so that when we remove it again it is already gone
        RemoveBookInputBoundary firstInteractor =
                new RemoveBookInteractor(bookshelfRepository, new OutputRecorder());
        firstInteractor.execute(new RemoveBookInputData(DUNE_ID));

        OutputRecorder recorder = new OutputRecorder();

        RemoveBookInputBoundary interactor = new RemoveBookInteractor(bookshelfRepository, recorder);
        interactor.execute(new RemoveBookInputData(DUNE_ID));

        // assertions of correctness using the recorder values
        assertNull(recorder.success);
        assertEquals("This book is not on your bookshelf.", recorder.failure);
        // only the first removal should have saved the bookshelf
        assertEquals(1, bookshelfRepository.saveCount);
    }
}
