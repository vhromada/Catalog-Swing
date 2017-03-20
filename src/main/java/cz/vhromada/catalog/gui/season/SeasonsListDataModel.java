package cz.vhromada.catalog.gui.season;

import java.util.List;

import cz.vhromada.catalog.entity.Season;
import cz.vhromada.catalog.entity.Show;
import cz.vhromada.catalog.facade.SeasonFacade;
import cz.vhromada.catalog.gui.commons.AbstractListDataModel;
import cz.vhromada.result.Result;

import org.springframework.util.Assert;

/**
 * A class represents data model for list with seasons.
 *
 * @author Vladimir Hromada
 */
public class SeasonsListDataModel extends AbstractListDataModel<Season> {

    /**
     * SerialVersionUID
     */
    private static final long serialVersionUID = 1L;

    /**
     * Facade for seasons
     */
    private final SeasonFacade seasonFacade;

    /**
     * Show
     */
    private final Show show;

    /**
     * Creates a new instance of SeasonsListDataModel.
     *
     * @param seasonFacade facade for seasons
     * @param show          show
     * @throws IllegalArgumentException if facade for seasons is null
     *                                  or show is null
     */
    public SeasonsListDataModel(final SeasonFacade seasonFacade, final Show show) {
        Assert.notNull(seasonFacade, "Facade for seasons mustn't be null.");
        Assert.notNull(show, "Show mustn't be null.");

        this.seasonFacade = seasonFacade;
        this.show = show;
        update();
    }

    @Override
    protected Result<List<Season>> getData() {
        return seasonFacade.find(show);
    }

    @Override
    protected String getDisplayValue(final Season dataObject) {
        return Integer.toString(dataObject.getNumber());
    }

}
