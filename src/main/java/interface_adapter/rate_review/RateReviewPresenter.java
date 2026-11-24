package interface_adapter.rate_review;
import use_case.rate_review.RateReviewOutputData;
import use_case.rate_review.RateReviewOutputBoundary;

/*
 * The presenter for the Rate and Review Use Case.
 * Updates the shelved book view model and its listeners.
 */
public class RateReviewPresenter implements RateReviewOutputBoundary{
    private final ShelvedBookViewModel shelvedBookViewModel;
    public RateReviewPresenter(ShelvedBookViewModel shelvedBookViewModel){
        this.shelvedBookViewModel = shelvedBookViewModel;
    }

    /*
     * Shows the saved rating and review and a "Saved." message.
     * @param reviewOutputData the saved rating and review
     */
    public void prepareSuccessView(RateReviewOutputData reviewOutputData){
        final ShelvedBookState state = shelvedBookViewModel.getState();
        state.setRating(reviewOutputData.getRating());
        state.setReviewText(reviewOutputData.getReview());
        state.setMessage("Saved.");
        state.setErrorMessage("");
        shelvedBookViewModel.setState(state);
        shelvedBookViewModel.notifyStateChanged();
    }
    /*
     * Shows an error message. The review and rating stays unchanged.
     * @param error explains the error
     */
    public void prepareFailView(String error){
        final ShelvedBookState state = shelvedBookViewModel.getState();
        state.setErrorMessage(error);
        shelvedBookViewModel.setState(state);
        shelvedBookViewModel.notifyStateChanged();

    }
}
