package interface_adapter.rate_review;
/*
 * A listener that is told when ShelvedBookViewModel's state is changed.
 */

public interface ShelvedBookViewModelListener {
    /*
     * Called when shelved book state has changed. Listener
     * should read new state from view model and update what is displayed.
     */
    void shelvedBookChanged();
}
