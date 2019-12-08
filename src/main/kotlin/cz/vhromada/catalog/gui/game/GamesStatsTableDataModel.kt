package cz.vhromada.catalog.gui.game

import cz.vhromada.catalog.entity.Game
import cz.vhromada.catalog.facade.GameFacade
import cz.vhromada.catalog.gui.common.AbstractStatsTableDataModel
import cz.vhromada.validation.result.Result
import cz.vhromada.validation.result.Status

/**
 * A class represents data model for table with stats for games.
 *
 * @author Vladimir Hromada
 */
class GamesStatsTableDataModel(private val gameFacade: GameFacade) : AbstractStatsTableDataModel() {

    /**
     * List of games
     */
    private lateinit var games: List<Game>

    /**
     * Total count of media
     */
    private var totalMediaCount: Int = 0

    init {
        update()
    }

    override fun getValueAt(rowIndex: Int, columnIndex: Int): Any {
        return when (columnIndex) {
            0 -> games.size
            1 -> totalMediaCount
            else -> throw IndexOutOfBoundsException("Bad column")
        }
    }

    override fun getColumnCount(): Int {
        return 2
    }

    override fun getRowCount(): Int {
        return 1
    }

    override fun getColumnClass(columnIndex: Int): Class<*> {
        return Int::class.java
    }

    override fun getColumnName(column: Int): String {
        return when (column) {
            0 -> "Count of games"
            1 -> "Count of media"
            else -> throw IndexOutOfBoundsException("Bad column")
        }
    }

    @Suppress("DuplicatedCode")
    override fun update() {
        val gamesResult = gameFacade.getAll()
        val totalMediaCountResult = gameFacade.getTotalMediaCount()

        val result = Result<Void>()
        result.addEvents(gamesResult.events())
        result.addEvents(totalMediaCountResult.events())

        if (Status.OK == result.status) {
            games = gamesResult.data!!
            totalMediaCount = totalMediaCountResult.data!!
        } else {
            throw IllegalArgumentException("Can't get data. $result")
        }
    }

}
