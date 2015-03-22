package cz.vhromada.catalog.gui.category;

import java.util.List;

import javax.swing.GroupLayout;
import javax.swing.JLabel;

import cz.vhromada.catalog.facade.BookFacade;
import cz.vhromada.catalog.facade.to.BookCategoryTO;
import cz.vhromada.catalog.facade.to.BookTO;
import cz.vhromada.catalog.gui.commons.AbstractDataPanel;
import cz.vhromada.validators.Validators;

/**
 * A class represents panel with book category's data.
 *
 * @author Vladimir Hromada
 */
public class BookCategoryDataPanel extends AbstractDataPanel<BookCategoryTO> {

    /**
     * SerialVersionUID
     */
    private static final long serialVersionUID = 1L;

    /**
     * Facade for books
     */
    private BookFacade bookFacade;

    /**
     * Label for name
     */
    private JLabel nameLabel = new JLabel("Name");

    /**
     * Label with name
     */
    private JLabel nameData = new JLabel();

    /**
     * Label for count of books
     */
    private JLabel booksCountLabel = new JLabel("Count of books");

    /**
     * Label with count of books
     */
    private JLabel booksCountData = new JLabel();

    /**
     * Label for note
     */
    private JLabel noteLabel = new JLabel("Note");

    /**
     * Label with note
     */
    private JLabel noteData = new JLabel();

    /**
     * Creates a new instance of BookCategoryDataPanel.
     *
     * @param bookCategory TO for book category
     * @param bookFacade   facade for books
     * @throws IllegalArgumentException if book category is null
     *                                  or facade for books is null
     */
    public BookCategoryDataPanel(final BookCategoryTO bookCategory, final BookFacade bookFacade) {
        Validators.validateArgumentNotNull(bookFacade, "Facade for books");

        this.bookFacade = bookFacade;

        updateData(bookCategory);

        initData(nameLabel, nameData);
        initData(booksCountLabel, booksCountData);
        initData(noteLabel, noteData);

        createLayout();
    }

    @Override
    protected void updateComponentData(final BookCategoryTO data) {
        nameData.setText(data.getName());
        booksCountData.setText(getSongsCount(data));
        noteData.setText(data.getNote());
    }

    @Override
    protected String getCzWikiUrl() {
        throw new IllegalStateException("Getting URL to czech Wikipedia page is not allowed for book categories.");
    }

    @Override
    protected String getEnWikiUrl() {
        throw new IllegalStateException("Getting URL to english Wikipedia page is not allowed for book categories.");
    }

    @Override
    protected String getCsfdUrl() {
        throw new IllegalStateException("Getting URL to ČSFD page is not allowed for book categories.");
    }

    @Override
    protected int getImdbUrl() {
        throw new IllegalStateException("Getting URL to IMDB page is not allowed for book categories.");
    }

    @Override
    protected GroupLayout.Group getHorizontalLayoutWithComponents(final GroupLayout layout, final GroupLayout.Group group) {
        return group
                .addGroup(createHorizontalDataComponents(layout, nameLabel, nameData))
                .addGroup(createHorizontalDataComponents(layout, booksCountLabel, booksCountData))
                .addGroup(createHorizontalDataComponents(layout, noteLabel, noteData));
    }

    @Override
    protected GroupLayout.Group getVerticalLayoutWithComponents(final GroupLayout layout, final GroupLayout.Group group) {
        return group
                .addGroup(createVerticalComponents(layout, nameLabel, nameData))
                .addGap(VERTICAL_GAP_SIZE)
                .addGroup(createVerticalComponents(layout, booksCountLabel, booksCountData))
                .addGap(VERTICAL_GAP_SIZE)
                .addGroup(createVerticalComponents(layout, noteLabel, noteData));
    }

    /**
     * Returns count of book category's books.
     *
     * @param bookCategory TO for book category
     * @return count of book category's books
     */
    private String getSongsCount(final BookCategoryTO bookCategory) {
        final List<BookTO> books = bookFacade.findBooksByBookCategory(bookCategory);
        return Integer.toString(books.size());
    }

}
