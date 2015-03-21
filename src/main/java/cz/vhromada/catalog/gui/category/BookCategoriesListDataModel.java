package cz.vhromada.catalog.gui.category;

import java.util.List;

import cz.vhromada.catalog.facade.BookCategoryFacade;
import cz.vhromada.catalog.facade.to.BookCategoryTO;
import cz.vhromada.catalog.gui.commons.AbstractListDataModel;
import cz.vhromada.validators.Validators;

/**
 * A class represents data model for list with book categories.
 *
 * @author Vladimir Hromada
 */
public class BookCategoriesListDataModel extends AbstractListDataModel<BookCategoryTO> {

    /**
     * SerialVersionUID
     */
    private static final long serialVersionUID = 1L;

    /**
     * Facade for book categories
     */
    private BookCategoryFacade bookCategoryFacade;

    /**
     * Creates a new instance of BookCategoriesListDataModel.
     *
     * @param bookCategoryFacade facade for book categories
     * @throws IllegalArgumentException if service is null
     */
    public BookCategoriesListDataModel(final BookCategoryFacade bookCategoryFacade) {
        Validators.validateArgumentNotNull(bookCategoryFacade, "Facade for book categories");

        this.bookCategoryFacade = bookCategoryFacade;
        update();
    }

    @Override
    protected List<BookCategoryTO> getData() {
        return bookCategoryFacade.getBookCategories();
    }

    @Override
    protected String getDisplayValue(final BookCategoryTO dataObject) {
        return dataObject.getName();
    }

}
