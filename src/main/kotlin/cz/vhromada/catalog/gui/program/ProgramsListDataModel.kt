package cz.vhromada.catalog.gui.program

import cz.vhromada.catalog.entity.Program
import cz.vhromada.catalog.facade.ProgramFacade
import cz.vhromada.catalog.gui.common.AbstractListDataModel
import cz.vhromada.common.result.Result

/**
 * A class represents data model for list with programs.
 *
 * @author Vladimir Hromada
 */
class ProgramsListDataModel(private val programFacade: ProgramFacade) : AbstractListDataModel<Program>() {

    init {
        update()
    }

    override fun getData(): Result<List<Program>> {
        return programFacade.getAll()
    }

    override fun getDisplayValue(dataObject: Program): String {
        return dataObject.name!!
    }

}
