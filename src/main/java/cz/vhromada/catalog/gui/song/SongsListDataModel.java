package cz.vhromada.catalog.gui.song;

import java.util.List;

import cz.vhromada.catalog.entity.Music;
import cz.vhromada.catalog.entity.Song;
import cz.vhromada.catalog.facade.SongFacade;
import cz.vhromada.catalog.gui.common.AbstractListDataModel;
import cz.vhromada.validation.result.Result;

import org.springframework.util.Assert;

/**
 * A class represents data model for list with songs.
 *
 * @author Vladimir Hromada
 */
public class SongsListDataModel extends AbstractListDataModel<Song> {

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
    private final Music music;

    /**
     * Creates a new instance of SongsListDataModel.
     *
     * @param songFacade facade for songs
     * @param music      music
     * @throws IllegalArgumentException if facade for songs is null
     *                                  or music is null
     */
    public SongsListDataModel(final SongFacade songFacade, final Music music) {
        Assert.notNull(songFacade, "Facade for songs mustn't be null.");
        Assert.notNull(music, "Music mustn't be null.");

        this.songFacade = songFacade;
        this.music = music;
        update();
    }

    @Override
    protected Result<List<Song>> getData() {
        return songFacade.find(music);
    }

    @Override
    protected String getDisplayValue(final Song dataObject) {
        return dataObject.getName();
    }

}
