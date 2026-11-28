package interface_adapter.add_book;

import use_case.add_book.AddBookOutputBoundary;
import use_case.add_book.AddBookOutputData;

/**
 *The Presenter for the add book use case.
 */
public class AddBookPresenter implements AddBookOutputBoundary {
    private final AddBookViewModel addBookViewModel;

    public AddBookPresenter(AddBookViewModel addBookViewModel){
        this.addBookViewModel = addBookViewModel;
    }

    @Override
    public void prepareSuccessView(AddBookOutputData response){
        this.addBookViewModel.setMessage(response.getTitle() + " by "+ response.getAuthor() +" has been successfully added to the shelf.");
    }

    @Override
    public void prepareFailView(String errorMessage){
        this.addBookViewModel.setMessage(errorMessage);
    }
}
