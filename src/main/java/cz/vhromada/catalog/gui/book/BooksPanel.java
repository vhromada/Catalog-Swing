package cz.vhromada.catalog.gui.book;

import javax.swing.JPanel;
import javax.swing.JTabbedPane;

import cz.vhromada.catalog.facade.BookFacade;
import cz.vhromada.catalog.facade.to.BookCategoryTO;
import cz.vhromada.catalog.facade.to.BookTO;
import cz.vhromada.catalog.gui.commons.AbstractInfoDialog;
import cz.vhromada.catalog.gui.commons.AbstractOverviewDataPanel;
import cz.vhromada.validators.Validators;

/**
 * A class represents panel with books' data.
 *
 * @author Vladimir Hromada
 */
public class BooksPanel extends AbstractOverviewDataPanel<BookTO> {

    /**
     * SerialVersionUID
     */
    private static final long serialVersionUID = 1L;

    /**
     * Facade for books
     */
    private BookFacade bookFacade;

    /**
     * TO for book category
     */
    private BookCategoryTO bookCategory;

    /**
     * Creates a new instance of BooksPanel.
     *
     * @param bookFacade   facade for books
     * @param bookCategory TO for book category
     * @throws IllegalArgumentException if facade for books is null
     *                                  or TO for book category is null
     */
    public BooksPanel(final BookFacade bookFacade, final BookCategoryTO bookCategory) {
        super(getBooksListDataModel(bookFacade, bookCategory));

        this.bookFacade = bookFacade;
        this.bookCategory = bookCategory;
    }

    /**
     * Sets a new value to TO for book category.
     *
     * @param bookCategory new value
     * @throws IllegalArgumentException if TO for book category is null
     */
    public void setBookCategory(final BookCategoryTO bookCategory) {
        Validators.validateArgumentNotNull(bookCategory, "TO for book category");

        this.bookCategory = bookCategory;
    }

    @Override
    public void newData() {
        throw new UnsupportedOperationException("Creating new data is not allowed for books.");
    }

    @Override
    public void clearSelection() {
        throw new UnsupportedOperationException("Clearing selection is not allowed for books.");
    }

    @Override
    public void save() {
        throw new UnsupportedOperationException("Saving data is not allowed for books.");
    }

    @Override
    public boolean isSaved() {
        throw new UnsupportedOperationException("Testing if data are saved is not allowed for books.");
    }

    @Override
    protected AbstractInfoDialog<BookTO> getInfoDialog(final boolean add, final BookTO data) {
        return add ? new BookInfoDialog() : new BookInfoDialog(data);
    }

    @Override
    protected void addData(final BookTO data) {
        data.setBookCategory(bookCategory);
        bookFacade.add(data);
    }

    @Override
    protected void deleteData() {
        throw new UnsupportedOperationException("Deleting data is not allowed for books.");
    }

    @Override
    protected void updateData(final BookTO data) {
        bookFacade.update(data);
    }

    @Override
    protected void removeData(final BookTO data) {
        bookFacade.remove(data);
    }

    @Override
    protected void duplicatesData(final BookTO data) {
        bookFacade.duplicate(data);
    }

    @Override
    protected void moveUpData(final BookTO data) {
        bookFacade.moveUp(data);
    }

    @Override
    protected void moveDownData(final BookTO data) {
        bookFacade.moveDown(data);
    }

    @Override
    protected JPanel getDataPanel(final BookTO data) {
        return new BookDataPanel(data);
    }

    @Override
    protected void updateDataOnChange(final JTabbedPane dataPanel, final BookTO data) {
    }

    /**
     * Returns data model for list with books.
     *
     * @param facade             facade for books
     * @param bookCategoryObject TO for book category
     * @return data model for list with books
     * @throws IllegalArgumentException if facade for books is null
     *                                  or TO for bookCategory is null
     */
    private static BooksListDataModel getBooksListDataModel(final BookFacade facade, final BookCategoryTO bookCategoryObject) {
        return new BooksListDataModel(facade, bookCategoryObject);
    }

}
