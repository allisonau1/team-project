package interface_adapter.addBook;

import interface_adapter.addBook.AddBookState;
import interface_adapter.addBook.BookViewModelListener;

import java.util.ArrayList;
import java.util.List;

public class  AddBookViewModel {
    private final List<BookViewModelListener> listeners = new ArrayList<>();
    private AddBookState state = new AddBookState();

    public AddBookState getState() {
        return this.state;
    }

    public void setState(AddBookState state) {
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
}