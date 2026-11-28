package view;

import interface_adapter.rate_review.RateReviewController;
import interface_adapter.rate_review.ShelvedBookState;
import interface_adapter.rate_review.ShelvedBookViewModel;
import interface_adapter.rate_review.ShelvedBookViewModelListener;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.geom.Path2D;
import java.awt.image.BufferedImage;
import java.net.URL;

/**
 * Plain (no custom colours or fonts) Swing view of a single shelved book's details
 * with a rating dropdown, star display, review box, Save Review & Rating button
 * and Remove Book button.
 * It only reads from the view model's state; it does not depend on the Book entity.
 */
public class ShelvedBookView extends JPanel implements ShelvedBookViewModelListener {

    private final ShelvedBookViewModel viewModel;
    private RateReviewController rateReviewController;

    private final CoverPanel coverPanel = new CoverPanel();
    private final JLabel titleLabel = new JLabel("Book Title");
    private final JLabel authorLabel = new JLabel("Author");
    private final JLabel dateAddedLabel = new JLabel("Date Added: N/A");
    private final JLabel isbnLabel = new JLabel("ISBN: N/A");
    private final JLabel pubYearLabel = new JLabel("Publication Year: N/A");

    // null entry = "no rating chosen yet"
    private final JComboBox<Integer> ratingBox = new JComboBox<>(new Integer[]{null, 1, 2, 3, 4, 5});
    private final StarPanel starPanel = new StarPanel();
    private final JTextArea reviewArea = new JTextArea();
    private final JLabel statusLabel = new JLabel(" ");
    private final JButton saveButton = new JButton("Save Review & Rating");
    private final JButton removeButton = new JButton("Remove Book from Shelf");

    private Integer shownCoverId;

    public ShelvedBookView(ShelvedBookViewModel viewModel) {
        this.viewModel = viewModel;
        this.viewModel.addListener(this);

        setLayout(new BorderLayout(10, 10));
        setBorder(BorderFactory.createEmptyBorder(12, 12, 12, 12));

        add(buildTopSection(), BorderLayout.NORTH);
        add(buildMiddleSection(), BorderLayout.CENTER);
        add(buildBottomSection(), BorderLayout.SOUTH);

        // Dropdown: update the stars live as the rating changes
        ratingBox.addActionListener(
                new ActionListener() {
                    public void actionPerformed(ActionEvent evt) {
                        if (evt.getSource().equals(ratingBox)) {
                            starPanel.setRating((Integer) ratingBox.getSelectedItem());
                        }
                    }
                });

        // Save: send the book id, rating and review to the controller
        saveButton.addActionListener(
                new ActionListener() {
                    public void actionPerformed(ActionEvent evt) {
                        if (evt.getSource().equals(saveButton)) {
                            if (rateReviewController != null) {
                                final ShelvedBookState state = viewModel.getState();
                                rateReviewController.execute(
                                        state.getOpenLibraryID(),
                                        (Integer) ratingBox.getSelectedItem(),
                                        reviewArea.getText());
                            }
                        }
                    }
                });

        removeButton.addActionListener(
                new ActionListener() {
                    public void actionPerformed(ActionEvent evt) {
                        if (evt.getSource().equals(removeButton)) {
                            // TODO: implement what happens when remove is clicked
                        }
                    }
                });
    }

    public void setRateReviewController(RateReviewController controller) {
        this.rateReviewController = controller;
    }

    // VIEW MODEL LISTENER

    /** Called by the view model whenever its state changes: redraw everything from the state. */
    @Override
    public void shelvedBookChanged() {
        final ShelvedBookState state = viewModel.getState();

        titleLabel.setText(state.getTitle().isEmpty() ? "Book Title" : state.getTitle());
        authorLabel.setText(state.getAuthor().isEmpty() ? "Author" : state.getAuthor());
        dateAddedLabel.setText("Date Added: " + state.getDateAdded());
        isbnLabel.setText("ISBN: " + state.getIsbn());
        pubYearLabel.setText("Publication Year: " + state.getPublicationYear());

        // only reload the cover when it actually changed (a save also fires this method)
        if (state.getCoverId() == null ? shownCoverId != null : !state.getCoverId().equals(shownCoverId)) {
            shownCoverId = state.getCoverId();
            coverPanel.loadCover(shownCoverId);
        }

        ratingBox.setSelectedItem(state.getRating());
        starPanel.setRating(state.getRating());
        reviewArea.setText(state.getReviewText());

        if (!state.getErrorMessage().isEmpty()) {
            statusLabel.setForeground(Color.RED);
            statusLabel.setText(state.getErrorMessage());
        } else {
            statusLabel.setForeground(Color.DARK_GRAY);
            statusLabel.setText(state.getMessage().isEmpty() ? " " : state.getMessage());
        }
    }

    // LAYOUT

    private JPanel buildTopSection() {
        final JPanel top = new JPanel(new BorderLayout(12, 0));

        coverPanel.setPreferredSize(new Dimension(130, 200));
        top.add(coverPanel, BorderLayout.WEST);

        titleLabel.setFont(titleLabel.getFont().deriveFont(Font.BOLD, 28f));
        authorLabel.setFont(authorLabel.getFont().deriveFont(Font.BOLD, 16f));
        final JSeparator sep = new JSeparator();
        sep.setMaximumSize(new Dimension(Integer.MAX_VALUE, 2));

        final JPanel info = new JPanel();
        info.setLayout(new BoxLayout(info, BoxLayout.Y_AXIS));
        info.add(left(titleLabel));
        info.add(left(authorLabel));
        info.add(Box.createVerticalStrut(6));
        info.add(sep);
        info.add(Box.createVerticalStrut(6));
        info.add(left(dateAddedLabel));
        info.add(left(isbnLabel));
        info.add(left(pubYearLabel));
        info.add(Box.createVerticalGlue());
        top.add(info, BorderLayout.CENTER);
        return top;
    }

    private JPanel buildMiddleSection() {
        final JPanel middle = new JPanel(new BorderLayout(0, 6));

        // rating row: "Rate Book: [dropdown]" on the left, stars on the right
        final JPanel ratingRow = new JPanel(new BorderLayout());
        final JPanel ratingLeft = new JPanel(new FlowLayout(FlowLayout.LEFT, 6, 0));
        ratingLeft.add(new JLabel("Rate Book:"));
        ratingLeft.add(ratingBox);
        ratingRow.add(ratingLeft, BorderLayout.WEST);
        ratingRow.add(starPanel, BorderLayout.EAST);

        final JPanel header = new JPanel(new BorderLayout());
        header.add(ratingRow, BorderLayout.NORTH);
        header.add(new JLabel("Review:"), BorderLayout.SOUTH);

        reviewArea.setLineWrap(true);
        reviewArea.setWrapStyleWord(true);
        reviewArea.setRows(8);

        middle.add(header, BorderLayout.NORTH);
        middle.add(new JScrollPane(reviewArea), BorderLayout.CENTER);
        return middle;
    }

    private JPanel buildBottomSection() {
        final JPanel bottom = new JPanel();
        bottom.setLayout(new BoxLayout(bottom, BoxLayout.Y_AXIS));

        statusLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        saveButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        removeButton.setAlignmentX(Component.CENTER_ALIGNMENT);

        bottom.add(statusLabel);
        bottom.add(Box.createVerticalStrut(4));
        bottom.add(saveButton);
        bottom.add(Box.createVerticalStrut(6));
        bottom.add(removeButton);
        return bottom;
    }

    private static JComponent left(JComponent c) {
        c.setAlignmentX(Component.LEFT_ALIGNMENT);
        return c;
    }

    // COVER IMAGE

    /** Gray box until the cover (from Open Library, by cover id) has loaded. */
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
                    return ImageIO.read(new URL("https://covers.openlibrary.org/b/id/" + coverId + "-M.jpg"));
                }

                @Override
                protected void done() {
                    try {
                        image = get();
                        repaint();
                    } catch (Exception ignored) {
                        // keep the gray placeholder
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

    // STAR DISPLAY

    /** Draws 5 stars; the first rating number of stars are filled. */
    private static class StarPanel extends JPanel {
        private static final int STAR_SIZE = 32;
        private static final int GAP = 6;
        private int rating;

        StarPanel() {
            setOpaque(false);
            setPreferredSize(new Dimension(5 * STAR_SIZE + 6 * GAP, STAR_SIZE + 8));
        }

        void setRating(Integer rating) {
            this.rating = rating == null ? 0 : rating;
            repaint();
        }

        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            final Graphics2D g2 = (Graphics2D) g.create();
            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            for (int i = 0; i < 5; i++) {
                final Path2D star = starShape(GAP + i * (STAR_SIZE + GAP) + STAR_SIZE / 2.0,
                        getHeight() / 2.0, STAR_SIZE / 2.0);
                g2.setColor(i < rating ? Color.ORANGE : Color.LIGHT_GRAY);
                g2.fill(star);
                g2.setColor(Color.DARK_GRAY);
                g2.draw(star);
            }
            g2.dispose();
        }

        private static Path2D starShape(double cx, double cy, double outer) {
            final double inner = outer * 0.45;
            final Path2D path = new Path2D.Double();
            for (int i = 0; i < 10; i++) {
                final double r = (i % 2 == 0) ? outer : inner;
                final double angle = Math.PI / 2 + i * Math.PI / 5;
                final double x = cx + r * Math.cos(angle);
                final double y = cy - r * Math.sin(angle);
                if (i == 0) {
                    path.moveTo(x, y);
                } else {
                    path.lineTo(x, y);
                }
            }
            path.closePath();
            return path;
        }
    }

    // QUICK MANUAL TEST (view only: fills the state by hand, no use case or entity involved)

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            final ShelvedBookViewModel viewModel = new ShelvedBookViewModel();
            final ShelvedBookView view = new ShelvedBookView(viewModel);

            // Pretend a presenter loaded a shelved book into the state.
            final ShelvedBookState state = viewModel.getState();
            state.setOpenLibraryID("OL45804W");
            state.setTitle("Fantastic Mr Fox");
            state.setAuthor("Roald Dahl");
            state.setIsbn("9780142410349");
            state.setPublicationYear("1970");
            state.setDateAdded("25/11/2026");
            state.setCoverId(8739161);
            viewModel.setState(state);
            viewModel.notifyStateChanged();

            final JFrame frame = new JFrame("Shelved Book");
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.setContentPane(view);
            frame.setSize(640, 580);
            frame.setLocationRelativeTo(null);
            frame.setVisible(true);
        });
    }
}