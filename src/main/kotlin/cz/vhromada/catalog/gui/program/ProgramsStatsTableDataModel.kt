package cz.vhromada.catalog.gui.program

import cz.vhromada.catalog.entity.Program
import cz.vhromada.catalog.facade.ProgramFacade
import cz.vhromada.catalog.gui.common.AbstractStatsTableDataModel
import cz.vhromada.validation.result.Result
import cz.vhromada.validation.result.Status

/**
 * A class represents data model for table with stats for programs.
 *
 * @author Vladimir Hromada
 */
class ProgramsStatsTableDataModel(private val programFacade: ProgramFacade) : AbstractStatsTableDataModel() {

    /**
     * List of programs
     */
    private lateinit var programs: List<Program>

    /**
     * Total count of media
     */
    private var totalMediaCount: Int = 0

    init {
        update()
    }

    override fun getValueAt(rowIndex: Int, columnIndex: Int): Any {
        return when (columnIndex) {
            0 -> programs.size
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
            0 -> "Count of programs"
            1 -> "Count of media"
            else -> throw IndexOutOfBoundsException("Bad column")
        }
    }

    @Suppress("DuplicatedCode")
    override fun update() {
        val programsResult = programFacade.getAll()
        val totalMediaCountResult = programFacade.getTotalMediaCount()

        val result = Result<Void>()
        result.addEvents(programsResult.events())
        result.addEvents(totalMediaCountResult.events())

        if (Status.OK == result.status) {
            programs = programsResult.data!!
            totalMediaCount = totalMediaCountResult.data!!
        } else {
            throw IllegalArgumentException("Can't get data. $result")
        }
    }

}
