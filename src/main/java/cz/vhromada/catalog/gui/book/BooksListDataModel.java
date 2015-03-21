package cz.vhromada.catalog.gui.book;

import java.util.List;

import cz.vhromada.catalog.facade.BookFacade;
import cz.vhromada.catalog.facade.to.BookCategoryTO;
import cz.vhromada.catalog.facade.to.BookTO;
import cz.vhromada.catalog.gui.commons.AbstractListDataModel;
import cz.vhromada.validators.Validators;

/**
 * A class represents data model for list with books.
 *
 * @author Vladimir Hromada
 */
public class BooksListDataModel extends AbstractListDataModel<BookTO> {

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
     * Creates a new instance of BooksListDataModel.
     *
     * @param bookFacade   facade for books
     * @param bookCategory TO for book category
     * @throws IllegalArgumentException if facade for books is null
     *                                  or TO for book category is null
     */
    public BooksListDataModel(final BookFacade bookFacade, final BookCategoryTO bookCategory) {
        Validators.validateArgumentNotNull(bookFacade, "Facade for books");
        Validators.validateArgumentNotNull(bookCategory, "TO for book category");

        this.bookFacade = bookFacade;
        this.bookCategory = bookCategory;
        update();
    }

    @Override
    protected List<BookTO> getData() {
        return bookFacade.findBooksByBookCategory(bookCategory);
    }

    @Override
    protected String getDisplayValue(final BookTO dataObject) {
        return dataObject.getTitle();
    }

}
