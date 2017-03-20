package cz.vhromada.catalog.gui.genre;

import javax.swing.JPanel;
import javax.swing.JTabbedPane;

import cz.vhromada.catalog.entity.Genre;
import cz.vhromada.catalog.facade.GenreFacade;
import cz.vhromada.catalog.gui.commons.AbstractInfoDialog;
import cz.vhromada.catalog.gui.commons.AbstractOverviewDataPanel;

/**
 * A class represents panel with genres' data.
 *
 * @author Vladimir Hromada
 */
public class GenresPanel extends AbstractOverviewDataPanel<Genre> {

    /**
     * SerialVersionUID
     */
    private static final long serialVersionUID = 1L;

    /**
     * Facade for genres
     */
    private final GenreFacade genreFacade;

    /**
     * Creates a new instance of GenresPanel.
     *
     * @param genreFacade facade for genres
     * @throws IllegalArgumentException if facade for genres is null
     */
    public GenresPanel(final GenreFacade genreFacade) {
        super(getGenresListDataModel(genreFacade), getGenresStatsTableDataModel(genreFacade));

        this.genreFacade = genreFacade;
    }

    @Override
    protected AbstractInfoDialog<Genre> getInfoDialog(final boolean add, final Genre data) {
        return add ? new GenreInfoDialog() : new GenreInfoDialog(data);
    }

    @Override
    protected void deleteData() {
        genreFacade.newData();
    }

    @Override
    protected void addData(final Genre data) {
        genreFacade.add(data);
    }

    @Override
    protected void updateData(final Genre data) {
        genreFacade.update(data);
    }

    @Override
    protected void removeData(final Genre data) {
        genreFacade.remove(data);
    }

    @Override
    protected void duplicatesData(final Genre data) {
        genreFacade.duplicate(data);
    }

    @Override
    protected void moveUpData(final Genre data) {
        genreFacade.moveUp(data);
    }

    @Override
    protected void moveDownData(final Genre data) {
        genreFacade.moveDown(data);
    }

    @Override
    protected JPanel getDataPanel(final Genre data) {
        return new GenreDataPanel(data);
    }

    @Override
    protected void updateDataOnChange(final JTabbedPane dataPanel, final Genre data) {
    }

    /**
     * Returns data model for list with genres.
     *
     * @param facade facade for genres
     * @return data model for list with genres
     * @throws IllegalArgumentException if facade for genres is null
     */
    private static GenresListDataModel getGenresListDataModel(final GenreFacade facade) {
        return new GenresListDataModel(facade);
    }

    /**
     * Returns data model for table with stats for genres.
     *
     * @param facade facade for genres
     * @return data model for table with stats for genres
     * @throws IllegalArgumentException if facade for genres is null
     */
    private static GenresStatsTableDataModel getGenresStatsTableDataModel(final GenreFacade facade) {
        return new GenresStatsTableDataModel(facade);
    }

}
