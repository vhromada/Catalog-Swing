package cz.vhromada.catalog.gui.episode

import cz.vhromada.catalog.entity.Episode
import cz.vhromada.catalog.gui.common.AbstractInfoDialog
import cz.vhromada.catalog.gui.common.TimeDataPanel
import cz.vhromada.common.Time
import javax.swing.GroupLayout
import javax.swing.JLabel
import javax.swing.JSpinner
import javax.swing.JTextField
import javax.swing.SpinnerNumberModel

/**
 * A class represents dialog for episode.
 *
 * @author Vladimir Hromada
 */
class EpisodeInfoDialog : AbstractInfoDialog<Episode> {

    /**
     * Label for episode's number
     */
    private val numberLabel = JLabel("Number of episode")

    /**
     * Spinner for episode's number
     */
    private val numberData = JSpinner(SpinnerNumberModel(1, 1, Integer.MAX_VALUE, 1))

    /**
     * Label for name
     */
    private val nameLabel = JLabel("Name")

    /**
     * Text field for name
     */
    private val nameData = JTextField()

    /**
     * Panel for length
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
     * Creates a new instance of EpisodeInfoDialog.
     */
    constructor() {
        init()
    }

    /**
     * Creates a new instance of EpisodeInfoDialog.
     *
     * @param episode episode
     */
    constructor(episode: Episode) : super(episode) {
        init()
        this.numberData.value = episode.number
        this.nameData.text = episode.name
        this.lengthPanel.length = Time(episode.length!!)
        this.noteData.text = episode.note
    }

    override fun initComponents() {
        initLabelComponent(numberLabel, numberData)
        initLabelComponent(nameLabel, nameData)
        initLabelComponent(noteLabel, noteData)

        addInputValidator(nameData)
    }

    override fun processData(objectData: Episode?): Episode {
        if (objectData == null) {
            return Episode(id = null,
                    number = numberData.value as Int,
                    name = nameData.text,
                    length = lengthPanel.length.length,
                    note = noteData.text,
                    position = null)
        }
        return objectData.copy(
                number = numberData.value as Int,
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
                .addGroup(createHorizontalComponents(layout, numberLabel, numberData))
                .addGroup(createHorizontalComponents(layout, nameLabel, nameData))
                .addComponent(lengthPanel)
                .addGroup(createHorizontalComponents(layout, noteLabel, noteData))
    }

    override fun getVerticalLayoutWithComponents(layout: GroupLayout, group: GroupLayout.Group): GroupLayout.Group {
        return group
                .addGroup(createVerticalComponents(layout, numberLabel, numberData))
                .addGap(VERTICAL_GAP_SIZE)
                .addGroup(createVerticalComponents(layout, nameLabel, nameData))
                .addGap(VERTICAL_GAP_SIZE)
                .addComponent(lengthPanel)
                .addGap(VERTICAL_GAP_SIZE)
                .addGroup(createVerticalComponents(layout, noteLabel, noteData))
    }

}
