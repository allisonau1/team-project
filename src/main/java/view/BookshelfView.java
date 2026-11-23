import javax.swing.*;

public class BookshelfView extends JPanel {

    private final BookshelfViewModel viewModel;

    private final JPanel booksPanel;
    private final JLabel messageLabel;

    public BookshelfView(BookshelfViewModel viewModel) {

        this.viewModel = viewModel;

        this.setLayout(new BorderLayout());

        // Title
        JLabel titleLabel = new JLabel("My Bookshelf");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 24));

        // Panel containing books
        booksPanel = new JPanel();
        booksPanel.setLayout(new GridLayout(0, 3, 10, 10));

        // Success message
        messageLabel = new JLabel("");

        this.add(titleLabel, BorderLayout.NORTH);
        this.add(new JScrollPane(booksPanel), BorderLayout.CENTER);
        this.add(messageLabel, BorderLayout.SOUTH);

        // Listen for changes to the ViewModel
        viewModel.addPropertyChangeListener(evt -> {
            if (BookshelfViewModel.BOOKSHELF_STATE.equals(
                    evt.getPropertyName())) {
                updateBooks();
            }

            if (BookshelfViewModel.SUCCESS_MESSAGE.equals(
                    evt.getPropertyName())) {
                messageLabel.setText(viewModel.getMessage());
            }
        });

        updateBooks();
    }

    private void updateBooks() {

        booksPanel.removeAll();

        for (BookDisplayData book : viewModel.getBooks()) {

            JPanel bookPanel = new JPanel();
            bookPanel.setLayout(new BoxLayout(
                    bookPanel, BoxLayout.Y_AXIS));

            JLabel bookTitle = new JLabel(book.getTitle());
            JLabel bookAuthor = new JLabel(book.getAuthor());

            bookPanel.add(bookTitle);
            bookPanel.add(bookAuthor);

            bookPanel.setBorder(
                    BorderFactory.createLineBorder(Color.GRAY));

            booksPanel.add(bookPanel);
        }

        booksPanel.revalidate();
        booksPanel.repaint();
    }
}