package interface_adapter.rate_review;

/**
 * The state for the Shelved Book View. Which book is shown, its display details,
 * its rating and review, and any status or error message.
 * Holds only strings and numbers so that it does not depend on the Book entity.
 */
public class ShelvedBookState {
    private String openLibraryID = "";
    private String title = "";
    private String author = "";
    private String isbn = "N/A";
    private String publicationYear = "N/A";
    private String dateAdded = "N/A";
    private Integer coverId;
    private Integer rating;
    private String reviewText = "";
    private String message = "";
    private String errorMessage = "";

    public String getOpenLibraryID() {
        return openLibraryID;
    }

    public void setOpenLibraryID(String openLibraryID) {
        this.openLibraryID = openLibraryID;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getIsbn() {
        return isbn;
    }

    public void setIsbn(String isbn) {
        this.isbn = isbn;
    }

    public String getPublicationYear() {
        return publicationYear;
    }

    public void setPublicationYear(String publicationYear) {
        this.publicationYear = publicationYear;
    }

    public String getDateAdded() {
        return dateAdded;
    }

    public void setDateAdded(String dateAdded) {
        this.dateAdded = dateAdded;
    }

    public Integer getCoverId() {
        return coverId;
    }

    public void setCoverId(Integer coverId) {
        this.coverId = coverId;
    }

    public Integer getRating() {
        return rating;
    }

    public void setRating(Integer rating) {
        this.rating = rating;
    }

    public String getReviewText() {
        return reviewText;
    }

    public void setReviewText(String reviewText) {
        this.reviewText = reviewText;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }
}