package interface_adapter.addBook;

import use_case.addBook.AddBookOutputBoundary;
import use_case.addBook.AddBookOutputData;

/**
 *The Presenter for the add book use case.
 */
public class AddBookPresenter implements AddBookOutputBoundary {
    private final BookViewModel bookViewModel;

    public AddBookPresenter(BookViewModel bookViewModel){
        this.bookViewModel = bookViewModel;
    }

    @Override
    public void prepareSuccessView(AddBookOutputData response){
        this.bookViewModel.setMessage(response.getTitle() + " by "+ response.getAuthor() +" has been successfully added to the shelf.");
    }

    @Override
    public void prepareFailView(String errorMessage){
        this.bookViewModel.setMessage(errorMessage);
    }
}
