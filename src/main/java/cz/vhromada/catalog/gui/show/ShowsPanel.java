package cz.vhromada.catalog.gui.show;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

import javax.swing.JPanel;
import javax.swing.JTabbedPane;

import cz.vhromada.catalog.facade.EpisodeFacade;
import cz.vhromada.catalog.facade.GenreFacade;
import cz.vhromada.catalog.facade.SeasonFacade;
import cz.vhromada.catalog.facade.ShowFacade;
import cz.vhromada.catalog.facade.to.ShowTO;
import cz.vhromada.catalog.gui.commons.AbstractInfoDialog;
import cz.vhromada.catalog.gui.commons.AbstractOverviewDataPanel;
import cz.vhromada.catalog.gui.season.SeasonsPanel;
import cz.vhromada.validators.Validators;

/**
 * A class represents panel with shows' data.
 *
 * @author Vladimir Hromada
 */
public class ShowsPanel extends AbstractOverviewDataPanel<ShowTO> {

    /**
     * SerialVersionUID
     */
    private static final long serialVersionUID = 1L;

    /**
     * Facade for shows
     */
    private ShowFacade showFacade;

    /**
     * Facade for seasons
     */
    private SeasonFacade seasonFacade;

    /**
     * Facade for episodes
     */
    private EpisodeFacade episodeFacade;

    /**
     * Facade for genres
     */
    private GenreFacade genreFacade;

    /**
     * Creates a new instance of ShowsPanel.
     *
     * @param showFacade   facade for shows
     * @param seasonFacade  facade for seasons
     * @param episodeFacade facade for episodes
     * @param genreFacade   facade for genres
     * @throws IllegalArgumentException if facade for shows is null
     *                                  or facade for seasons is null
     *                                  or facade for episodes is null
     *                                  or facade for genres is null
     */
    public ShowsPanel(final ShowFacade showFacade, final SeasonFacade seasonFacade, final EpisodeFacade episodeFacade, final GenreFacade genreFacade) {
        super(getShowsListDataModel(showFacade), getShowsStatsTableDataModel(showFacade));

        Validators.validateArgumentNotNull(seasonFacade, "Facade for seasons");
        Validators.validateArgumentNotNull(episodeFacade, "Facade for episodes");
        Validators.validateArgumentNotNull(genreFacade, "Facade for genres");

        this.showFacade = showFacade;
        this.seasonFacade = seasonFacade;
        this.episodeFacade = episodeFacade;
        this.genreFacade = genreFacade;
    }

    @Override
    protected AbstractInfoDialog<ShowTO> getInfoDialog(final boolean add, final ShowTO data) {
        return add ? new ShowInfoDialog(genreFacade) : new ShowInfoDialog(genreFacade, data);
    }

    @Override
    protected void deleteData() {
        showFacade.newData();
    }

    @Override
    protected void addData(final ShowTO data) {
        showFacade.add(data);
    }

    @Override
    protected void updateData(final ShowTO data) {
        showFacade.update(data);
    }

    @Override
    protected void removeData(final ShowTO data) {
        showFacade.remove(data);
    }

    @Override
    protected void duplicatesData(final ShowTO data) {
        showFacade.duplicate(data);
    }

    @Override
    protected void moveUpData(final ShowTO data) {
        showFacade.moveUp(data);
    }

    @Override
    protected void moveDownData(final ShowTO data) {
        showFacade.moveDown(data);
    }

    @Override
    protected JPanel getDataPanel(final ShowTO data) {
        return new ShowDataPanel(data, seasonFacade, episodeFacade);
    }

    @Override
    protected void updateDataOnChange(final JTabbedPane dataPanel, final ShowTO data) {
        final SeasonsPanel seasonsPanel = new SeasonsPanel(seasonFacade, episodeFacade, data);
        seasonsPanel.addPropertyChangeListener("update", new PropertyChangeListener() {

            @Override
            public void propertyChange(final PropertyChangeEvent evt) {
                if (Boolean.TRUE.equals(evt.getNewValue())) {
                    seasonsPanel.setShow(data);
                    updateModel(data);
                }
            }

        });
        dataPanel.add("Seasons", seasonsPanel);
    }

    /**
     * Returns data model for list with shows.
     *
     * @param facade facade for shows
     * @return data model for list with shows
     * @throws IllegalArgumentException if facade for shows is null
     */
    private static ShowsListDataModel getShowsListDataModel(final ShowFacade facade) {
        return new ShowsListDataModel(facade);
    }

    /**
     * Returns data model for table with stats for shows.
     *
     * @param facade facade for shows
     * @return data model for table with stats for shows
     * @throws IllegalArgumentException if facade for shows is null
     */
    private static ShowsStatsTableDataModel getShowsStatsTableDataModel(final ShowFacade facade) {
        return new ShowsStatsTableDataModel(facade);
    }

}
