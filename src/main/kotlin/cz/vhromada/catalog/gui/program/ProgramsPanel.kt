package cz.vhromada.catalog.gui.program

import cz.vhromada.catalog.entity.Program
import cz.vhromada.catalog.facade.ProgramFacade
import cz.vhromada.catalog.gui.common.AbstractInfoDialog
import cz.vhromada.catalog.gui.common.AbstractOverviewDataPanel
import javax.swing.JPanel
import javax.swing.JTabbedPane

/**
 * A class represents panel with programs' data.
 *
 * @author Vladimir Hromada
 */
class ProgramsPanel(private val programFacade: ProgramFacade) : AbstractOverviewDataPanel<Program>(ProgramsListDataModel(programFacade), ProgramsStatsTableDataModel(programFacade)) {

    override fun getInfoDialog(add: Boolean, data: Program?): AbstractInfoDialog<Program> {
        return if (add) ProgramInfoDialog() else ProgramInfoDialog(data!!)
    }

    override fun deleteData() {
        programFacade.newData()
    }

    override fun addData(data: Program) {
        programFacade.add(data)
    }

    override fun updateData(data: Program) {
        programFacade.update(data)
    }

    override fun removeData(data: Program) {
        programFacade.remove(data)
    }

    override fun duplicatesData(data: Program) {
        programFacade.duplicate(data)
    }

    override fun moveUpData(data: Program) {
        programFacade.moveUp(data)
    }

    override fun moveDownData(data: Program) {
        programFacade.moveDown(data)
    }

    override fun getDataPanel(data: Program): JPanel {
        return ProgramDataPanel(data)
    }

    override fun updateDataOnChange(dataPanel: JTabbedPane, data: Program) {
        // nothing
    }

}
