package cz.vhromada.catalog.gui.movie;

import java.util.List;

import cz.vhromada.catalog.entity.Movie;
import cz.vhromada.catalog.facade.MovieFacade;
import cz.vhromada.catalog.gui.commons.AbstractListDataModel;
import cz.vhromada.result.Result;

import org.springframework.util.Assert;

/**
 * A class represents data model for list with movies.
 *
 * @author Vladimir Hromada
 */
public class MoviesListDataModel extends AbstractListDataModel<Movie> {

    /**
     * SerialVersionUID
     */
    private static final long serialVersionUID = 1L;

    /**
     * Facade for movies
     */
    private MovieFacade movieFacade;

    /**
     * Creates a new instance of MoviesListDataModel.
     *
     * @param movieFacade facade for movies
     * @throws IllegalArgumentException if facade for movies is null
     */
    public MoviesListDataModel(final MovieFacade movieFacade) {
        Assert.notNull(movieFacade, "Facade for movies mustn't be null.");

        this.movieFacade = movieFacade;
        update();
    }

    @Override
    protected Result<List<Movie>> getData() {
        return movieFacade.getAll();
    }

    @Override
    protected String getDisplayValue(final Movie dataObject) {
        return dataObject.getCzechName();
    }

}
