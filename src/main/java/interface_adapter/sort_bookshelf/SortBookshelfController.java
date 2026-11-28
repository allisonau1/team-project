package interface_adapter.sort_bookshelf;

import use_case.sort_bookshelf.SortBookshelfInputBoundary;
import use_case.sort_bookshelf.SortBookshelfInputData;
import use_case.sort_bookshelf.SortOption;

/**
 * The Controller for the Sort Bookshelf use case.
 */
public class SortBookshelfController {
    private final SortBookshelfInputBoundary interactor;

    /**
     * Creates the controller.
     * @param interactor the input boundary that runs the use case
     */
    public SortBookshelfController(SortBookshelfInputBoundary interactor) {
        this.interactor = interactor;
    }

    /**
     * Sorts the bookshelf using the given option.
     * @param sortOption the sort the user selected.
     */
    public void execute(SortOption sortOption) {
        interactor.execute(new SortBookshelfInputData(sortOption));
    }
}