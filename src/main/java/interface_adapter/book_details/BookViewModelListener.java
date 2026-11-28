package interface_adapter.book_details;

public interface BookViewModelListener {
    /*
     * Called when shelved book state has changed. Listener
     * should read new state from view model and update what is displayed.
     */
    void shelvedBookChanged();
}
