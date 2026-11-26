package interface_adapter.search;

import entity.Book;

import java.util.ArrayList;
import java.util.List;

/**
 * The state for the Search View Model.
 */
public class SearchState {
    private String resultError;
    private List<String> titles = List.of();
    private List<String> authors = List.of();
    private List<Integer> coverIds = List.of();
    private List<String> openLibraryIds = List.of();

    public String getResultError() {
        return resultError;
    }

    public void setResult(
            List<String> titles,
            List<String> authors,
            List<Integer> coverIds,
            List<String> openLibraryIds
    ) {
        int size = titles.size();
        if (authors.size() != size || coverIds.size() != size || openLibraryIds.size() != size) {
            throw new IllegalArgumentException("Search result lists must have the same size.");
        }

        this.titles = new ArrayList<>(titles);
        this.authors = new ArrayList<>(authors);
        this.coverIds = new ArrayList<>(coverIds);
        this.openLibraryIds = new ArrayList<>(openLibraryIds);
    }

    public List<String> getTitles() {
        return titles;
    }

    public List<String> getAuthors() {
        return authors;
    }

    public List<Integer> getCoverIds() {
        return coverIds;
    }

    public List<String> getOpenLibraryIds() {
        return openLibraryIds;
    }

    public void setResultError(String resultError) {
        this.resultError = resultError;
    }

    @Override
    public String toString() {
        return "SearchState{" +
                "titles=" + titles +
                ", authors=" + authors +
                ", coverIds=" + coverIds +
                ", openLibraryIds=" + openLibraryIds +
                ", resultError=" + resultError +
                '}';
    }
}
