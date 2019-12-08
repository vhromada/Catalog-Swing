package cz.vhromada.catalog.gui.season

import cz.vhromada.catalog.entity.Season
import cz.vhromada.catalog.entity.Show
import cz.vhromada.catalog.facade.EpisodeFacade
import cz.vhromada.catalog.facade.SeasonFacade
import cz.vhromada.catalog.gui.common.AbstractInfoDialog
import cz.vhromada.catalog.gui.common.AbstractOverviewDataPanel
import cz.vhromada.catalog.gui.episode.EpisodesPanel
import javax.swing.JPanel
import javax.swing.JTabbedPane

/**
 * A class represents panel with seasons' data.
 *
 * @author Vladimir Hromada
 */
class SeasonsPanel(
        private val seasonFacade: SeasonFacade,
        private val episodeFacade: EpisodeFacade,
        private var show: Show) : AbstractOverviewDataPanel<Season>(SeasonsListDataModel(seasonFacade, show)) {

    /**
     * Sets a new value to show.
     *
     * @param show new value
     */
    fun setShow(show: Show) {
        this.show = show
    }

    override fun newData() {
        error { "Creating new data is not allowed for seasons." }
    }

    override fun clearSelection() {
        error { "Clearing selection is not allowed for seasons." }
    }

    override fun save() {
        error { "Saving data is not allowed for seasons." }
    }

    override fun getInfoDialog(add: Boolean, data: Season?): AbstractInfoDialog<Season> {
        return if (add) SeasonInfoDialog() else SeasonInfoDialog(data!!)
    }

    override fun addData(data: Season) {
        seasonFacade.add(show, data)
    }

    override fun deleteData() {
        error { "Deleting data is not allowed for seasons." }
    }

    override fun updateData(data: Season) {
        seasonFacade.update(data)
    }

    override fun removeData(data: Season) {
        seasonFacade.remove(data)
    }

    override fun duplicatesData(data: Season) {
        seasonFacade.duplicate(data)
    }

    override fun moveUpData(data: Season) {
        seasonFacade.moveUp(data)
    }

    override fun moveDownData(data: Season) {
        seasonFacade.moveDown(data)
    }

    override fun getDataPanel(data: Season): JPanel {
        return SeasonDataPanel(data, episodeFacade)
    }

    override fun updateDataOnChange(dataPanel: JTabbedPane, data: Season) {
        val episodesPanel = EpisodesPanel(episodeFacade, data)
        episodesPanel.addPropertyChangeListener("update") {
            if (java.lang.Boolean.TRUE == it.newValue) {
                updateModel(data)
                episodesPanel.setSeason(data)
                firePropertyChange("update", false, true)
            }
        }
        dataPanel.add("Episodes", episodesPanel)
    }

}
