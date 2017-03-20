package cz.vhromada.catalog.gui.game;

import java.util.List;

import cz.vhromada.catalog.entity.Game;
import cz.vhromada.catalog.facade.GameFacade;
import cz.vhromada.catalog.gui.commons.AbstractListDataModel;
import cz.vhromada.result.Result;

import org.springframework.util.Assert;

/**
 * A class represents data model for list with games.
 *
 * @author Vladimir Hromada
 */
public class GamesListDataModel extends AbstractListDataModel<Game> {

    /**
     * SerialVersionUID
     */
    private static final long serialVersionUID = 1L;

    /**
     * Facade for games
     */
    private GameFacade gameFacade;

    /**
     * Creates a new instance of GamesListDataModel.
     *
     * @param gameFacade facade for games
     * @throws IllegalArgumentException if facade for games is null
     */
    public GamesListDataModel(final GameFacade gameFacade) {
        Assert.notNull(gameFacade, "Facade for games mustn't be null");

        this.gameFacade = gameFacade;
        update();
    }

    @Override
    protected Result<List<Game>> getData() {
        return gameFacade.getAll();
    }

    @Override
    protected String getDisplayValue(final Game dataObject) {
        return dataObject.getName();
    }

}
