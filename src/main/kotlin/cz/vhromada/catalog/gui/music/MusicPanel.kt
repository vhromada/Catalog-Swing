package cz.vhromada.catalog.gui.music

import cz.vhromada.catalog.entity.Music
import cz.vhromada.catalog.facade.MusicFacade
import cz.vhromada.catalog.facade.SongFacade
import cz.vhromada.catalog.gui.common.AbstractInfoDialog
import cz.vhromada.catalog.gui.common.AbstractOverviewDataPanel
import cz.vhromada.catalog.gui.song.SongsPanel
import javax.swing.JPanel
import javax.swing.JTabbedPane

/**
 * A class represents panel with music data.
 *
 * @author Vladimir Hromada
 */
class MusicPanel(
        private val musicFacade: MusicFacade,
        private val songFacade: SongFacade) : AbstractOverviewDataPanel<Music>(MusicListDataModel(musicFacade), MusicStatsTableDataModel(musicFacade)) {

    override fun getInfoDialog(add: Boolean, data: Music?): AbstractInfoDialog<Music> {
        return if (add) MusicInfoDialog() else MusicInfoDialog(data!!)
    }

    override fun deleteData() {
        musicFacade.newData()
    }

    override fun addData(data: Music) {
        musicFacade.add(data)
    }

    override fun updateData(data: Music) {
        musicFacade.update(data)
    }

    override fun removeData(data: Music) {
        musicFacade.remove(data)
    }

    override fun duplicatesData(data: Music) {
        musicFacade.duplicate(data)
    }

    override fun moveUpData(data: Music) {
        musicFacade.moveUp(data)
    }

    override fun moveDownData(data: Music) {
        musicFacade.moveDown(data)
    }

    override fun getDataPanel(data: Music): JPanel {
        return MusicDataPanel(data, songFacade)
    }

    override fun updateDataOnChange(dataPanel: JTabbedPane, data: Music) {
        val songsPanel = SongsPanel(songFacade, data)
        songsPanel.addPropertyChangeListener("update") {
            if (java.lang.Boolean.TRUE == it.newValue) {
                updateModel(data)
                songsPanel.setMusic(data)
            }
        }
        dataPanel.add("Songs", songsPanel)
    }

}
