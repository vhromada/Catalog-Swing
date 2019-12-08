package cz.vhromada.catalog.gui.genre

import cz.vhromada.catalog.entity.Genre
import cz.vhromada.catalog.facade.GenreFacade
import cz.vhromada.catalog.gui.common.AbstractStatsTableDataModel
import cz.vhromada.validation.result.Status

/**
 * A class represents data model for table with stats for genres.
 *
 * @author Vladimir Hromada
 */
class GenresStatsTableDataModel(private val genreFacade: GenreFacade) : AbstractStatsTableDataModel() {

    /**
     * List of genres
     */
    private lateinit var genres: List<Genre>

    init {
        update()
    }

    override fun getValueAt(rowIndex: Int, columnIndex: Int): Any {
        when (columnIndex) {
            0 -> return genres.size
            else -> throw IndexOutOfBoundsException("Bad column")
        }
    }

    override fun getColumnCount(): Int {
        return 1
    }

    override fun getRowCount(): Int {
        return 1
    }

    override fun getColumnClass(columnIndex: Int): Class<*> {
        return Int::class.java
    }

    override fun getColumnName(column: Int): String {
        when (column) {
            0 -> return "Count of genres"
            else -> throw IndexOutOfBoundsException("Bad column")
        }
    }

    override fun update() {
        val result = genreFacade.getAll()

        if (Status.OK == result.status) {
            genres = result.data!!
        } else {
            throw IllegalArgumentException("Can't get data. $result")
        }
    }

}
