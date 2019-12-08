package cz.vhromada.catalog.gui.music

import cz.vhromada.catalog.entity.Music
import cz.vhromada.catalog.facade.SongFacade
import cz.vhromada.catalog.gui.common.AbstractDataPanel
import cz.vhromada.catalog.gui.common.WebPageButtonType
import cz.vhromada.common.Time
import cz.vhromada.validation.result.Status
import javax.swing.GroupLayout
import javax.swing.JButton
import javax.swing.JLabel

/**
 * A class represents panel with music data.
 *
 * @author Vladimir Hromada
 */
class MusicDataPanel(
        music: Music,
        private val songFacade: SongFacade) : AbstractDataPanel<Music>() {

    /**
     * Label for name
     */
    private val nameLabel = JLabel("Name")

    /**
     * Label with name
     */
    private val nameData = JLabel()

    /**
     * Label for count of media
     */
    private val mediaCountLabel = JLabel("Count of media")

    /**
     * Label with count of media
     */
    private val mediaCountData = JLabel()

    /**
     * Label for count of songs
     */
    private val songsCountLabel = JLabel("Count of songs")

    /**
     * Label with count of songs
     */
    private val songsCountData = JLabel()

    /**
     * Label for total length
     */
    private val totalLengthLabel = JLabel("Total length")

    /**
     * Label with total length
     */
    private val totalLengthData = JLabel()

    /**
     * Label for note
     */
    private val noteLabel = JLabel("Note")

    /**
     * Label with note
     */
    private val noteData = JLabel()

    /**
     * Button for showing music czech Wikipedia page
     */
    private val wikiCzButton = JButton("Czech Wikipedia")

    /**
     * Button for showing music english Wikipedia page
     */
    private val wikiEnButton = JButton("English Wikipedia")

    /**
     * URL to czech Wikipedia page about music
     */
    private var wikiCz: String? = null

    /**
     * URL to english Wikipedia page about music
     */
    private var wikiEn: String? = null

    init {
        updateData(music)

        initData(nameLabel, nameData)
        initData(mediaCountLabel, mediaCountData)
        initData(songsCountLabel, songsCountData)
        initData(totalLengthLabel, totalLengthData)
        initData(noteLabel, noteData)

        initButton(wikiCzButton, WebPageButtonType.WIKI_CZ)
        initButton(wikiEnButton, WebPageButtonType.WIKI_EN)

        createLayout()
    }

    @Suppress("DuplicatedCode")
    override fun updateComponentData(data: Music) {
        nameData.text = data.name
        mediaCountData.text = data.mediaCount?.toString()
        songsCountData.text = getSongsCount(data)
        totalLengthData.text = getMusicLength(data)
        noteData.text = data.note

        wikiCz = data.wikiCz
        wikiEn = data.wikiEn

        wikiCzButton.isEnabled = !wikiCz.isNullOrBlank()
        wikiEnButton.isEnabled = !wikiEn.isNullOrBlank()
    }

    override fun getCzWikiUrl(): String {
        return wikiCz!!
    }

    override fun getEnWikiUrl(): String {
        return wikiEn!!
    }

    override fun getCsfdUrl(): String {
        error { "Getting URL to ČSFD page is not allowed for music." }
    }

    override fun getImdbUrl(): Int {
        error { "Getting URL to IMDB page is not allowed for music." }
    }

    override fun getHorizontalLayoutWithComponents(layout: GroupLayout, group: GroupLayout.Group): GroupLayout.Group {
        return group
                .addGroup(createHorizontalDataComponents(layout, nameLabel, nameData))
                .addGroup(createHorizontalDataComponents(layout, mediaCountLabel, mediaCountData))
                .addGroup(createHorizontalDataComponents(layout, songsCountLabel, songsCountData))
                .addGroup(createHorizontalDataComponents(layout, totalLengthLabel, totalLengthData))
                .addGroup(createHorizontalDataComponents(layout, noteLabel, noteData))
                .addGroup(createHorizontalButtons(layout, wikiCzButton, wikiEnButton))
    }

    override fun getVerticalLayoutWithComponents(layout: GroupLayout, group: GroupLayout.Group): GroupLayout.Group {
        return group
                .addGroup(createVerticalComponents(layout, nameLabel, nameData))
                .addGap(VERTICAL_GAP_SIZE)
                .addGroup(createVerticalComponents(layout, mediaCountLabel, mediaCountData))
                .addGap(VERTICAL_GAP_SIZE)
                .addGroup(createVerticalComponents(layout, songsCountLabel, songsCountData))
                .addGap(VERTICAL_GAP_SIZE)
                .addGroup(createVerticalComponents(layout, totalLengthLabel, totalLengthData))
                .addGap(VERTICAL_GAP_SIZE)
                .addGroup(createVerticalComponents(layout, noteLabel, noteData))
                .addGap(VERTICAL_GAP_SIZE)
                .addGroup(createVerticalButtons(layout, wikiCzButton, wikiEnButton))
    }

    /**
     * Returns count of music songs.
     *
     * @param music music
     * @return count of music songs
     */
    private fun getSongsCount(music: Music): String {
        val result = songFacade.find(music)

        if (Status.OK == result.status) {
            return result.data!!.size.toString()
        }
        throw IllegalArgumentException("Can't get data. $result")
    }

    /**
     * Returns total length of all music songs.
     *
     * @param music music
     * @return total length of all music songs
     */
    private fun getMusicLength(music: Music): String {
        val result = songFacade.find(music)

        if (Status.OK == result.status) {
            return Time(result.data!!.sumBy { it.length!! }).toString()
        }
        throw IllegalArgumentException("Can't get data. $result")
    }

}
