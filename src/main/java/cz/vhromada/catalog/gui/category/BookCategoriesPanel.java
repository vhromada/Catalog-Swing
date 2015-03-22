package cz.vhromada.catalog.gui.category;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

import javax.swing.JPanel;
import javax.swing.JTabbedPane;

import cz.vhromada.catalog.facade.BookCategoryFacade;
import cz.vhromada.catalog.facade.BookFacade;
import cz.vhromada.catalog.facade.to.BookCategoryTO;
import cz.vhromada.catalog.gui.book.BooksPanel;
import cz.vhromada.catalog.gui.commons.AbstractInfoDialog;
import cz.vhromada.catalog.gui.commons.AbstractOverviewDataPanel;
import cz.vhromada.validators.Validators;

/**
 * A class represents panel with book categories' data.
 *
 * @author Vladimir Hromada
 */
public class BookCategoriesPanel extends AbstractOverviewDataPanel<BookCategoryTO> {

    /**
     * SerialVersionUID
     */
    private static final long serialVersionUID = 1L;

    /**
     * Facade for book categories
     */
    private BookCategoryFacade bookCategoryFacade;

    /**
     * Facade for books
     */
    private BookFacade bookFacade;

    /**
     * Creates a new instance of BookCategoriesPanel.
     *
     * @param bookCategoryFacade facade for book categories
     * @param bookFacade         facade for books
     * @throws IllegalArgumentException if facade for book categories is null
     *                                  or facade for books is null
     */
    public BookCategoriesPanel(final BookCategoryFacade bookCategoryFacade, final BookFacade bookFacade) {
        super(getBookCategoriesListDataModel(bookCategoryFacade), getBookCategoriesStatsTableDataModel(bookCategoryFacade));

        Validators.validateArgumentNotNull(bookFacade, "Facade for books");

        this.bookCategoryFacade = bookCategoryFacade;
        this.bookFacade = bookFacade;
    }

    @Override
    protected AbstractInfoDialog<BookCategoryTO> getInfoDialog(final boolean add, final BookCategoryTO data) {
        return add ? new BookCategoryInfoDialog() : new BookCategoryInfoDialog(data);
    }

    @Override
    protected void deleteData() {
        bookCategoryFacade.newData();
    }

    @Override
    protected void addData(final BookCategoryTO data) {
        bookCategoryFacade.add(data);
    }

    @Override
    protected void updateData(final BookCategoryTO data) {
        bookCategoryFacade.update(data);
    }

    @Override
    protected void removeData(final BookCategoryTO data) {
        bookCategoryFacade.remove(data);
    }

    @Override
    protected void duplicatesData(final BookCategoryTO data) {
        bookCategoryFacade.duplicate(data);
    }

    @Override
    protected void moveUpData(final BookCategoryTO data) {
        bookCategoryFacade.moveUp(data);
    }

    @Override
    protected void moveDownData(final BookCategoryTO data) {
        bookCategoryFacade.moveDown(data);
    }

    @Override
    protected JPanel getDataPanel(final BookCategoryTO data) {
        return new BookCategoryDataPanel(data, bookFacade);
    }

    @Override
    protected void updateDataOnChange(final JTabbedPane dataPanel, final BookCategoryTO data) {
        final BooksPanel booksPanel = new BooksPanel(bookFacade, data);
        booksPanel.addPropertyChangeListener("update", new PropertyChangeListener() {

            @Override
            public void propertyChange(final PropertyChangeEvent evt) {
                if (Boolean.TRUE.equals(evt.getNewValue())) {
                    updateModel(data);
                }
            }

        });
        dataPanel.add("Books", booksPanel);
    }

    /**
     * Returns data model for list with book categories.
     *
     * @param facade facade for book categories
     * @return data model for list with book categories
     * @throws IllegalArgumentException if facade for book categories is null
     */
    private static BookCategoriesListDataModel getBookCategoriesListDataModel(final BookCategoryFacade facade) {
        return new BookCategoriesListDataModel(facade);
    }

    /**
     * Returns data model for table with stats for book categories.
     *
     * @param facade facade for book categories
     * @return data model for table with stats for book categories
     * @throws IllegalArgumentException if facade for book categories is null
     */
    private static BookCategoriesStatsTableDataModel getBookCategoriesStatsTableDataModel(final BookCategoryFacade facade) {
        return new BookCategoriesStatsTableDataModel(facade);
    }

}
