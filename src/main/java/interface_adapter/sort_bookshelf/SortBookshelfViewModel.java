package interface_adapter.sort_bookshelf;

import java.util.ArrayList;
import java.util.List;

/**
 * The ViewModel for sorting the bookshelf.
 */
public class SortBookshelfViewModel {
    private final List<SortBookshelfViewModelListener> listeners = new ArrayList<>();

    private SortBookshelfState state = new SortBookshelfState();

    public SortBookshelfState getState() {
        return this.state;
    }

    public void setState(SortBookshelfState state) {
        this.state = state;
    }

    public void addListener(SortBookshelfViewModelListener listener) {
        listeners.add(listener);
    }

    public void notifyStateChanged() {
        for (SortBookshelfViewModelListener listener : listeners) {
            listener.stateChanged();
        }
    }
}