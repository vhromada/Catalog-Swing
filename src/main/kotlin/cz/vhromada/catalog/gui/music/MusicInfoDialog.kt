package cz.vhromada.catalog.gui.music

import cz.vhromada.catalog.entity.Music
import cz.vhromada.catalog.gui.common.AbstractInfoDialog
import javax.swing.GroupLayout
import javax.swing.JLabel
import javax.swing.JSpinner
import javax.swing.JTextField
import javax.swing.SpinnerNumberModel

/**
 * A class represents dialog for music.
 *
 * @author Vladimir Hromada
 */
class MusicInfoDialog : AbstractInfoDialog<Music> {

    /**
     * Label for name
     */
    private val nameLabel = JLabel("Name")

    /**
     * Text field for name
     */
    private val nameData = JTextField()

    /**
     * Label for czech Wikipedia
     */
    private val wikiCzLabel = JLabel("Czech Wikipedia")

    /**
     * Text field for czech Wikipedia
     */
    private val wikiCzData = JTextField()

    /**
     * Label for english Wikipedia
     */
    private val wikiEnLabel = JLabel("English Wikipedia")

    /**
     * Text field for english Wikipedia
     */
    private val wikiEnData = JTextField()

    /**
     * Label for count of media
     */
    private val mediaCountLabel = JLabel("Count of media")

    /**
     * Spinner for count of media
     */
    private val mediaCountData = JSpinner(SpinnerNumberModel(1, 1, Integer.MAX_VALUE, 1))

    /**
     * Label for note
     */
    private val noteLabel = JLabel("Note")

    /**
     * Text field for note
     */
    private val noteData = JTextField()

    /**
     * Creates a new instance of MusicInfoDialog.
     */
    constructor() {
        init()
    }

    /**
     * Creates a new instance of MusicInfoDialog.
     *
     * @param music music
     * @throws IllegalArgumentException if music is null
     */
    constructor(music: Music) : super(music) {
        init()
        this.nameData.text = music.name
        this.wikiCzData.text = music.wikiCz
        this.wikiEnData.text = music.wikiEn
        this.mediaCountData.value = music.mediaCount
        this.noteData.text = music.note
    }

    override fun initComponents() {
        initLabelComponent(nameLabel, nameData)
        initLabelComponent(wikiCzLabel, wikiCzData)
        initLabelComponent(wikiEnLabel, wikiEnData)
        initLabelComponent(mediaCountLabel, mediaCountData)
        initLabelComponent(noteLabel, noteData)

        addInputValidator(nameData)
    }

    override fun processData(objectData: Music?): Music {
        if (objectData == null) {
            return Music(id = null,
                    name = nameData.text,
                    wikiCz = wikiCzData.text,
                    wikiEn = wikiEnData.text,
                    mediaCount = mediaCountData.value as Int,
                    note = noteData.text,
                    position = null)
        }
        return objectData.copy(name = nameData.text,
                wikiCz = wikiCzData.text,
                wikiEn = wikiEnData.text,
                mediaCount = mediaCountData.value as Int,
                note = noteData.text)
    }

    /**
     * Returns true if input is valid: name isn't empty string.
     *
     * @return true if input is valid: name isn't empty string
     */
    override fun isInputValid(): Boolean {
        return nameData.text.isNotBlank()
    }

    override fun getHorizontalLayoutWithComponents(layout: GroupLayout, group: GroupLayout.Group): GroupLayout.Group {
        return group
                .addGroup(createHorizontalComponents(layout, nameLabel, nameData))
                .addGroup(createHorizontalComponents(layout, wikiCzLabel, wikiCzData))
                .addGroup(createHorizontalComponents(layout, wikiEnLabel, wikiEnData))
                .addGroup(createHorizontalComponents(layout, mediaCountLabel, mediaCountData))
                .addGroup(createHorizontalComponents(layout, noteLabel, noteData))
    }

    override fun getVerticalLayoutWithComponents(layout: GroupLayout, group: GroupLayout.Group): GroupLayout.Group {
        return group
                .addGroup(createVerticalComponents(layout, nameLabel, nameData))
                .addGap(VERTICAL_GAP_SIZE)
                .addGroup(createVerticalComponents(layout, wikiCzLabel, wikiCzData))
                .addGap(VERTICAL_GAP_SIZE)
                .addGroup(createVerticalComponents(layout, wikiEnLabel, wikiEnData))
                .addGap(VERTICAL_GAP_SIZE)
                .addGroup(createVerticalComponents(layout, mediaCountLabel, mediaCountData))
                .addGap(VERTICAL_GAP_SIZE)
                .addGroup(createVerticalComponents(layout, noteLabel, noteData))
    }

}
