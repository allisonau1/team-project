package interface_adapter.addBook;

import java.util.ArrayList;
import java.util.List;

public class AddBookViewModel {

    private final List<AddBookViewModelListener> listeners = new ArrayList<>();

    private AddBookState state = new AddBookState();

    public AddBookState getState() {
        return this.state;
    }

    public void setState(AddBookState state) {
        this.state = state;
        notifyStateChanged();
    }

    public void setMessage(String message) {
        state.setMessage(message);
        notifyStateChanged();
    }

    /*
     * Adds a listener to be notified when the state changes.
     * @param listener the listener to add
     */
    public void addListener(AddBookViewModelListener listener) {
        listeners.add(listener);
    }

    /*
     * Notifies all listeners that the state has changed.
     */
    public void notifyStateChanged() {
        for (AddBookViewModelListener listener : listeners) {
            listener.addBookStateChanged();
        }
    }
}