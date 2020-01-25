package cz.vhromada.catalog.gui.season

import cz.vhromada.catalog.entity.Season
import cz.vhromada.catalog.entity.Show
import cz.vhromada.catalog.facade.SeasonFacade
import cz.vhromada.catalog.gui.common.AbstractListDataModel
import cz.vhromada.common.result.Result

/**
 * A class represents data model for list with seasons.
 *
 * @author Vladimir Hromada
 */
class SeasonsListDataModel(
        private val seasonFacade: SeasonFacade,
        private val show: Show) : AbstractListDataModel<Season>() {

    init {
        update()
    }

    override fun getData(): Result<List<Season>> {
        return seasonFacade.find(show)
    }

    override fun getDisplayValue(dataObject: Season): String {
        return dataObject.number.toString()
    }

}
