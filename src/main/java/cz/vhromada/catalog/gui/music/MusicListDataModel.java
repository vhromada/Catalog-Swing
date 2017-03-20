package cz.vhromada.catalog.gui.music;

import java.util.List;

import cz.vhromada.catalog.entity.Music;
import cz.vhromada.catalog.facade.MusicFacade;
import cz.vhromada.catalog.gui.common.AbstractListDataModel;
import cz.vhromada.result.Result;

import org.springframework.util.Assert;

/**
 * A class represents data model for list with music.
 *
 * @author Vladimir Hromada
 */
public class MusicListDataModel extends AbstractListDataModel<Music> {

    /**
     * SerialVersionUID
     */
    private static final long serialVersionUID = 1L;

    /**
     * Facade for music
     */
    private final MusicFacade musicFacade;

    /**
     * Creates a new instance of MusicListDataModel.
     *
     * @param musicFacade facade for music
     * @throws IllegalArgumentException if facade for music is null
     */
    public MusicListDataModel(final MusicFacade musicFacade) {
        Assert.notNull(musicFacade, "Facade for music mustn't be null.");

        this.musicFacade = musicFacade;
        update();
    }

    @Override
    protected Result<List<Music>> getData() {
        return musicFacade.getAll();
    }

    @Override
    protected String getDisplayValue(final Music dataObject) {
        return dataObject.getName();
    }

}
