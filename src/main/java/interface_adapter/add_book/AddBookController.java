package interface_adapter.add_book;
import use_case.add_book.AddBookInputBoundary;
import use_case.add_book.AddBookInputData;

/**
 * The controller for the Add Book use case.
 */
public class AddBookController {
     private final AddBookInputBoundary addBookInteractor;

     public AddBookController(AddBookInputBoundary addBookInteractor){
         this.addBookInteractor = addBookInteractor;
     }

    /**
     * Executes the Add Book Use Case.
     */
    public void execute(String openLibraryId){
        final AddBookInputData addBookInputData = new AddBookInputData(openLibraryId);
        addBookInteractor.execute(addBookInputData);
    }


}
