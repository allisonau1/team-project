package interface_adapter.rate_review;
import use_case.rate_review.RateReviewInputBoundary;
import use_case.rate_review.RateReviewInputData;

/*
 * Controller for the Rate Review Use Case
 */
public class RateReviewController {
    private final RateReviewInputBoundary rateReviewInteractor;

    public RateReviewController(RateReviewInputBoundary rateReviewInteractor){
        this.rateReviewInteractor = rateReviewInteractor;
    }

    /*
     * Executes the Rate Review Use Case
     */
    public void execute (String openLibraryID, Integer rating, String review) {
        final RateReviewInputData rateReviewInputData =
                new RateReviewInputData(openLibraryID, rating, review);
        rateReviewInteractor.execute(rateReviewInputData);
        }

}

