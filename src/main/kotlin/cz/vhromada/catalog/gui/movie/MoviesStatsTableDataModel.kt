package cz.vhromada.catalog.gui.movie

import cz.vhromada.catalog.entity.Movie
import cz.vhromada.catalog.facade.MovieFacade
import cz.vhromada.catalog.gui.common.AbstractStatsTableDataModel
import cz.vhromada.common.Time
import cz.vhromada.validation.result.Result
import cz.vhromada.validation.result.Status

/**
 * A class represents data model for table with stats for movies.
 *
 * @author Vladimir Hromada
 */
class MoviesStatsTableDataModel(private val movieFacade: MovieFacade) : AbstractStatsTableDataModel() {

    /**
     * List of movies
     */
    private lateinit var movies: List<Movie>

    /**
     * Total length of all movies
     */
    private lateinit var totalLength: Time

    /**
     * Total count of media
     */
    private var totalMediaCount: Int = 0

    init {
        update()
    }

    override fun getValueAt(rowIndex: Int, columnIndex: Int): Any {
        return when (columnIndex) {
            0 -> movies.size
            1 -> totalLength.toString()
            2 -> totalMediaCount
            else -> throw IndexOutOfBoundsException(BAD_COLUMN_ERROR_MESSAGE)
        }
    }

    override fun getColumnCount(): Int {
        return 3
    }

    override fun getRowCount(): Int {
        return 1
    }

    override fun getColumnClass(columnIndex: Int): Class<*> {
        return when (columnIndex) {
            0 -> Int::class.java
            1 -> String::class.java
            2 -> Int::class.java
            else -> throw IndexOutOfBoundsException(BAD_COLUMN_ERROR_MESSAGE)
        }
    }

    override fun getColumnName(column: Int): String {
        return when (column) {
            0 -> "Count of movies"
            1 -> "Total length"
            2 -> "Count of media"
            else -> throw IndexOutOfBoundsException(BAD_COLUMN_ERROR_MESSAGE)
        }
    }

    override fun update() {
        val showsResult = movieFacade.getAll()
        val totalLengthResult = movieFacade.getTotalLength()
        val totalMediaCountResult = movieFacade.getTotalMediaCount()

        val result = Result<Void>()
        result.addEvents(showsResult.events())
        result.addEvents(totalLengthResult.events())
        result.addEvents(totalMediaCountResult.events())

        if (Status.OK == result.status) {
            movies = showsResult.data!!
            totalLength = totalLengthResult.data!!
            totalMediaCount = totalMediaCountResult.data!!
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
