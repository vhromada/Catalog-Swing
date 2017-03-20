package cz.vhromada.catalog.gui.episode;

import javax.swing.JPanel;
import javax.swing.JTabbedPane;

import cz.vhromada.catalog.entity.Episode;
import cz.vhromada.catalog.entity.Season;
import cz.vhromada.catalog.facade.EpisodeFacade;
import cz.vhromada.catalog.gui.commons.AbstractInfoDialog;
import cz.vhromada.catalog.gui.commons.AbstractOverviewDataPanel;

import org.springframework.util.Assert;

/**
 * A class represents panel with episodes' data.
 *
 * @author Vladimir Hromada
 */
public class EpisodesPanel extends AbstractOverviewDataPanel<Episode> {

    /**
     * SerialVersionUID
     */
    private static final long serialVersionUID = 1L;

    /**
     * Facade for episodes
     */
    private final EpisodeFacade episodeFacade;

    /**
     * Season
     */
    private Season season;

    /**
     * Creates a new instance of EpisodesPanel.
     *
     * @param episodeFacade facade for episodes
     * @param season        season
     * @throws IllegalArgumentException if facade for episodes is null
     *                                  or season is null
     */
    public EpisodesPanel(final EpisodeFacade episodeFacade, final Season season) {
        super(getEpisodesListDataModel(episodeFacade, season));

        this.episodeFacade = episodeFacade;
        this.season = season;
    }

    /**
     * Sets a new value to season.
     *
     * @param season new value
     * @throws IllegalArgumentException if season is null
     */
    public void setSeason(final Season season) {
        Assert.notNull(season, "Season mustn't be null.");

        this.season = season;
    }

    @Override
    public void newData() {
        throw new UnsupportedOperationException("Creating new data is not allowed for episodes.");
    }

    @Override
    public void clearSelection() {
        throw new UnsupportedOperationException("Clearing selection is not allowed for episodes.");
    }

    @Override
    public void save() {
        throw new UnsupportedOperationException("Saving data is not allowed for episodes.");
    }

    @Override
    public boolean isSaved() {
        throw new UnsupportedOperationException("Testing if data are saved is not allowed for episodes.");
    }

    @Override
    protected AbstractInfoDialog<Episode> getInfoDialog(final boolean add, final Episode data) {
        return add ? new EpisodeInfoDialog() : new EpisodeInfoDialog(data);
    }

    @Override
    protected void addData(final Episode data) {
        episodeFacade.add(season, data);
    }

    @Override
    protected void deleteData() {
        throw new UnsupportedOperationException("Deleting data is not allowed for episodes.");
    }

    @Override
    protected void updateData(final Episode data) {
        episodeFacade.update(data);
    }

    @Override
    protected void removeData(final Episode data) {
        episodeFacade.remove(data);
    }

    @Override
    protected void duplicatesData(final Episode data) {
        episodeFacade.duplicate(data);
    }

    @Override
    protected void moveUpData(final Episode data) {
        episodeFacade.moveUp(data);
    }

    @Override
    protected void moveDownData(final Episode data) {
        episodeFacade.moveDown(data);
    }

    @Override
    protected JPanel getDataPanel(final Episode data) {
        return new EpisodeDataPanel(data);
    }

    @Override
    protected void updateDataOnChange(final JTabbedPane dataPanel, final Episode data) {
    }

    /**
     * Returns data model for list with episodes.
     *
     * @param facade       facade for episodes
     * @param seasonObject season
     * @return data model for list with episodes
     * @throws IllegalArgumentException if facade for episodes is null
     *                                  or season is null
     */
    private static EpisodesListDataModel getEpisodesListDataModel(final EpisodeFacade facade, final Season seasonObject) {
        return new EpisodesListDataModel(facade, seasonObject);
    }

}
