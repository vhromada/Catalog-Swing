package cz.vhromada.catalog.gui.music

import cz.vhromada.catalog.entity.Music
import cz.vhromada.catalog.facade.MusicFacade
import cz.vhromada.catalog.gui.common.AbstractListDataModel
import cz.vhromada.common.result.Result

/**
 * A class represents data model for list with music.
 *
 * @author Vladimir Hromada
 */
class MusicListDataModel(private val musicFacade: MusicFacade) : AbstractListDataModel<Music>() {

    init {
        update()
    }

    override fun getData(): Result<List<Music>> {
        return musicFacade.getAll()
    }

    override fun getDisplayValue(dataObject: Music): String {
        return dataObject.name!!
    }

}
