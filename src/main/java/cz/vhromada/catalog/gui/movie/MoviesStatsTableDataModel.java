package cz.vhromada.catalog.gui.movie;

import java.util.List;

import cz.vhromada.catalog.entity.Movie;
import cz.vhromada.catalog.facade.MovieFacade;
import cz.vhromada.catalog.gui.common.AbstractStatsTableDataModel;
import cz.vhromada.common.Time;
import cz.vhromada.validation.result.Result;
import cz.vhromada.validation.result.Status;

import org.springframework.util.Assert;

/**
 * A class represents data model for table with stats for movies.
 *
 * @author Vladimir Hromada
 */
public class MoviesStatsTableDataModel extends AbstractStatsTableDataModel {

    /**
     * SerialVersionUID
     */
    private static final long serialVersionUID = 1L;

    /**
     * Error message for bad column
     */
    private static final String BAD_COLUMN_ERROR_MESSAGE = "Bad column";

    /**
     * Facade for movies
     */
    private final MovieFacade movieFacade;

    /**
     * List of movies
     */
    private List<Movie> movies;

    /**
     * Total length of all movies
     */
    private Time totalLength;

    /**
     * Total count of media
     */
    private int totalMediaCount;

    /**
     * Creates a new instance of MoviesStatsTableDataModel.
     *
     * @param movieFacade facade for movies
     * @throws IllegalArgumentException if facade for movies is null
     */
    public MoviesStatsTableDataModel(final MovieFacade movieFacade) {
        Assert.notNull(movieFacade, "Facade for movies mustn't be null.");

        this.movieFacade = movieFacade;
        update();
    }

    @Override
    public Object getValueAt(final int rowIndex, final int columnIndex) {
        switch (columnIndex) {
            case 0:
                return movies.size();
            case 1:
                return totalLength.toString();
            case 2:
                return totalMediaCount;
            default:
                throw new IndexOutOfBoundsException(BAD_COLUMN_ERROR_MESSAGE);
        }
    }

    @Override
    public int getColumnCount() {
        return 3;
    }

    @Override
    public int getRowCount() {
        return 1;
    }

    @Override
    public Class<?> getColumnClass(final int columnIndex) {
        switch (columnIndex) {
            case 0:
                return Integer.class;
            case 1:
                return String.class;
            case 2:
                return Integer.class;
            default:
                throw new IndexOutOfBoundsException(BAD_COLUMN_ERROR_MESSAGE);
        }
    }

    @Override
    public String getColumnName(final int column) {
        switch (column) {
            case 0:
                return "Count of movies";
            case 1:
                return "Total length";
            case 2:
                return "Count of media";
            default:
                throw new IndexOutOfBoundsException(BAD_COLUMN_ERROR_MESSAGE);
        }
    }

    @Override
    public final void update() {
        final Result<List<Movie>> showsResult = movieFacade.getAll();
        final Result<Time> totalLengthResult = movieFacade.getTotalLength();
        final Result<Integer> totalMediaCountResult = movieFacade.getTotalMediaCount();

        final Result<Void> result = new Result<>();
        result.addEvents(showsResult.getEvents());
        result.addEvents(totalLengthResult.getEvents());
        result.addEvents(totalMediaCountResult.getEvents());

        if (Status.OK == result.getStatus()) {
            movies = showsResult.getData();
            totalLength = totalLengthResult.getData();
            totalMediaCount = totalMediaCountResult.getData();
        } else {
            throw new IllegalArgumentException("Can't get data. " + result);
        }
    }

}
