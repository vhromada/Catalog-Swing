package cz.vhromada.catalog.gui.season;

import java.util.List;

import cz.vhromada.catalog.facade.SeasonFacade;
import cz.vhromada.catalog.facade.to.SeasonTO;
import cz.vhromada.catalog.facade.to.ShowTO;
import cz.vhromada.catalog.gui.commons.AbstractListDataModel;
import cz.vhromada.validators.Validators;

/**
 * A class represents data model for list with seasons.
 *
 * @author Vladimir Hromada
 */
public class SeasonsListDataModel extends AbstractListDataModel<SeasonTO> {

    /**
     * SerialVersionUID
     */
    private static final long serialVersionUID = 1L;

    /**
     * Facade for seasons
     */
    private SeasonFacade seasonFacade;

    /**
     * TO for show
     */
    private ShowTO show;

    /**
     * Creates a new instance of SeasonsListDataModel.
     *
     * @param seasonFacade facade for seasons
     * @param show         TO for show
     * @throws IllegalArgumentException if facade for seasons is null
     *                                  or TO for show is null
     */
    public SeasonsListDataModel(final SeasonFacade seasonFacade, final ShowTO show) {
        Validators.validateArgumentNotNull(seasonFacade, "Facade for seasons");
        Validators.validateArgumentNotNull(show, "TO for show");

        this.seasonFacade = seasonFacade;
        this.show = show;
        update();
    }

    @Override
    protected List<SeasonTO> getData() {
        return seasonFacade.findSeasonsByShow(show);
    }

    @Override
    protected String getDisplayValue(final SeasonTO dataObject) {
        return Integer.toString(dataObject.getNumber());
    }

}
