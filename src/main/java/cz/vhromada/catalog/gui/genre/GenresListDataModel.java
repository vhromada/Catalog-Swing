package cz.vhromada.catalog.gui.genre;

import java.util.List;

import cz.vhromada.catalog.entity.Genre;
import cz.vhromada.catalog.facade.GenreFacade;
import cz.vhromada.catalog.gui.commons.AbstractListDataModel;
import cz.vhromada.result.Result;

import org.springframework.util.Assert;

/**
 * A class represents data model for list with genres.
 *
 * @author Vladimir Hromada
 */
public class GenresListDataModel extends AbstractListDataModel<Genre> {

    /**
     * SerialVersionUID
     */
    private static final long serialVersionUID = 1L;

    /**
     * Facade for genres
     */
    private final GenreFacade genreFacade;

    /**
     * Creates a new instance of GenresListDataModel.
     *
     * @param genreFacade facade for genres
     * @throws IllegalArgumentException if facade for genres is null
     */
    public GenresListDataModel(final GenreFacade genreFacade) {
        Assert.notNull(genreFacade, "Facade for genres mustn't be null.");

        this.genreFacade = genreFacade;
        update();
    }

    @Override
    protected Result<List<Genre>> getData() {
        return genreFacade.getAll();
    }

    @Override
    protected String getDisplayValue(final Genre dataObject) {
        return dataObject.getName();
    }

}
