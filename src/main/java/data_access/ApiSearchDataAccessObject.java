package data_access;

import entity.Book;
import okhttp3.HttpUrl;
import org.json.JSONArray;
import org.json.JSONObject;
import use_case.search.SearchDataAccessInterface;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * DAO for user data implemented using an API.
 */
public class ApiSearchDataAccessObject implements SearchDataAccessInterface {
    private final OkHttpClient client = new OkHttpClient();

    public static void main(String[] args) {
        ApiSearchDataAccessObject dao = new ApiSearchDataAccessObject();
        dao.search("Harry Potter");
    }

    /**
     * Construct this DAO for searching from the API.
     * @param query the query that pass into the API
     * @throws IOException if there is an IOException when accessing the file
     */
    public List<Book> search(String query) {
        HttpUrl url = HttpUrl.get("https://openlibrary.org/search.json")
                .newBuilder()
                .addQueryParameter("q", query)
                .build();

        Request request = new Request.Builder()
                .url(url)
                .build();

        List<Book> body = null;
        try (Response response = client.newCall(request).execute()) {
            if (response.isSuccessful()) {
                body = parseBooks(response.body().string());
            } else {
                System.out.println("Request failed with code " + response.code());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return body;
    }

    private List<Book> parseBooks(String response) {
        JSONArray docs = new JSONObject(response).getJSONArray("docs");
        int resultCount = Math.min(10, docs.length());
        List<Book> books = new ArrayList<>();

        for (int i = 0; i < resultCount; i++) {
            JSONObject doc = docs.getJSONObject(i);

            // Extract the author
            JSONArray authors = doc.optJSONArray("author_name");
            String author = authors == null ? null : authors.optString(0, null);

            // Extract the isbn
            JSONArray isbns = doc.optJSONArray("isbn");
            String isbn = isbns == null ? null : isbns.optString(0, null);

            // Extract first publication year
            Integer publicationYear = doc.isNull("first_publish_year")
                    ? null : doc.getInt("first_publish_year");

            // Extract cover id
            Integer coverId = doc.isNull("cover_i")
                    ? null : doc.getInt("cover_i");

            books.add(new Book(
                    doc.getString("key"),
                    doc.getString("title"),
                    author,
                    publicationYear,
                    isbn,
                    coverId
            ));
        }

        return books;
    }
}
