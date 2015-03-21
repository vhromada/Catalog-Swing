package cz.vhromada.catalog.gui.category;

import java.util.List;

import cz.vhromada.catalog.facade.BookCategoryFacade;
import cz.vhromada.catalog.facade.to.BookCategoryTO;
import cz.vhromada.catalog.gui.commons.AbstractStatsTableDataModel;
import cz.vhromada.validators.Validators;

/**
 * A class represents data model for table with stats for book categories.
 *
 * @author Vladimir Hromada
 */
public class BookCategoriesStatsTableDataModel extends AbstractStatsTableDataModel {

    /**
     * SerialVersionUID
     */
    private static final long serialVersionUID = 1L;

    /**
     * Error message for bad column
     */
    private static final String BAD_COLUMN_ERROR_MESSAGE = "Bad column";

    /**
     * Facade for book categories
     */
    private BookCategoryFacade bookCategoryFacade;

    /**
     * List of TO for book category
     */
    private List<BookCategoryTO> bookCategories;

    /**
     * Count of books from all book categories
     */
    private int booksCount;

    /**
     * Creates a new instance of BookCategoriesStatsTableDataModel.
     *
     * @param bookCategoryFacade facade for book categories
     * @throws IllegalArgumentException if service is null
     */
    public BookCategoriesStatsTableDataModel(final BookCategoryFacade bookCategoryFacade) {
        Validators.validateArgumentNotNull(bookCategoryFacade, "Facade for book categories");

        this.bookCategoryFacade = bookCategoryFacade;
        update();
    }

    @Override
    public Object getValueAt(final int rowIndex, final int columnIndex) {
        switch (columnIndex) {
            case 0:
                return bookCategories.size();
            case 1:
                return booksCount;
            default:
                throw new IndexOutOfBoundsException(BAD_COLUMN_ERROR_MESSAGE);
        }
    }

    @Override
    public int getColumnCount() {
        return 2;
    }

    @Override
    public int getRowCount() {
        return 1;
    }

    @Override
    public Class<?> getColumnClass(final int columnIndex) {
        return Integer.class;
    }

    @Override
    public String getColumnName(final int column) {
        switch (column) {
            case 0:
                return "Count of book categories";
            case 1:
                return "Count of books";
            default:
                throw new IndexOutOfBoundsException(BAD_COLUMN_ERROR_MESSAGE);
        }
    }

    @Override
    public final void update() {
        bookCategories = bookCategoryFacade.getBookCategories();
        booksCount = bookCategoryFacade.getBooksCount();
    }

}
