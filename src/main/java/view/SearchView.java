package view;

import interface_adapter.search.SearchController;
import interface_adapter.search.SearchState;
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
        SearchState state = searchViewModel.getState();
        showResults(state);
        searchErrorField.setText(state.getResultError());
    }

    public void setSearchController(SearchController searchController) {
        this.searchController = searchController;
    }

    private void showResults(SearchState state) {
        resultsPanel.removeAll();

        List<String> titles = state.getTitles();
        for (int i = 0; i < titles.size(); i++) {
            JButton button = new JButton(titles.get(i));
            resultsPanel.add(button);
        }

        resultsPanel.revalidate();
        resultsPanel.repaint();
    }
}
