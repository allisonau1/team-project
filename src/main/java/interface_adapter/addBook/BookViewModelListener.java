package interface_adapter.addBook;

public interface BookViewModelListener {
    /*
     * Called when shelved book state has changed. Listener
     * should read new state from view model and update what is displayed.
     */
    void shelvedBookChanged();
}
