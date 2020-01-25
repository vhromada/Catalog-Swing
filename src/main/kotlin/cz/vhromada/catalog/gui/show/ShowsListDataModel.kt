package cz.vhromada.catalog.gui.show

import cz.vhromada.catalog.entity.Show
import cz.vhromada.catalog.facade.ShowFacade
import cz.vhromada.catalog.gui.common.AbstractListDataModel
import cz.vhromada.common.result.Result

/**
 * A class represents data model for list with shows.
 *
 * @author Vladimir Hromada
 */
class ShowsListDataModel(private val showFacade: ShowFacade) : AbstractListDataModel<Show>() {

    init {
        update()
    }

    override fun getData(): Result<List<Show>> {
        return showFacade.getAll()
    }

    override fun getDisplayValue(dataObject: Show): String {
        return dataObject.czechName!!
    }

}
