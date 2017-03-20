package cz.vhromada.catalog.gui.music;

import javax.swing.JPanel;
import javax.swing.JTabbedPane;

import cz.vhromada.catalog.entity.Music;
import cz.vhromada.catalog.facade.MusicFacade;
import cz.vhromada.catalog.facade.SongFacade;
import cz.vhromada.catalog.gui.commons.AbstractInfoDialog;
import cz.vhromada.catalog.gui.commons.AbstractOverviewDataPanel;
import cz.vhromada.catalog.gui.song.SongsPanel;

import org.springframework.util.Assert;

/**
 * A class represents panel with music data.
 *
 * @author Vladimir Hromada
 */
public class MusicPanel extends AbstractOverviewDataPanel<Music> {

    /**
     * SerialVersionUID
     */
    private static final long serialVersionUID = 1L;

    /**
     * Facade for music
     */
    private final MusicFacade musicFacade;

    /**
     * Facade for songs
     */
    private final SongFacade songFacade;

    /**
     * Creates a new instance of MusicPanel.
     *
     * @param musicFacade facade for music
     * @param songFacade  facade for songs
     * @throws IllegalArgumentException if facade for music is null
     *                                  or facade for songs is null
     */
    public MusicPanel(final MusicFacade musicFacade, final SongFacade songFacade) {
        super(getMusicListDataModel(musicFacade), getMusicStatsTableDataModel(musicFacade));

        Assert.notNull(songFacade, "Facade for songs mustn't be null.");

        this.musicFacade = musicFacade;
        this.songFacade = songFacade;
    }

    @Override
    protected AbstractInfoDialog<Music> getInfoDialog(final boolean add, final Music data) {
        return add ? new MusicInfoDialog() : new MusicInfoDialog(data);
    }

    @Override
    protected void deleteData() {
        musicFacade.newData();
    }

    @Override
    protected void addData(final Music data) {
        musicFacade.add(data);
    }

    @Override
    protected void updateData(final Music data) {
        musicFacade.update(data);
    }

    @Override
    protected void removeData(final Music data) {
        musicFacade.remove(data);
    }

    @Override
    protected void duplicatesData(final Music data) {
        musicFacade.duplicate(data);
    }

    @Override
    protected void moveUpData(final Music data) {
        musicFacade.moveUp(data);
    }

    @Override
    protected void moveDownData(final Music data) {
        musicFacade.moveDown(data);
    }

    @Override
    protected JPanel getDataPanel(final Music data) {
        return new MusicDataPanel(data, songFacade);
    }

    @Override
    protected void updateDataOnChange(final JTabbedPane dataPanel, final Music data) {
        final SongsPanel songsPanel = new SongsPanel(songFacade, data);
        songsPanel.addPropertyChangeListener("update", evt -> {
            if (Boolean.TRUE.equals(evt.getNewValue())) {
                updateModel(data);
                songsPanel.setMusic(data);
            }
        });
        dataPanel.add("Songs", songsPanel);
    }

    /**
     * Returns data model for list with music.
     *
     * @param facade facade for music
     * @return data model for list with music
     * @throws IllegalArgumentException if facade for music is null
     */
    private static MusicListDataModel getMusicListDataModel(final MusicFacade facade) {
        return new MusicListDataModel(facade);
    }

    /**
     * Returns data model for table with stats for music.
     *
     * @param facade facade for music
     * @return data model for table with stats for music
     * @throws IllegalArgumentException if facade for music is null
     */
    private static MusicStatsTableDataModel getMusicStatsTableDataModel(final MusicFacade facade) {
        return new MusicStatsTableDataModel(facade);
    }

}
