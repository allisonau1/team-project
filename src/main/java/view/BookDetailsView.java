package view;

import interface_adapter.add_book.*;
import interface_adapter.book_details.BookViewModel;
import interface_adapter.book_details.BookViewModelListener;
import interface_adapter.book_details.BookViewState;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.net.URL;

/**
 * Swing view displaying the details of a selected book.
 * Allows the user to add the selected book to their bookshelf.
 */
public class BookDetailsView extends JPanel implements BookViewModelListener, AddBookViewModelListener{

    private final BookViewModel viewModel;
    private final AddBookViewModel addBookViewModel;
    private AddBookController addBookController;

    private final CoverPanel coverPanel = new CoverPanel();
    private final JLabel titleLabel = new JLabel("Book Title");
    private final JLabel authorLabel = new JLabel("Author");
    private final JLabel isbnLabel = new JLabel("ISBN: N/A");
    private final JLabel pubYearLabel = new JLabel("Publication Year: N/A");

    private final JLabel statusLabel = new JLabel(" ");
    private final JButton addButton = new JButton("Add to Bookshelf");

    private Integer shownCoverId;

    public BookDetailsView(BookViewModel viewModel, AddBookViewModel addBookViewModel) {
        this.viewModel = viewModel;
        this.addBookViewModel = addBookViewModel;
        this.viewModel.addListener(this);
        this.addBookViewModel.addListener(this);

        setLayout(new BorderLayout(10, 10));
        setBorder(BorderFactory.createEmptyBorder(12, 12, 12, 12));

        add(buildTopSection(), BorderLayout.NORTH);
        add(buildBottomSection(), BorderLayout.SOUTH);

        // Add the selected book to the bookshelf.
        addButton.addActionListener(evt -> {
            if (addBookController != null) {
                final BookViewState state = viewModel.getState();
                addBookController.execute(state.getOpenLibraryID());
            }
        });
    }

    public void setAddBookController(AddBookController controller) {
        this.addBookController = controller;
    }

    // VIEW MODEL LISTENERS

    /**
     * Called by the view model whenever its state changes.
     * Updates the displayed book details.
     */
    @Override
    public void shelvedBookChanged() {
        final BookViewState state = viewModel.getState();

        titleLabel.setText(
                state.getTitle().isEmpty() ? "Book Title" : state.getTitle());

        authorLabel.setText(
                state.getAuthor().isEmpty() ? "Author" : state.getAuthor());

        isbnLabel.setText("ISBN: " + state.getIsbn());
        pubYearLabel.setText("Publication Year: " + state.getPublicationYear());

        // Only reload the cover if its ID has changed.
        if (state.getCoverId() == null
                ? shownCoverId != null
                : !state.getCoverId().equals(shownCoverId)) {

            shownCoverId = state.getCoverId();
            coverPanel.loadCover(shownCoverId);
        }

        if (!state.getErrorMessage().isEmpty()) {
            statusLabel.setForeground(Color.RED);
            statusLabel.setText(state.getErrorMessage());
        } else {
            statusLabel.setForeground(Color.DARK_GRAY);
            statusLabel.setText(
                    state.getMessage().isEmpty() ? " " : state.getMessage());
        }
    }

    /**
     * Called by the add book view model whenever its state changes.
     * Updates the success or error message
     */
    @Override
    public void addBookStateChanged() {
        AddBookState state = addBookViewModel.getState();

        statusLabel.setText(state.getMessage());
    }

    // LAYOUT

    private JPanel buildTopSection() {
        final JPanel top = new JPanel(new BorderLayout(12, 0));

        coverPanel.setPreferredSize(new Dimension(130, 200));
        top.add(coverPanel, BorderLayout.WEST);

        titleLabel.setFont(
                titleLabel.getFont().deriveFont(Font.BOLD, 28f));

        authorLabel.setFont(
                authorLabel.getFont().deriveFont(Font.BOLD, 16f));

        final JSeparator separator = new JSeparator();
        separator.setMaximumSize(new Dimension(Integer.MAX_VALUE, 2));

        final JPanel info = new JPanel();
        info.setLayout(new BoxLayout(info, BoxLayout.Y_AXIS));

        info.add(left(titleLabel));
        info.add(left(authorLabel));
        info.add(Box.createVerticalStrut(6));
        info.add(separator);
        info.add(Box.createVerticalStrut(6));
        info.add(left(isbnLabel));
        info.add(left(pubYearLabel));
        info.add(Box.createVerticalGlue());

        top.add(info, BorderLayout.CENTER);

        return top;
    }

    private JPanel buildBottomSection() {
        final JPanel bottom = new JPanel();
        bottom.setLayout(new BoxLayout(bottom, BoxLayout.Y_AXIS));

        statusLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        addButton.setAlignmentX(Component.CENTER_ALIGNMENT);

        bottom.add(statusLabel);
        bottom.add(Box.createVerticalStrut(6));
        bottom.add(addButton);

        return bottom;
    }

    private static JComponent left(JComponent component) {
        component.setAlignmentX(Component.LEFT_ALIGNMENT);
        return component;
    }

    // COVER IMAGE

    /**
     * Displays a book cover from Open Library using its cover ID.
     */
    private static class CoverPanel extends JPanel {

        private Image image;

        CoverPanel() {
            setBackground(Color.GRAY);
        }

        void loadCover(Integer coverId) {
            image = null;
            repaint();

            if (coverId == null) {
                return;
            }

            new SwingWorker<BufferedImage, Void>() {
                @Override
                protected BufferedImage doInBackground() throws Exception {
                    return ImageIO.read(new URL(
                            "https://covers.openlibrary.org/b/id/"
                                    + coverId + "-M.jpg"));
                }

                @Override
                protected void done() {
                    try {
                        image = get();
                        repaint();
                    } catch (Exception ignored) {
                        // Keep the gray placeholder if loading fails.
                    }
                }
            }.execute();
        }

        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);

            if (image != null) {
                g.drawImage(image, 0, 0, getWidth(), getHeight(), null);
            }
        }
    }
}
