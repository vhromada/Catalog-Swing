package cz.vhromada.catalog.gui.show;

import javax.swing.JPanel;
import javax.swing.JTabbedPane;

import cz.vhromada.catalog.entity.Show;
import cz.vhromada.catalog.facade.EpisodeFacade;
import cz.vhromada.catalog.facade.GenreFacade;
import cz.vhromada.catalog.facade.SeasonFacade;
import cz.vhromada.catalog.facade.ShowFacade;
import cz.vhromada.catalog.gui.common.AbstractInfoDialog;
import cz.vhromada.catalog.gui.common.AbstractOverviewDataPanel;
import cz.vhromada.catalog.gui.season.SeasonsPanel;

import org.springframework.util.Assert;

/**
 * A class represents panel with shows' data.
 *
 * @author Vladimir Hromada
 */
public class ShowsPanel extends AbstractOverviewDataPanel<Show> {

    /**
     * SerialVersionUID
     */
    private static final long serialVersionUID = 1L;

    /**
     * Facade for shows
     */
    private final ShowFacade showFacade;

    /**
     * Facade for seasons
     */
    private final SeasonFacade seasonFacade;

    /**
     * Facade for episodes
     */
    private final EpisodeFacade episodeFacade;

    /**
     * Facade for genres
     */
    private final GenreFacade genreFacade;

    /**
     * Creates a new instance of ShowsPanel.
     *
     * @param showFacade    facade for shows
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

        Assert.notNull(seasonFacade, "Facade for seasons mustn't be null.");
        Assert.notNull(episodeFacade, "Facade for episodes mustn't be null.");
        Assert.notNull(genreFacade, "Facade for genres mustn't be null.");

        this.showFacade = showFacade;
        this.seasonFacade = seasonFacade;
        this.episodeFacade = episodeFacade;
        this.genreFacade = genreFacade;
    }

    @Override
    protected AbstractInfoDialog<Show> getInfoDialog(final boolean add, final Show data) {
        return add ? new ShowInfoDialog(genreFacade) : new ShowInfoDialog(genreFacade, data);
    }

    @Override
    protected void deleteData() {
        showFacade.newData();
    }

    @Override
    protected void addData(final Show data) {
        showFacade.add(data);
    }

    @Override
    protected void updateData(final Show data) {
        showFacade.update(data);
    }

    @Override
    protected void removeData(final Show data) {
        showFacade.remove(data);
    }

    @Override
    protected void duplicatesData(final Show data) {
        showFacade.duplicate(data);
    }

    @Override
    protected void moveUpData(final Show data) {
        showFacade.moveUp(data);
    }

    @Override
    protected void moveDownData(final Show data) {
        showFacade.moveDown(data);
    }

    @Override
    protected JPanel getDataPanel(final Show data) {
        return new ShowDataPanel(data, seasonFacade, episodeFacade);
    }

    @Override
    protected void updateDataOnChange(final JTabbedPane dataPanel, final Show data) {
        final SeasonsPanel seasonsPanel = new SeasonsPanel(seasonFacade, episodeFacade, data);
        seasonsPanel.addPropertyChangeListener("update", evt -> {
            if (Boolean.TRUE.equals(evt.getNewValue())) {
                seasonsPanel.setShow(data);
                updateModel(data);
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
