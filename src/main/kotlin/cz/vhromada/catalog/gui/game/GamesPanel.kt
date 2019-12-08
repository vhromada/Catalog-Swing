package cz.vhromada.catalog.gui.game

import cz.vhromada.catalog.entity.Game
import cz.vhromada.catalog.facade.GameFacade
import cz.vhromada.catalog.gui.common.AbstractInfoDialog
import cz.vhromada.catalog.gui.common.AbstractOverviewDataPanel
import javax.swing.JPanel
import javax.swing.JTabbedPane

/**
 * A class represents panel with games' data.
 *
 * @author Vladimir Hromada
 */
class GamesPanel(private val gameFacade: GameFacade) : AbstractOverviewDataPanel<Game>(GamesListDataModel(gameFacade), GamesStatsTableDataModel(gameFacade)) {

    override fun getInfoDialog(add: Boolean, data: Game?): AbstractInfoDialog<Game> {
        return if (add) GameInfoDialog() else GameInfoDialog(data!!)
    }

    override fun deleteData() {
        gameFacade.newData()
    }

    override fun addData(data: Game) {
        gameFacade.add(data)
    }

    override fun updateData(data: Game) {
        gameFacade.update(data)
    }

    override fun removeData(data: Game) {
        gameFacade.remove(data)
    }

    override fun duplicatesData(data: Game) {
        gameFacade.duplicate(data)
    }

    override fun moveUpData(data: Game) {
        gameFacade.moveUp(data)
    }

    override fun moveDownData(data: Game) {
        gameFacade.moveDown(data)
    }

    override fun getDataPanel(data: Game): JPanel {
        return GameDataPanel(data)
    }

    override fun updateDataOnChange(dataPanel: JTabbedPane, data: Game) {
        // nothing
    }

}
