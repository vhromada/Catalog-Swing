package cz.vhromada.catalog.gui.game;

import javax.swing.JPanel;
import javax.swing.JTabbedPane;

import cz.vhromada.catalog.entity.Game;
import cz.vhromada.catalog.facade.GameFacade;
import cz.vhromada.catalog.gui.common.AbstractInfoDialog;
import cz.vhromada.catalog.gui.common.AbstractOverviewDataPanel;

/**
 * A class represents panel with games' data.
 *
 * @author Vladimir Hromada
 */
public class GamesPanel extends AbstractOverviewDataPanel<Game> {

    /**
     * SerialVersionUID
     */
    private static final long serialVersionUID = 1L;

    /**
     * Facade for games
     */
    private final GameFacade gameFacade;

    /**
     * Creates a new instance of GamesPanel.
     *
     * @param gameFacade facade for games
     * @throws IllegalArgumentException if facade for games is null
     */
    public GamesPanel(final GameFacade gameFacade) {
        super(getGamesListDataModel(gameFacade), getGamesStatsTableDataModel(gameFacade));

        this.gameFacade = gameFacade;
    }

    @Override
    protected AbstractInfoDialog<Game> getInfoDialog(final boolean add, final Game data) {
        return add ? new GameInfoDialog() : new GameInfoDialog(data);
    }

    @Override
    protected void deleteData() {
        gameFacade.newData();
    }

    @Override
    protected void addData(final Game data) {
        gameFacade.add(data);
    }

    @Override
    protected void updateData(final Game data) {
        gameFacade.update(data);
    }

    @Override
    protected void removeData(final Game data) {
        gameFacade.remove(data);
    }

    @Override
    protected void duplicatesData(final Game data) {
        gameFacade.duplicate(data);
    }

    @Override
    protected void moveUpData(final Game data) {
        gameFacade.moveUp(data);
    }

    @Override
    protected void moveDownData(final Game data) {
        gameFacade.moveDown(data);
    }

    @Override
    protected JPanel getDataPanel(final Game data) {
        return new GameDataPanel(data);
    }

    @Override
    protected void updateDataOnChange(final JTabbedPane dataPanel, final Game data) {
    }

    /**
     * Returns data model for list with games.
     *
     * @param facade facade for games
     * @return data model for list with games
     * @throws IllegalArgumentException if facade for games is null
     */
    private static GamesListDataModel getGamesListDataModel(final GameFacade facade) {
        return new GamesListDataModel(facade);
    }

    /**
     * Returns data model for table with stats for games.
     *
     * @param facade facade for games
     * @return data model for table with stats for games
     * @throws IllegalArgumentException if facade for games is null
     */
    private static GamesStatsTableDataModel getGamesStatsTableDataModel(final GameFacade facade) {
        return new GamesStatsTableDataModel(facade);
    }

}
