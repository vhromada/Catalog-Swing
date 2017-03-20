package cz.vhromada.catalog.gui.show;

import java.util.List;

import cz.vhromada.catalog.entity.Show;
import cz.vhromada.catalog.facade.ShowFacade;
import cz.vhromada.catalog.gui.common.AbstractListDataModel;
import cz.vhromada.result.Result;

import org.springframework.util.Assert;

/**
 * A class represents data model for list with shows.
 *
 * @author Vladimir Hromada
 */
public class ShowsListDataModel extends AbstractListDataModel<Show> {

    /**
     * SerialVersionUID
     */
    private static final long serialVersionUID = 1L;

    /**
     * Facade for shows
     */
    private final ShowFacade showFacade;

    /**
     * Creates a new instance of ShowsListDataModel.
     *
     * @param showFacade facade for shows
     * @throws IllegalArgumentException if service is null
     */
    public ShowsListDataModel(final ShowFacade showFacade) {
        Assert.notNull(showFacade, "Facade for shows mustn't be null.");

        this.showFacade = showFacade;
        update();
    }

    @Override
    protected Result<List<Show>> getData() {
        return showFacade.getAll();
    }

    @Override
    protected String getDisplayValue(final Show dataObject) {
        return dataObject.getCzechName();
    }

}
