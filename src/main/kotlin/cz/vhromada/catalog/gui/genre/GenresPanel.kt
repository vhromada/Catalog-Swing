package cz.vhromada.catalog.gui.genre

import cz.vhromada.catalog.entity.Genre
import cz.vhromada.catalog.facade.GenreFacade
import cz.vhromada.catalog.gui.common.AbstractInfoDialog
import cz.vhromada.catalog.gui.common.AbstractOverviewDataPanel
import javax.swing.JPanel
import javax.swing.JTabbedPane

/**
 * A class represents panel with genres' data.
 *
 * @author Vladimir Hromada
 */
class GenresPanel(private val genreFacade: GenreFacade) : AbstractOverviewDataPanel<Genre>(GenresListDataModel(genreFacade), GenresStatsTableDataModel(genreFacade)) {

    override fun getInfoDialog(add: Boolean, data: Genre?): AbstractInfoDialog<Genre> {
        return if (add) GenreInfoDialog() else GenreInfoDialog(data!!)
    }

    override fun deleteData() {
        genreFacade.newData()
    }

    override fun addData(data: Genre) {
        genreFacade.add(data)
    }

    override fun updateData(data: Genre) {
        genreFacade.update(data)
    }

    override fun removeData(data: Genre) {
        genreFacade.remove(data)
    }

    override fun duplicatesData(data: Genre) {
        genreFacade.duplicate(data)
    }

    override fun moveUpData(data: Genre) {
        genreFacade.moveUp(data)
    }

    override fun moveDownData(data: Genre) {
        genreFacade.moveDown(data)
    }

    override fun getDataPanel(data: Genre): JPanel {
        return GenreDataPanel(data)
    }

    override fun updateDataOnChange(dataPanel: JTabbedPane, data: Genre) {
        // nothing
    }

}
