package cz.vhromada.catalog.gui.genre

import cz.vhromada.catalog.entity.Genre
import cz.vhromada.catalog.facade.GenreFacade
import cz.vhromada.catalog.gui.common.AbstractListDataModel
import cz.vhromada.validation.result.Result

/**
 * A class represents data model for list with genres.
 *
 * @author Vladimir Hromada
 */
class GenresListDataModel(private val genreFacade: GenreFacade) : AbstractListDataModel<Genre>() {

    init {
        update()
    }

    override fun getData(): Result<List<Genre>> {
        return genreFacade.getAll()
    }

    override fun getDisplayValue(dataObject: Genre): String {
        return dataObject.name!!
    }

}
