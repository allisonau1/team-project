package interface_adapter.rate_review;

import java.util.ArrayList;
import java.util.List;

public class ShelvedBookViewModel {
    private final List<ShelvedBookViewModelListener> listeners = new ArrayList<>();
    private ShelvedBookState state = new ShelvedBookState();

    public ShelvedBookState getState() {
        return this.state;
    }

    public void setState(ShelvedBookState state) {
        this.state = state;
    }

    /*
     * Adds a listener to be told when state changes
     * @param listener the listener to add
     */
    public void addListener(ShelvedBookViewModelListener listener){
        listeners.add(listener);
    }

    /*
     * Tells every added listener state has changed
     */
    public void notifyStateChanged() {
        for (ShelvedBookViewModelListener listener:listeners){
            listener.shelvedBookChanged();
        }
    }
}
