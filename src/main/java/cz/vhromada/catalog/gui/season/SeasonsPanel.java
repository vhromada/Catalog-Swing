package cz.vhromada.catalog.gui.season;

import javax.swing.JPanel;
import javax.swing.JTabbedPane;

import cz.vhromada.catalog.entity.Season;
import cz.vhromada.catalog.entity.Show;
import cz.vhromada.catalog.facade.EpisodeFacade;
import cz.vhromada.catalog.facade.SeasonFacade;
import cz.vhromada.catalog.gui.common.AbstractInfoDialog;
import cz.vhromada.catalog.gui.common.AbstractOverviewDataPanel;
import cz.vhromada.catalog.gui.episode.EpisodesPanel;

import org.springframework.util.Assert;

/**
 * A class represents panel with seasons' data.
 *
 * @author Vladimir Hromada
 */
public class SeasonsPanel extends AbstractOverviewDataPanel<Season> {

    /**
     * SerialVersionUID
     */
    private static final long serialVersionUID = 1L;

    /**
     * Facade for seasons
     */
    private final SeasonFacade seasonFacade;

    /**
     * Facade for episodes
     */
    private final EpisodeFacade episodeFacade;

    /**
     * Show
     */
    private Show show;

    /**
     * Creates a new instance of SeasonsPanel.
     *
     * @param seasonFacade  facade for seasons
     * @param episodeFacade facade for episodes
     * @param show           show
     * @throws IllegalArgumentException if facade for seasons is null
     *                                  or facade for episodes is null
     *                                  or show is null
     */
    public SeasonsPanel(final SeasonFacade seasonFacade, final EpisodeFacade episodeFacade, final Show show) {
        super(getSeasonsListDataModel(seasonFacade, show));

        Assert.notNull(episodeFacade, "Facade for episodes mustn't be null.");

        this.seasonFacade = seasonFacade;
        this.episodeFacade = episodeFacade;
        this.show = show;
    }

    /**
     * Sets a new value to show.
     *
     * @param show new value
     * @throws IllegalArgumentException if show is null
     */
    public void setShow(final Show show) {
        Assert.notNull(show, " show");

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
    protected AbstractInfoDialog<Season> getInfoDialog(final boolean add, final Season data) {
        return add ? new SeasonInfoDialog() : new SeasonInfoDialog(data);
    }

    @Override
    protected void addData(final Season data) {
        seasonFacade.add(show, data);
    }

    @Override
    protected void deleteData() {
        throw new UnsupportedOperationException("Deleting data is not allowed for seasons.");
    }

    @Override
    protected void updateData(final Season data) {
        seasonFacade.update(data);
    }

    @Override
    protected void removeData(final Season data) {
        seasonFacade.remove(data);
    }

    @Override
    protected void duplicatesData(final Season data) {
        seasonFacade.duplicate(data);
    }

    @Override
    protected void moveUpData(final Season data) {
        seasonFacade.moveUp(data);
    }

    @Override
    protected void moveDownData(final Season data) {
        seasonFacade.moveDown(data);
    }

    @Override
    protected JPanel getDataPanel(final Season data) {
        return new SeasonDataPanel(data, episodeFacade);
    }

    @Override
    protected void updateDataOnChange(final JTabbedPane dataPanel, final Season data) {
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
     * @param showObject  show
     * @return data model for list with seasons
     * @throws IllegalArgumentException if facade for seasons is null
     *                                  or show is null
     */
    private static SeasonsListDataModel getSeasonsListDataModel(final SeasonFacade facade, final Show showObject) {
        return new SeasonsListDataModel(facade, showObject);
    }

}
