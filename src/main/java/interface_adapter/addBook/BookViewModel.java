package interface_adapter.addBook;

import java.util.ArrayList;
import java.util.List;

public class BookViewModel {
    private final List<BookViewModelListener> listeners = new ArrayList<>();
    private BookViewState state = new BookViewState();

    public BookViewState getState() {
        return this.state;
    }

    public void setState(BookViewState state) {
        this.state = state;
    }

    /*
     * Adds a listener to be told when state changes
     * @param listener the listener to add
     */
    public void addListener(BookViewModelListener listener) {
        listeners.add(listener);
    }

    /*
     * Tells every added listener state has changed
     */
    public void notifyStateChanged() {
        for (BookViewModelListener listener : listeners) {
            listener.shelvedBookChanged();
        }
    }

    public void setSelectedBook(
            String openLibraryID,
            String title,
            String author,
            String isbn,
            String publicationYear,
            Integer coverID) {

        state.setOpenLibraryID(openLibraryID);
        state.setTitle(title);
        state.setAuthor(author);
        state.setIsbn(isbn);
        state.setPublicationYear(publicationYear);
        state.setCoverID(coverID);

        notifyStateChanged();
    }
}