package cz.vhromada.catalog.gui.song;

import javax.swing.JPanel;
import javax.swing.JTabbedPane;

import cz.vhromada.catalog.entity.Music;
import cz.vhromada.catalog.entity.Song;
import cz.vhromada.catalog.facade.SongFacade;
import cz.vhromada.catalog.gui.commons.AbstractInfoDialog;
import cz.vhromada.catalog.gui.commons.AbstractOverviewDataPanel;

import org.springframework.util.Assert;

/**
 * A class represents panel with songs' data.
 *
 * @author Vladimir Hromada
 */
public class SongsPanel extends AbstractOverviewDataPanel<Song> {

    /**
     * SerialVersionUID
     */
    private static final long serialVersionUID = 1L;

    /**
     * Facade for songs
     */
    private final SongFacade songFacade;

    /**
     * Music
     */
    private Music music;

    /**
     * Creates a new instance of SongsPanel.
     *
     * @param songFacade facade for songs
     * @param music      music
     * @throws IllegalArgumentException if facade for songs is null
     *                                  or music is null
     */
    public SongsPanel(final SongFacade songFacade, final Music music) {
        super(getSongsListDataModel(songFacade, music));

        this.songFacade = songFacade;
        this.music = music;
    }

    /**
     * Sets a new value to music.
     *
     * @param music new value
     * @throws IllegalArgumentException if music is null
     */
    public void setMusic(final Music music) {
        Assert.notNull(music, "Music mustn't be null.");

        this.music = music;
    }

    @Override
    public void newData() {
        throw new UnsupportedOperationException("Creating new data is not allowed for songs.");
    }

    @Override
    public void clearSelection() {
        throw new UnsupportedOperationException("Clearing selection is not allowed for songs.");
    }

    @Override
    public void save() {
        throw new UnsupportedOperationException("Saving data is not allowed for songs.");
    }

    @Override
    public boolean isSaved() {
        throw new UnsupportedOperationException("Testing if data are saved is not allowed for songs.");
    }

    @Override
    protected AbstractInfoDialog<Song> getInfoDialog(final boolean add, final Song data) {
        return add ? new SongInfoDialog() : new SongInfoDialog(data);
    }

    @Override
    protected void addData(final Song data) {
        songFacade.add(music, data);
    }

    @Override
    protected void deleteData() {
        throw new UnsupportedOperationException("Deleting data is not allowed for songs.");
    }

    @Override
    protected void updateData(final Song data) {
        songFacade.update(data);
    }

    @Override
    protected void removeData(final Song data) {
        songFacade.remove(data);
    }

    @Override
    protected void duplicatesData(final Song data) {
        songFacade.duplicate(data);
    }

    @Override
    protected void moveUpData(final Song data) {
        songFacade.moveUp(data);
    }

    @Override
    protected void moveDownData(final Song data) {
        songFacade.moveDown(data);
    }

    @Override
    protected JPanel getDataPanel(final Song data) {
        return new SongDataPanel(data);
    }

    @Override
    protected void updateDataOnChange(final JTabbedPane dataPanel, final Song data) {
    }

    /**
     * Returns data model for list with songs.
     *
     * @param facade      facade for songs
     * @param musicObject music
     * @return data model for list with songs
     * @throws IllegalArgumentException if facade for songs is null
     *                                  or music is null
     */
    private static SongsListDataModel getSongsListDataModel(final SongFacade facade, final Music musicObject) {
        return new SongsListDataModel(facade, musicObject);
    }

}
