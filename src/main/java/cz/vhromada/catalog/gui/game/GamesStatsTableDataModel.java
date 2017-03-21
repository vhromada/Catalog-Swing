package cz.vhromada.catalog.gui.game;

import java.util.List;

import cz.vhromada.catalog.entity.Game;
import cz.vhromada.catalog.facade.GameFacade;
import cz.vhromada.catalog.gui.common.AbstractStatsTableDataModel;
import cz.vhromada.result.Result;
import cz.vhromada.result.Status;

import org.springframework.util.Assert;

/**
 * A class represents data model for table with stats for games.
 *
 * @author Vladimir Hromada
 */
@SuppressWarnings("Duplicates")
public class GamesStatsTableDataModel extends AbstractStatsTableDataModel {

    /**
     * SerialVersionUID
     */
    private static final long serialVersionUID = 1L;

    /**
     * Facade for games
     */
    private final GameFacade gameFacade;

    /**
     * List of games
     */
    private List<Game> games;

    /**
     * Total count of media
     */
    private int totalMediaCount;

    /**
     * Creates a new instance of GamesStatsTableDataModel.
     *
     * @param gameFacade facade for games
     * @throws IllegalArgumentException if facade for games is null
     */
    public GamesStatsTableDataModel(final GameFacade gameFacade) {
        Assert.notNull(gameFacade, "Facade for games mustn't be null.");

        this.gameFacade = gameFacade;
        update();
    }

    @Override
    public Object getValueAt(final int rowIndex, final int columnIndex) {
        switch (columnIndex) {
            case 0:
                return games.size();
            case 1:
                return totalMediaCount;
            default:
                throw new IndexOutOfBoundsException("Bad column");
        }
    }

    @Override
    public int getColumnCount() {
        return 2;
    }

    @Override
    public int getRowCount() {
        return 1;
    }

    @Override
    public Class<?> getColumnClass(final int columnIndex) {
        return Integer.class;
    }

    @Override
    public String getColumnName(final int column) {
        switch (column) {
            case 0:
                return "Count of games";
            case 1:
                return "Count of media";
            default:
                throw new IndexOutOfBoundsException("Bad column");
        }
    }

    @Override
    public final void update() {
        final Result<List<Game>> gamesResult = gameFacade.getAll();
        final Result<Integer> totalMediaCountResult = gameFacade.getTotalMediaCount();

        final Result<Void> result = new Result<>();
        result.addEvents(gamesResult.getEvents());
        result.addEvents(totalMediaCountResult.getEvents());

        if (Status.OK == result.getStatus()) {
            games = gamesResult.getData();
            totalMediaCount = totalMediaCountResult.getData();
        } else {
            throw new IllegalArgumentException("Can't get data. " + result);
        }
    }

}
