package cz.vhromada.catalog.gui.show

import cz.vhromada.catalog.entity.Show
import cz.vhromada.catalog.facade.EpisodeFacade
import cz.vhromada.catalog.facade.GenreFacade
import cz.vhromada.catalog.facade.PictureFacade
import cz.vhromada.catalog.facade.SeasonFacade
import cz.vhromada.catalog.facade.ShowFacade
import cz.vhromada.catalog.gui.common.AbstractInfoDialog
import cz.vhromada.catalog.gui.common.AbstractOverviewDataPanel
import cz.vhromada.catalog.gui.season.SeasonsPanel
import javax.swing.JPanel
import javax.swing.JTabbedPane

/**
 * A class represents panel with shows' data.
 *
 * @author Vladimir Hromada
 */
class ShowsPanel(
        private val showFacade: ShowFacade,
        private val seasonFacade: SeasonFacade,
        private val episodeFacade: EpisodeFacade,
        private val genreFacade: GenreFacade,
        private val pictureFacade: PictureFacade) : AbstractOverviewDataPanel<Show>(ShowsListDataModel(showFacade), ShowsStatsTableDataModel(showFacade)) {

    override fun getInfoDialog(add: Boolean, data: Show?): AbstractInfoDialog<Show> {
        return if (add) ShowInfoDialog(genreFacade, pictureFacade) else ShowInfoDialog(genreFacade, pictureFacade, data!!)
    }

    override fun deleteData() {
        showFacade.newData()
    }

    override fun addData(data: Show) {
        showFacade.add(data)
    }

    override fun updateData(data: Show) {
        showFacade.update(data)
    }

    override fun removeData(data: Show) {
        showFacade.remove(data)
    }

    override fun duplicatesData(data: Show) {
        showFacade.duplicate(data)
    }

    override fun moveUpData(data: Show) {
        showFacade.moveUp(data)
    }

    override fun moveDownData(data: Show) {
        showFacade.moveDown(data)
    }

    override fun getDataPanel(data: Show): JPanel {
        return ShowDataPanel(data, seasonFacade, episodeFacade, pictureFacade)
    }

    override fun updateDataOnChange(dataPanel: JTabbedPane, data: Show) {
        val seasonsPanel = SeasonsPanel(seasonFacade, episodeFacade, data)
        seasonsPanel.addPropertyChangeListener("update") {
            if (java.lang.Boolean.TRUE == it.newValue) {
                seasonsPanel.setShow(data)
                updateModel(data)
            }
        }
        dataPanel.add("Seasons", seasonsPanel)
    }

}
