package cz.vhromada.catalog.gui.episode;

import java.util.List;

import cz.vhromada.catalog.entity.Episode;
import cz.vhromada.catalog.entity.Season;
import cz.vhromada.catalog.facade.EpisodeFacade;
import cz.vhromada.catalog.gui.commons.AbstractListDataModel;
import cz.vhromada.result.Result;

import org.springframework.util.Assert;

/**
 * A class represents data model for list with episodes.
 *
 * @author Vladimir Hromada
 */
public class EpisodesListDataModel extends AbstractListDataModel<Episode> {

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
    private final Season season;

    /**
     * Creates a new instance of EpisodesListDataModel.
     *
     * @param episodeFacade facade for episodes
     * @param season        season
     * @throws IllegalArgumentException if facade for episodes is null
     *                                  or season is null
     */
    public EpisodesListDataModel(final EpisodeFacade episodeFacade, final Season season) {
        Assert.notNull(episodeFacade, "Facade for episode mustn't be null.");
        Assert.notNull(season, "Season mustn't be null.");

        this.episodeFacade = episodeFacade;
        this.season = season;
        update();
    }

    @Override
    protected Result<List<Episode>> getData() {
        return episodeFacade.find(season);
    }

    @Override
    protected String getDisplayValue(final Episode dataObject) {
        return dataObject.getName();
    }

}
