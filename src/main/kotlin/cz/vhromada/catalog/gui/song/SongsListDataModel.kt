package cz.vhromada.catalog.gui.song

import cz.vhromada.catalog.entity.Music
import cz.vhromada.catalog.entity.Song
import cz.vhromada.catalog.facade.SongFacade
import cz.vhromada.catalog.gui.common.AbstractListDataModel
import cz.vhromada.validation.result.Result

/**
 * A class represents data model for list with songs.
 *
 * @author Vladimir Hromada
 */
class SongsListDataModel(
        private val songFacade: SongFacade,
        private val music: Music) : AbstractListDataModel<Song>() {

    init {
        update()
    }

    override fun getData(): Result<List<Song>> {
        return songFacade.find(music)
    }

    override fun getDisplayValue(dataObject: Song): String {
        return dataObject.name!!
    }

}
