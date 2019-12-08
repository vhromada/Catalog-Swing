package cz.vhromada.catalog.gui.music

import cz.vhromada.catalog.entity.Music
import cz.vhromada.catalog.facade.MusicFacade
import cz.vhromada.catalog.gui.common.AbstractStatsTableDataModel
import cz.vhromada.common.Time
import cz.vhromada.validation.result.Result
import cz.vhromada.validation.result.Status

/**
 * A class represents data model for table with stats for music.
 *
 * @author Vladimir Hromada
 */
class MusicStatsTableDataModel(private val musicFacade: MusicFacade) : AbstractStatsTableDataModel() {

    /**
     * List of music
     */
    private lateinit var musicList: List<Music>

    /**
     * Total count of media
     */
    private var totalMediaCount: Int = 0

    /**
     * Cunt of songs from all music
     */
    private var songsCount: Int = 0

    /**
     * Total length of all songs
     */
    private lateinit var totalLength: Time

    init {
        update()
    }

    override fun getValueAt(rowIndex: Int, columnIndex: Int): Any {
        return when (columnIndex) {
            0 -> musicList.size
            1 -> totalMediaCount
            2 -> songsCount
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
            0 -> "Count of music"
            1 -> "Count of media"
            2 -> "Count of songs"
            3 -> "Total length"
            else -> throw IndexOutOfBoundsException(BAD_COLUMN_ERROR_MESSAGE)
        }
    }

    @Suppress("DuplicatedCode")
    override fun update() {
        val musicResult = musicFacade.getAll()
        val totalMediaCountResult = musicFacade.getTotalMediaCount()
        val songsCountResult = musicFacade.getSongsCount()
        val totalLengthResult = musicFacade.getTotalLength()

        val result = Result<Void>()
        result.addEvents(musicResult.events())
        result.addEvents(totalMediaCountResult.events())
        result.addEvents(songsCountResult.events())
        result.addEvents(totalLengthResult.events())

        if (Status.OK == result.status) {
            musicList = musicResult.data!!
            totalMediaCount = totalMediaCountResult.data!!
            songsCount = songsCountResult.data!!
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
