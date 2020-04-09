package cz.vhromada.catalog.gui.song

import cz.vhromada.catalog.entity.Song
import cz.vhromada.catalog.gui.common.AbstractDataPanel
import cz.vhromada.common.entity.Time
import javax.swing.GroupLayout
import javax.swing.JLabel

/**
 * A class represents panel with song's data.
 *
 * @author Vladimir Hromada
 */
class SongDataPanel(song: Song) : AbstractDataPanel<Song>() {

    /**
     * Label for name
     */
    private val nameLabel = JLabel("Name")

    /**
     * Label with name
     */
    private val nameData = JLabel()

    /**
     * Label for length
     */
    private val lengthLabel = JLabel("Length")

    /**
     * Label with length
     */
    private val lengthData = JLabel()

    /**
     * Label for note
     */
    private val noteLabel = JLabel("Note")

    /**
     * Label with note
     */
    private val noteData = JLabel()

    init {
        updateData(song)

        initData(nameLabel, nameData)
        initData(lengthLabel, lengthData)
        initData(noteLabel, noteData)

        createLayout()
    }

    override fun updateComponentData(data: Song) {
        nameData.text = data.name
        lengthData.text = Time(data.length!!).toString()
        noteData.text = data.note
    }

    override fun getCzWikiUrl(): String {
        error { "Getting URL to czech Wikipedia page is not allowed for songs." }
    }

    override fun getEnWikiUrl(): String {
        error { "Getting URL to english Wikipedia page is not allowed for songs." }
    }

    override fun getCsfdUrl(): String {
        error { "Getting URL to ČSFD page is not allowed for songs." }
    }

    override fun getImdbUrl(): Int {
        error { "Getting URL to IMDB page is not allowed for songs." }
    }

    override fun getHorizontalLayoutWithComponents(layout: GroupLayout, group: GroupLayout.Group): GroupLayout.Group {
        return group
                .addGroup(createHorizontalDataComponents(layout, nameLabel, nameData))
                .addGroup(createHorizontalDataComponents(layout, lengthLabel, lengthData))
                .addGroup(createHorizontalDataComponents(layout, noteLabel, noteData))
    }

    override fun getVerticalLayoutWithComponents(layout: GroupLayout, group: GroupLayout.Group): GroupLayout.Group {
        return group
                .addGroup(createVerticalComponents(layout, nameLabel, nameData))
                .addGap(VERTICAL_GAP_SIZE)
                .addGroup(createVerticalComponents(layout, lengthLabel, lengthData))
                .addGap(VERTICAL_GAP_SIZE)
                .addGroup(createVerticalComponents(layout, noteLabel, noteData))
                .addGap(VERTICAL_GAP_SIZE)
    }

}
