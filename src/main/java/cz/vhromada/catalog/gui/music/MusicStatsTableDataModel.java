package cz.vhromada.catalog.gui.music;

import java.util.List;

import cz.vhromada.catalog.entity.Music;
import cz.vhromada.catalog.facade.MusicFacade;
import cz.vhromada.catalog.gui.common.AbstractStatsTableDataModel;
import cz.vhromada.common.Time;
import cz.vhromada.validation.result.Result;
import cz.vhromada.validation.result.Status;

import org.springframework.util.Assert;

/**
 * A class represents data model for table with stats for music.
 *
 * @author Vladimir Hromada
 */
@SuppressWarnings("Duplicates")
public class MusicStatsTableDataModel extends AbstractStatsTableDataModel {

    /**
     * SerialVersionUID
     */
    private static final long serialVersionUID = 1L;

    /**
     * Error message for bad column
     */
    private static final String BAD_COLUMN_ERROR_MESSAGE = "Bad column";

    /**
     * Facade for music
     */
    private final MusicFacade musicFacade;

    /**
     * List of music
     */
    private List<Music> musicList;

    /**
     * Total count of media
     */
    private int totalMediaCount;

    /**
     * Cunt of songs from all music
     */
    private int songsCount;

    /**
     * Total length of all songs
     */
    private Time totalLength;

    /**
     * Creates a new instance of MusicStatsTableDataModel.
     *
     * @param musicFacade facade for music
     * @throws IllegalArgumentException if facade for music is null
     */
    public MusicStatsTableDataModel(final MusicFacade musicFacade) {
        Assert.notNull(musicFacade, "Facade for music mustn't be null.");

        this.musicFacade = musicFacade;
        update();
    }

    @Override
    public Object getValueAt(final int rowIndex, final int columnIndex) {
        switch (columnIndex) {
            case 0:
                return musicList.size();
            case 1:
                return totalMediaCount;
            case 2:
                return songsCount;
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
                return "Count of music";
            case 1:
                return "Count of media";
            case 2:
                return "Count of songs";
            case 3:
                return "Total length";
            default:
                throw new IndexOutOfBoundsException(BAD_COLUMN_ERROR_MESSAGE);
        }
    }

    @Override
    public final void update() {
        final Result<List<Music>> musicResult = musicFacade.getAll();
        final Result<Integer> totalMediaCountResult = musicFacade.getTotalMediaCount();
        final Result<Integer> songsCountResult = musicFacade.getSongsCount();
        final Result<Time> totalLengthResult = musicFacade.getTotalLength();

        final Result<Void> result = new Result<>();
        result.addEvents(musicResult.getEvents());
        result.addEvents(totalMediaCountResult.getEvents());
        result.addEvents(songsCountResult.getEvents());
        result.addEvents(totalLengthResult.getEvents());

        if (Status.OK == result.getStatus()) {
            musicList = musicResult.getData();
            totalMediaCount = totalMediaCountResult.getData();
            songsCount = songsCountResult.getData();
            totalLength = totalLengthResult.getData();
        } else {
            throw new IllegalArgumentException("Can't get data. " + result);
        }
    }

}
