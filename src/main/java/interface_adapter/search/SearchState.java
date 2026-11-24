package interface_adapter.search;

import entity.Book;

import java.util.List;

/**
 * The state for the Search View Model.
 */
public class SearchState {
    private String query = "";
    private String queryError;
    private List<Book> result;
    private String resultError;

    public String getQuery() {
        return query;
    }

    public String getQueryError() {
        return queryError;
    }

    public List<Book> getResult() {
        return result;
    }

    public String getResultError() {
        return resultError;
    }

    public void setQuery(String query) {
        this.query = query;
    }

    public void setQueryError(String queryError) {
        this.queryError = queryError;
    }

    public void setResult(List<Book> result) {
        this.result = result;
    }

    public void setResultError(String resultError) {
        this.resultError = resultError;
    }

    @Override
    public String toString() {
        return "SearchState{"
                + "query='" + query + '\''
                + ", result='" + result + '\''
                + '}';
    }
}
