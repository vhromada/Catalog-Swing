package cz.vhromada.catalog.gui.episode

import cz.vhromada.catalog.entity.Episode
import cz.vhromada.catalog.entity.Season
import cz.vhromada.catalog.facade.EpisodeFacade
import cz.vhromada.catalog.gui.common.AbstractInfoDialog
import cz.vhromada.catalog.gui.common.AbstractOverviewDataPanel
import javax.swing.JPanel
import javax.swing.JTabbedPane

/**
 * A class represents panel with episodes' data.
 *
 * @author Vladimir Hromada
 */
class EpisodesPanel(
        private val episodeFacade: EpisodeFacade,
        private var season: Season) : AbstractOverviewDataPanel<Episode>(EpisodesListDataModel(episodeFacade, season)) {

    /**
     * Sets a new value to season.
     *
     * @param season new value
     */
    fun setSeason(season: Season) {
        this.season = season
    }

    override fun newData() {
        error { "Creating new data is not allowed for episodes." }
    }

    override fun clearSelection() {
        error { "Clearing selection is not allowed for episodes." }
    }

    override fun save() {
        error { "Saving data is not allowed for episodes." }
    }

    override fun getInfoDialog(add: Boolean, data: Episode?): AbstractInfoDialog<Episode> {
        return if (add) EpisodeInfoDialog() else EpisodeInfoDialog(data!!)
    }

    override fun addData(data: Episode) {
        episodeFacade.add(season, data)
    }

    override fun deleteData() {
        error { "Deleting data is not allowed for episodes." }
    }

    override fun updateData(data: Episode) {
        episodeFacade.update(data)
    }

    override fun removeData(data: Episode) {
        episodeFacade.remove(data)
    }

    override fun duplicatesData(data: Episode) {
        episodeFacade.duplicate(data)
    }

    override fun moveUpData(data: Episode) {
        episodeFacade.moveUp(data)
    }

    override fun moveDownData(data: Episode) {
        episodeFacade.moveDown(data)
    }

    override fun getDataPanel(data: Episode): JPanel {
        return EpisodeDataPanel(data)
    }

    override fun updateDataOnChange(dataPanel: JTabbedPane, data: Episode) {
        // nothing
    }

}
