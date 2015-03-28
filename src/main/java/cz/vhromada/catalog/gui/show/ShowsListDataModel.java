package cz.vhromada.catalog.gui.show;

import java.util.List;

import cz.vhromada.catalog.facade.ShowFacade;
import cz.vhromada.catalog.facade.to.ShowTO;
import cz.vhromada.catalog.gui.commons.AbstractListDataModel;
import cz.vhromada.validators.Validators;

/**
 * A class represents data model for list with shows.
 *
 * @author Vladimir Hromada
 */
public class ShowsListDataModel extends AbstractListDataModel<ShowTO> {

    /**
     * SerialVersionUID
     */
    private static final long serialVersionUID = 1L;

    /**
     * Facade for shows
     */
    private ShowFacade showFacade;

    /**
     * Creates a new instance of ShowsListDataModel.
     *
     * @param showFacade facade for shows
     * @throws IllegalArgumentException if service is null
     */
    public ShowsListDataModel(final ShowFacade showFacade) {
        Validators.validateArgumentNotNull(showFacade, "Facade for shows");

        this.showFacade = showFacade;
        update();
    }

    @Override
    protected List<ShowTO> getData() {
        return showFacade.getShows();
    }

    @Override
    protected String getDisplayValue(final ShowTO dataObject) {
        return dataObject.getCzechName();
    }

}
