package interface_adapter.sort_bookshelf;

/**
 * Listener for changes to the sort bookshelf state.
 */
public interface SortBookshelfViewModelListener {

    /**
     * The sort bookshelf state was updated.
     */
    void stateChanged();
}