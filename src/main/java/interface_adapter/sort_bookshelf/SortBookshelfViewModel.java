package interface_adapter.sort_bookshelf;

import java.util.ArrayList;
import java.util.List;

/**
 * The ViewModel for sorting the bookshelf.
 */
public class SortBookshelfViewModel {
    private final List<SortBookshelfViewModelListener> listeners = new ArrayList<>();

    private SortBookshelfState state = new SortBookshelfState();

    /**
     * Returns the current state.
     * @return the sort bookshelf state
     */
    public SortBookshelfState getState() {
        return this.state;
    }

    /**
     * Replaces the current state.
     * @param state the new state
     */
    public void setState(SortBookshelfState state) {
        this.state = state;
    }

    /**
     * Registers a listener to be notified when the state changes.
     * @param listener the listener to add
     */
    public void addListener(SortBookshelfViewModelListener listener) {
        listeners.add(listener);
    }

    /**
     * Notifies every registered listener that the state changed.
     */
    public void notifyStateChanged() {
        for (SortBookshelfViewModelListener listener : listeners) {
            listener.stateChanged();
        }
    }
}