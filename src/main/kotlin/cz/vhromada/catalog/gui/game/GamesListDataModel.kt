package cz.vhromada.catalog.gui.game

import cz.vhromada.catalog.entity.Game
import cz.vhromada.catalog.facade.GameFacade
import cz.vhromada.catalog.gui.common.AbstractListDataModel
import cz.vhromada.common.result.Result

/**
 * A class represents data model for list with games.
 *
 * @author Vladimir Hromada
 */
class GamesListDataModel(private val gameFacade: GameFacade) : AbstractListDataModel<Game>() {

    init {
        update()
    }

    override fun getData(): Result<List<Game>> {
        return gameFacade.getAll()
    }

    override fun getDisplayValue(dataObject: Game): String {
        return dataObject.name!!
    }

}
