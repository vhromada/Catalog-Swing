package cz.vhromada.catalog.gui.movie;

import javax.swing.JPanel;
import javax.swing.JTabbedPane;

import cz.vhromada.catalog.entity.Movie;
import cz.vhromada.catalog.facade.GenreFacade;
import cz.vhromada.catalog.facade.MovieFacade;
import cz.vhromada.catalog.facade.PictureFacade;
import cz.vhromada.catalog.gui.common.AbstractInfoDialog;
import cz.vhromada.catalog.gui.common.AbstractOverviewDataPanel;

import org.springframework.util.Assert;

/**
 * A class represents panel with movies' data.
 *
 * @author Vladimir Hromada
 */
public class MoviesPanel extends AbstractOverviewDataPanel<Movie> {

    /**
     * SerialVersionUID
     */
    private static final long serialVersionUID = 1L;

    /**
     * Facade for movies
     */
    private final MovieFacade movieFacade;

    /**
     * Facade for genres
     */
    private final GenreFacade genreFacade;

    /**
     * Facade for pictures
     */
    private final PictureFacade pictureFacade;

    /**
     * Creates a new instance of MoviesPanel.
     *
     * @param movieFacade   facade for movies
     * @param genreFacade   facade for genres
     * @param pictureFacade facade for pictures
     * @throws IllegalArgumentException if facade for movies is null
     *                                  or facade for genres is null
     *                                  or facade for pictures is null
     */
    public MoviesPanel(final MovieFacade movieFacade, final GenreFacade genreFacade, final PictureFacade pictureFacade) {
        super(getMoviesListDataModel(movieFacade), getMoviesStatsTableDataModel(movieFacade));

        Assert.notNull(genreFacade, "Facade for genres mustn't be null.");
        Assert.notNull(pictureFacade, "Facade for pictures mustn't be null.");

        this.movieFacade = movieFacade;
        this.genreFacade = genreFacade;
        this.pictureFacade = pictureFacade;
    }

    @Override
    protected AbstractInfoDialog<Movie> getInfoDialog(final boolean add, final Movie data) {
        return add ? new MovieInfoDialog(genreFacade, pictureFacade) : new MovieInfoDialog(genreFacade, pictureFacade, data);
    }

    @Override
    protected void deleteData() {
        movieFacade.newData();
    }

    @Override
    protected void addData(final Movie data) {
        movieFacade.add(data);
    }

    @Override
    protected void updateData(final Movie data) {
        movieFacade.update(data);
    }

    @Override
    protected void removeData(final Movie data) {
        movieFacade.remove(data);
    }

    @Override
    protected void duplicatesData(final Movie data) {
        movieFacade.duplicate(data);
    }

    @Override
    protected void moveUpData(final Movie data) {
        movieFacade.moveUp(data);
    }

    @Override
    protected void moveDownData(final Movie data) {
        movieFacade.moveDown(data);
    }

    @Override
    protected JPanel getDataPanel(final Movie data) {
        return new MovieDataPanel(data, pictureFacade);
    }

    @Override
    protected void updateDataOnChange(final JTabbedPane dataPanel, final Movie data) {
    }

    /**
     * Returns data model for list with movies.
     *
     * @param facade facade for movies
     * @return data model for list with movies
     * @throws IllegalArgumentException if facade for movies is null
     */
    private static MoviesListDataModel getMoviesListDataModel(final MovieFacade facade) {
        return new MoviesListDataModel(facade);
    }

    /**
     * Returns data model for table with stats for movies.
     *
     * @param facade facade for movies
     * @return data model for table with stats for movies
     * @throws IllegalArgumentException if facade for movies is null
     */
    private static MoviesStatsTableDataModel getMoviesStatsTableDataModel(final MovieFacade facade) {
        return new MoviesStatsTableDataModel(facade);
    }

}
