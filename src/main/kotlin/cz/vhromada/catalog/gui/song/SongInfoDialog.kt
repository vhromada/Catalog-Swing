package cz.vhromada.catalog.gui.song

import cz.vhromada.catalog.entity.Song
import cz.vhromada.catalog.gui.common.AbstractInfoDialog
import cz.vhromada.catalog.gui.common.TimeDataPanel
import cz.vhromada.common.Time
import javax.swing.GroupLayout
import javax.swing.JLabel
import javax.swing.JTextField

/**
 * A class represents dialog for song.
 *
 * @author Vladimir Hromada
 */
class SongInfoDialog : AbstractInfoDialog<Song> {

    /**
     * Label for name
     */
    private val nameLabel = JLabel("Name")

    /**
     * Text field for name
     */
    private val nameData = JTextField()

    /**
     * Length panel
     */
    private val lengthPanel = TimeDataPanel("Length")

    /**
     * Label for note
     */
    private val noteLabel = JLabel("Note")

    /**
     * Text field for note
     */
    private val noteData = JTextField()

    /**
     * Creates a new instance of SongInfoDialog.
     */
    constructor() {
        init()
    }

    /**
     * Creates a new instance of SongInfoDialog.
     *
     * @param song song
     * @throws IllegalArgumentException if song is null
     */
    constructor(song: Song) : super(song) {
        init()
        this.nameData.text = song.name
        this.lengthPanel.length = Time(song.length!!)
        this.noteData.text = song.note
    }

    override fun initComponents() {
        initLabelComponent(nameLabel, nameData)
        initLabelComponent(noteLabel, noteData)

        addInputValidator(nameData)
    }

    override fun processData(objectData: Song?): Song {
        if (objectData == null) {
            return Song(id = null,
                    name = nameData.text,
                    length = lengthPanel.length.length,
                    note = noteData.text,
                    position = null)
        }
        return objectData.copy(
                name = nameData.text,
                length = lengthPanel.length.length,
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
                .addComponent(lengthPanel)
                .addGroup(createHorizontalComponents(layout, noteLabel, noteData))
    }

    override fun getVerticalLayoutWithComponents(layout: GroupLayout, group: GroupLayout.Group): GroupLayout.Group {
        return group
                .addGroup(createVerticalComponents(layout, nameLabel, nameData))
                .addGap(VERTICAL_GAP_SIZE)
                .addComponent(lengthPanel)
                .addGap(VERTICAL_GAP_SIZE)
                .addGroup(createVerticalComponents(layout, noteLabel, noteData))
    }

}
