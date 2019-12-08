package cz.vhromada.catalog.gui.program

import cz.vhromada.catalog.entity.Program
import cz.vhromada.catalog.gui.common.AbstractDataPanel
import cz.vhromada.catalog.gui.common.WebPageButtonType
import javax.swing.GroupLayout
import javax.swing.JButton
import javax.swing.JLabel

/**
 * A class represents panel with program's data.
 *
 * @author Vladimir Hromada
 */
class ProgramDataPanel(program: Program) : AbstractDataPanel<Program>() {

    /**
     * Label for name
     */
    private val nameLabel = JLabel("Name")

    /**
     * Label with name
     */
    private val nameData = JLabel()

    /**
     * Label for additional data
     */
    private val dataLabel = JLabel("Additional data")

    /**
     * Label with additional data
     */
    private val dataData = JLabel()

    /**
     * Label for count of media
     */
    private val mediaCountLabel = JLabel("Count of media")

    /**
     * Label with count of media
     */
    private val mediaCountData = JLabel()

    /**
     * Label for note
     */
    private val noteLabel = JLabel("Note")

    /**
     * Label with note
     */
    private val noteData = JLabel()

    /**
     * Button for showing program's czech Wikipedia page
     */
    private val wikiCzButton = JButton("Czech Wikipedia")

    /**
     * Button for showing program's english Wikipedia page
     */
    private val wikiEnButton = JButton("English Wikipedia")

    /**
     * URL to czech Wikipedia page about program
     */
    private var wikiCz: String? = null

    /**
     * URL to english Wikipedia page about program
     */
    private var wikiEn: String? = null

    init {
        updateData(program)

        initData(nameLabel, nameData)
        initData(dataLabel, dataData)
        initData(mediaCountLabel, mediaCountData)
        initData(noteLabel, noteData)

        initButton(wikiCzButton, WebPageButtonType.WIKI_CZ)
        initButton(wikiEnButton, WebPageButtonType.WIKI_EN)

        createLayout()
    }

    @Suppress("DuplicatedCode")
    override fun updateComponentData(data: Program) {
        nameData.text = data.name
        dataData.text = getAdditionalData(data)
        mediaCountData.text = data.mediaCount?.toString()
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
        error { "Getting URL to ČSFD page is not allowed for programs." }
    }

    override fun getImdbUrl(): Int {
        error { "Getting URL to IMDB page is not allowed for programs." }
    }

    override fun getHorizontalLayoutWithComponents(layout: GroupLayout, group: GroupLayout.Group): GroupLayout.Group {
        return group
                .addGroup(createHorizontalDataComponents(layout, nameLabel, nameData))
                .addGroup(createHorizontalDataComponents(layout, dataLabel, dataData))
                .addGroup(createHorizontalDataComponents(layout, mediaCountLabel, mediaCountData))
                .addGroup(createHorizontalDataComponents(layout, noteLabel, noteData))
                .addGroup(createHorizontalButtons(layout, wikiCzButton, wikiEnButton))
    }

    override fun getVerticalLayoutWithComponents(layout: GroupLayout, group: GroupLayout.Group): GroupLayout.Group {
        return group
                .addGroup(createVerticalComponents(layout, nameLabel, nameData))
                .addGap(VERTICAL_GAP_SIZE)
                .addGroup(createVerticalComponents(layout, dataLabel, dataData))
                .addGap(VERTICAL_GAP_SIZE)
                .addGroup(createVerticalComponents(layout, mediaCountLabel, mediaCountData))
                .addGap(VERTICAL_GAP_SIZE)
                .addGroup(createVerticalComponents(layout, noteLabel, noteData))
                .addGap(VERTICAL_GAP_SIZE)
                .addGroup(createVerticalButtons(layout, wikiCzButton, wikiEnButton))
    }

    /**
     * Returns additional data.
     *
     * @param program program
     * @return additional data
     */
    private fun getAdditionalData(program: Program): String {
        val result = StringBuilder()
        if (program.crack!!) {
            result.append("Crack")
        }
        addAdditionalDataToResult(result, program.serialKey!!, "serial key")
        if (!program.otherData.isNullOrBlank()) {
            if (result.isNotEmpty()) {
                result.append(", ")
            }
            result.append(program.otherData)
        }

        return result.toString()
    }

}
