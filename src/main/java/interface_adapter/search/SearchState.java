package interface_adapter.search;

import entity.Book;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * The state for the Search View Model.
 */
public class SearchState {
    private String resultError;
    private List<String> titles = List.of();
    private List<String> authors = List.of();
    private List<Integer> coverIds = List.of();
    private List<String> openLibraryIds = List.of();
    private List<String> isbns = List.of();
    private List<Integer> publicationYears = List.of();

    public String getResultError() {
        return resultError;
    }

    public void setResult(List<Map<String, Object>> results) {
        int size = titles.size();
        if (authors.size() != size || coverIds.size() != size || openLibraryIds.size() != size) {
            throw new IllegalArgumentException("Search result lists must have the same size.");
        }
        for (Map<String, Object> result : results) {
            titles.add((String) result.get("title"));
            authors.add((String) result.get("author"));
            coverIds.add((Integer) result.get("coverID"));
            openLibraryIds.add((String) result.get("openLibraryId"));
            isbns.add((String) result.get("isbn"));
            publicationYears.add((Integer) result.get("publicationYear"));
        }

        this.titles = new ArrayList<>(titles);
        this.authors = new ArrayList<>(authors);
        this.coverIds = new ArrayList<>(coverIds);
        this.openLibraryIds = new ArrayList<>(openLibraryIds);
        this.isbns = new ArrayList<>(isbns);
        this.publicationYears = new ArrayList<>(publicationYears);
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

    public List<String> getIsbns() {
        return isbns;
    }

    public List<Integer> getPublicationYears() {
        return publicationYears;
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
