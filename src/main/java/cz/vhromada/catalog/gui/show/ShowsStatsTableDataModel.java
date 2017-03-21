package cz.vhromada.catalog.gui.show;

import java.util.List;

import cz.vhromada.catalog.common.Time;
import cz.vhromada.catalog.entity.Show;
import cz.vhromada.catalog.facade.ShowFacade;
import cz.vhromada.catalog.gui.common.AbstractStatsTableDataModel;
import cz.vhromada.result.Result;
import cz.vhromada.result.Status;

import org.springframework.util.Assert;

/**
 * A class represents data model for table with stats for shows.
 *
 * @author Vladimir Hromada
 */
@SuppressWarnings("Duplicates")
public class ShowsStatsTableDataModel extends AbstractStatsTableDataModel {

    /**
     * SerialVersionUID
     */
    private static final long serialVersionUID = 1L;

    /**
     * Error message for bad column
     */
    private static final String BAD_COLUMN_ERROR_MESSAGE = "Bad column";

    /**
     * Facade for shows
     */
    private final ShowFacade showFacade;

    /**
     * List of shows
     */
    private List<Show> shows;

    /**
     * Count of seasons from all shows
     */
    private int seasonsCount;

    /**
     * Count of episodes from all shows
     */
    private int episodesCount;

    /**
     * Total length of all shows
     */
    private Time totalLength;

    /**
     * Creates a new instance of ShowsStatsTableDataModel.
     *
     * @param showFacade facade for shows
     * @throws IllegalArgumentException if service is null
     */
    public ShowsStatsTableDataModel(final ShowFacade showFacade) {
        Assert.notNull(showFacade, "Facade for shows mustn't be null.");

        this.showFacade = showFacade;
        update();
    }

    @Override
    public Object getValueAt(final int rowIndex, final int columnIndex) {
        switch (columnIndex) {
            case 0:
                return shows.size();
            case 1:
                return seasonsCount;
            case 2:
                return episodesCount;
            case 3:
                return totalLength.toString();
            default:
                throw new IndexOutOfBoundsException(BAD_COLUMN_ERROR_MESSAGE);
        }
    }

    @Override
    public int getColumnCount() {
        return 4;
    }

    @Override
    public int getRowCount() {
        return 1;
    }

    @Override
    public Class<?> getColumnClass(final int columnIndex) {
        switch (columnIndex) {
            case 0:
                return Integer.class;
            case 1:
                return Integer.class;
            case 2:
                return Integer.class;
            case 3:
                return String.class;
            default:
                throw new IndexOutOfBoundsException(BAD_COLUMN_ERROR_MESSAGE);
        }
    }

    @Override
    public String getColumnName(final int column) {
        switch (column) {
            case 0:
                return "Count of shows";
            case 1:
                return "Count of seasons";
            case 2:
                return "Count of episodes";
            case 3:
                return "Total length";
            default:
                throw new IndexOutOfBoundsException(BAD_COLUMN_ERROR_MESSAGE);
        }
    }

    @Override
    public final void update() {
        final Result<List<Show>> showsResult = showFacade.getAll();
        final Result<Integer> seasonsCountResult = showFacade.getSeasonsCount();
        final Result<Integer> episodesCountResult = showFacade.getEpisodesCount();
        final Result<Time> totalLengthResult = showFacade.getTotalLength();

        final Result<Void> result = new Result<>();
        result.addEvents(showsResult.getEvents());
        result.addEvents(seasonsCountResult.getEvents());
        result.addEvents(episodesCountResult.getEvents());
        result.addEvents(totalLengthResult.getEvents());

        if (Status.OK == result.getStatus()) {
            shows = showsResult.getData();
            seasonsCount = seasonsCountResult.getData();
            episodesCount = episodesCountResult.getData();
            totalLength = totalLengthResult.getData();
        } else {
            throw new IllegalArgumentException("Can't get data. " + result);
        }
    }

}
