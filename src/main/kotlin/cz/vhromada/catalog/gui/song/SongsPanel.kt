package cz.vhromada.catalog.gui.song

import cz.vhromada.catalog.entity.Music
import cz.vhromada.catalog.entity.Song
import cz.vhromada.catalog.facade.SongFacade
import cz.vhromada.catalog.gui.common.AbstractInfoDialog
import cz.vhromada.catalog.gui.common.AbstractOverviewDataPanel
import javax.swing.JPanel
import javax.swing.JTabbedPane

/**
 * A class represents panel with songs' data.
 *
 * @author Vladimir Hromada
 */
class SongsPanel(
        private val songFacade: SongFacade,
        private var music: Music) : AbstractOverviewDataPanel<Song>(SongsListDataModel(songFacade, music)) {

    /**
     * Sets a new value to music.
     *
     * @param music new value
     */
    fun setMusic(music: Music) {
        this.music = music
    }

    override fun newData() {
        error { "Creating new data is not allowed for songs." }
    }

    override fun clearSelection() {
        error { "Clearing selection is not allowed for songs." }
    }

    override fun save() {
        error { "Saving data is not allowed for songs." }
    }

    override fun getInfoDialog(add: Boolean, data: Song?): AbstractInfoDialog<Song> {
        return if (add) SongInfoDialog() else SongInfoDialog(data!!)
    }

    override fun addData(data: Song) {
        songFacade.add(music, data)
    }

    override fun deleteData() {
        error { "Deleting data is not allowed for songs." }
    }

    override fun updateData(data: Song) {
        songFacade.update(data)
    }

    override fun removeData(data: Song) {
        songFacade.remove(data)
    }

    override fun duplicatesData(data: Song) {
        songFacade.duplicate(data)
    }

    override fun moveUpData(data: Song) {
        songFacade.moveUp(data)
    }

    override fun moveDownData(data: Song) {
        songFacade.moveDown(data)
    }

    override fun getDataPanel(data: Song): JPanel {
        return SongDataPanel(data)
    }

    override fun updateDataOnChange(dataPanel: JTabbedPane, data: Song) {
        // nothing
    }

}
