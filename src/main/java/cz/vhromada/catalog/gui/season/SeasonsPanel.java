package cz.vhromada.catalog.gui.season;

import javax.swing.JPanel;
import javax.swing.JTabbedPane;

import cz.vhromada.catalog.facade.EpisodeFacade;
import cz.vhromada.catalog.facade.SeasonFacade;
import cz.vhromada.catalog.facade.to.SeasonTO;
import cz.vhromada.catalog.facade.to.ShowTO;
import cz.vhromada.catalog.gui.commons.AbstractInfoDialog;
import cz.vhromada.catalog.gui.commons.AbstractOverviewDataPanel;
import cz.vhromada.catalog.gui.episode.EpisodesPanel;
import cz.vhromada.validators.Validators;

/**
 * A class represents panel with seasons' data.
 *
 * @author Vladimir Hromada
 */
public class SeasonsPanel extends AbstractOverviewDataPanel<SeasonTO> {

    /**
     * SerialVersionUID
     */
    private static final long serialVersionUID = 1L;

    /**
     * Facade for seasons
     */
    private SeasonFacade seasonFacade;

    /**
     * Facade for episodes
     */
    private EpisodeFacade episodeFacade;

    /**
     * TO for show
     */
    private ShowTO show;

    /**
     * Creates a new instance of SeasonsPanel.
     *
     * @param seasonFacade  facade for seasons
     * @param episodeFacade facade for episodes
     * @param show          TO for show
     * @throws IllegalArgumentException if facade for seasons is null
     *                                  or facade for episodes is null
     *                                  or TO for show is null
     */
    public SeasonsPanel(final SeasonFacade seasonFacade, final EpisodeFacade episodeFacade, final ShowTO show) {
        super(getSeasonsListDataModel(seasonFacade, show));

        Validators.validateArgumentNotNull(episodeFacade, "Facade for episodes");

        this.seasonFacade = seasonFacade;
        this.episodeFacade = episodeFacade;
        this.show = show;
    }

    /**
     * Sets a new value to TO for show.
     *
     * @param show new value
     * @throws IllegalArgumentException if TO for show is null
     */
    public void setShow(final ShowTO show) {
        Validators.validateArgumentNotNull(show, "TO for show");

        this.show = show;
    }

    @Override
    public void newData() {
        throw new UnsupportedOperationException("Creating new data is not allowed for seasons.");
    }

    @Override
    public void clearSelection() {
        throw new UnsupportedOperationException("Clearing selection is not allowed for seasons.");
    }

    @Override
    public void save() {
        throw new UnsupportedOperationException("Saving data is not allowed for seasons.");
    }

    @Override
    public boolean isSaved() {
        throw new UnsupportedOperationException("Testing if data are saved is not allowed for seasons.");
    }

    @Override
    protected AbstractInfoDialog<SeasonTO> getInfoDialog(final boolean add, final SeasonTO data) {
        return add ? new SeasonInfoDialog() : new SeasonInfoDialog(data);
    }

    @Override
    protected void addData(final SeasonTO data) {
        seasonFacade.add(show, data);
    }

    @Override
    protected void deleteData() {
        throw new UnsupportedOperationException("Deleting data is not allowed for seasons.");
    }

    @Override
    protected void updateData(final SeasonTO data) {
        seasonFacade.update(data);
    }

    @Override
    protected void removeData(final SeasonTO data) {
        seasonFacade.remove(data);
    }

    @Override
    protected void duplicatesData(final SeasonTO data) {
        seasonFacade.duplicate(data);
    }

    @Override
    protected void moveUpData(final SeasonTO data) {
        seasonFacade.moveUp(data);
    }

    @Override
    protected void moveDownData(final SeasonTO data) {
        seasonFacade.moveDown(data);
    }

    @Override
    protected JPanel getDataPanel(final SeasonTO data) {
        return new SeasonDataPanel(data, episodeFacade);
    }

    @Override
    protected void updateDataOnChange(final JTabbedPane dataPanel, final SeasonTO data) {
        final EpisodesPanel episodesPanel = new EpisodesPanel(episodeFacade, data);
        episodesPanel.addPropertyChangeListener("update", evt -> {
            if (Boolean.TRUE.equals(evt.getNewValue())) {
                updateModel(data);
                episodesPanel.setSeason(data);
                firePropertyChange("update", false, true);
            }
        });
        dataPanel.add("Episodes", episodesPanel);
    }

    /**
     * Returns data model for list with seasons.
     *
     * @param facade     facade for seasons
     * @param showObject TO for show
     * @return data model for list with seasons
     * @throws IllegalArgumentException if facade for seasons is null
     *                                  or TO for show is null
     */
    private static SeasonsListDataModel getSeasonsListDataModel(final SeasonFacade facade, final ShowTO showObject) {
        return new SeasonsListDataModel(facade, showObject);
    }

}
