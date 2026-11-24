package view;

import entity.Book;
import interface_adapter.search.SearchController;
import interface_adapter.search.SearchViewModel;
import interface_adapter.search.SearchViewModelListener;
import java.util.List;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * The View for when the user is logging into the program.
 */
public class SearchView extends JPanel implements ActionListener, SearchViewModelListener {
    private final JPanel resultsPanel = new JPanel();

    private final SearchViewModel searchViewModel;

    private final JTextField searchInputField = new JTextField(25);
    private final JLabel searchErrorField = new JLabel();
    private final JButton searchButton = new JButton("Search");

    private SearchController searchController = null;

    public SearchView(SearchViewModel searchViewModel) {

        this.searchViewModel = searchViewModel;
        this.searchViewModel.addListener(this);

        JPanel searchPanel = new JPanel();
        searchPanel.add(searchInputField);
        searchPanel.add(searchButton);

        searchButton.addActionListener(
            new ActionListener() {
                public void actionPerformed(ActionEvent evt) {
                    if (evt.getSource().equals(searchButton)) {
                        searchController.execute(searchInputField.getText());
                    }
                }
            }
        );

        this.add(searchPanel, BorderLayout.NORTH);
        this.add(new JScrollPane(resultsPanel), BorderLayout.CENTER);
    }

    /**
     * React to a button click that results in evt.
     * @param evt the ActionEvent to react to
     */
    public void actionPerformed(ActionEvent evt) {
        System.out.println("Click " + evt.getActionCommand());
    }

    @Override
    public void stateChanged() {
        List<Book> books = searchViewModel.getState().getResult();
        showResults(books);
        searchErrorField.setText(searchViewModel.getState().getResultError());
    }

    public void setSearchController(SearchController searchController) {
        this.searchController = searchController;
    }

    private void showResults(List<Book> books) {
        resultsPanel.removeAll();

        for (Book book : books) {
            JButton button = new JButton(book.getTitle());
            button.addActionListener();
            resultsPanel.add(button);
        }

        resultsPanel.revalidate();
        resultsPanel.repaint();
    }
}
