package cz.vhromada.catalog.gui.show

import cz.vhromada.catalog.entity.Show
import cz.vhromada.catalog.facade.ShowFacade
import cz.vhromada.catalog.gui.common.AbstractStatsTableDataModel
import cz.vhromada.common.Time
import cz.vhromada.common.result.Result
import cz.vhromada.common.result.Status

/**
 * A class represents data model for table with stats for shows.
 *
 * @author Vladimir Hromada
 */
class ShowsStatsTableDataModel(private val showFacade: ShowFacade) : AbstractStatsTableDataModel() {

    /**
     * List of shows
     */
    private lateinit var shows: List<Show>

    /**
     * Count of seasons from all shows
     */
    private var seasonsCount: Int = 0

    /**
     * Count of episodes from all shows
     */
    private var episodesCount: Int = 0

    /**
     * Total length of all shows
     */
    private lateinit var totalLength: Time

    init {
        update()
    }

    override fun getValueAt(rowIndex: Int, columnIndex: Int): Any {
        return when (columnIndex) {
            0 -> shows.size
            1 -> seasonsCount
            2 -> episodesCount
            3 -> totalLength.toString()
            else -> throw IndexOutOfBoundsException(BAD_COLUMN_ERROR_MESSAGE)
        }
    }

    override fun getColumnCount(): Int {
        return 4
    }

    override fun getRowCount(): Int {
        return 1
    }

    override fun getColumnClass(columnIndex: Int): Class<*> {
        return when (columnIndex) {
            0 -> Int::class.java
            1 -> Int::class.java
            2 -> Int::class.java
            3 -> String::class.java
            else -> throw IndexOutOfBoundsException(BAD_COLUMN_ERROR_MESSAGE)
        }
    }

    override fun getColumnName(column: Int): String {
        return when (column) {
            0 -> "Count of shows"
            1 -> "Count of seasons"
            2 -> "Count of episodes"
            3 -> "Total length"
            else -> throw IndexOutOfBoundsException(BAD_COLUMN_ERROR_MESSAGE)
        }
    }

    @Suppress("DuplicatedCode")
    override fun update() {
        val showsResult = showFacade.getAll()
        val seasonsCountResult = showFacade.getSeasonsCount()
        val episodesCountResult = showFacade.getEpisodesCount()
        val totalLengthResult = showFacade.getTotalLength()

        val result = Result<Void>()
        result.addEvents(showsResult.events())
        result.addEvents(seasonsCountResult.events())
        result.addEvents(episodesCountResult.events())
        result.addEvents(totalLengthResult.events())

        if (Status.OK == result.status) {
            shows = showsResult.data!!
            seasonsCount = seasonsCountResult.data!!
            episodesCount = episodesCountResult.data!!
            totalLength = totalLengthResult.data!!
        } else {
            throw IllegalArgumentException("Can't get data. $result")
        }
    }

    companion object {

        /**
         * Error message for bad column
         */
        private const val BAD_COLUMN_ERROR_MESSAGE = "Bad column"

    }

}
