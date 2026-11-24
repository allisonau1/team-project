package interface_adapter.rate_review;

import entity.Book;
/**
 * The state for the Shelved Book View. Which book is shown, its rating, review,
 * and any status or error message.
 */
public class ShelvedBookState {
    private String openLibraryID = "";
    private String title = "";
    private Integer rating;
    private String reviewText = "";
    private String message = "";
    private String errorMessage = "";


    public static ShelvedBookState fromBook(Book book){
        final ShelvedBookState state = new ShelvedBookState();
        state.openLibraryID = book.getOpenLibraryId();
        state.title = book.getTitle();
        if (book.getReview() != null){
            state.rating = book.getReview().getRating();
            state.reviewText = book.getReview().getReview();
        }
        return state;

    }

    public String getOpenLibraryID(){
        return openLibraryID;
    }

    public String getTitle(){
        return title;
    }

    public Integer getRating(){
        return rating;
    }

    public void setRating(Integer rating) {
        this.rating = rating;
    }

    public String getReviewText(){
        return reviewText;
    }

    public void setReviewText(String review) {
        this.reviewText = reviewText;
    }

    public String getMessage(){
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getErrorMessage(){
        return errorMessage;
    }

    public void setErrorMessage(String message) {
        this.errorMessage = errorMessage;
    }
}
