package cz.vhromada.catalog.gui.program

import cz.vhromada.catalog.entity.Program
import cz.vhromada.catalog.gui.common.AbstractInfoDialog
import cz.vhromada.catalog.gui.common.CatalogSwingConstants
import javax.swing.GroupLayout
import javax.swing.JCheckBox
import javax.swing.JLabel
import javax.swing.JSpinner
import javax.swing.JTextField
import javax.swing.SpinnerNumberModel

/**
 * A class represents dialog for program.
 *
 * @author Vladimir Hromada
 */
class ProgramInfoDialog : AbstractInfoDialog<Program> {

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
     * Check box for crack
     */
    private val crackData = JCheckBox("Crack")

    /**
     * Check box for serial key
     */
    private val serialData = JCheckBox("Serial key")

    /**
     * Label for other data
     */
    private val otherDataLabel = JLabel("Other data")

    /**
     * Text field for other data
     */
    private val otherDataData = JTextField()

    /**
     * Label for note
     */
    private val noteLabel = JLabel("Note")

    /**
     * Text field for note
     */
    private val noteData = JTextField()

    /**
     * Creates a new instance of ProgramInfoDialog.
     */
    constructor() {
        init()
    }

    /**
     * Creates a new instance of ProgramInfoDialog.
     *
     * @param program program
     */
    constructor(program: Program) : super(program) {
        init()
        this.nameData.text = program.name
        this.wikiCzData.text = program.wikiCz
        this.wikiEnData.text = program.wikiEn
        this.mediaCountData.value = program.mediaCount
        this.crackData.isSelected = program.crack!!
        this.serialData.isSelected = program.serialKey!!
        this.otherDataData.text = program.otherData
        this.noteData.text = program.note
    }

    override fun initComponents() {
        initLabelComponent(nameLabel, nameData)
        initLabelComponent(wikiCzLabel, wikiCzData)
        initLabelComponent(wikiEnLabel, wikiEnData)
        initLabelComponent(mediaCountLabel, mediaCountData)
        initLabelComponent(otherDataLabel, otherDataData)
        initLabelComponent(noteLabel, noteData)

        addInputValidator(nameData)
    }

    override fun processData(objectData: Program?): Program {
        if (objectData == null) {
            return Program(id = null,
                    name = nameData.text,
                    wikiCz = wikiCzData.text,
                    wikiEn = wikiEnData.text,
                    mediaCount = mediaCountData.value as Int,
                    crack = crackData.isSelected,
                    serialKey = serialData.isSelected,
                    otherData = otherDataData.text,
                    note = noteData.text,
                    position = null)
        }
        return objectData.copy(name = nameData.text,
                wikiCz = wikiCzData.text,
                wikiEn = wikiEnData.text,
                mediaCount = mediaCountData.value as Int,
                crack = crackData.isSelected,
                serialKey = serialData.isSelected,
                otherData = otherDataData.text,
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
                .addComponent(crackData, HORIZONTAL_LONG_COMPONENT_SIZE, HORIZONTAL_LONG_COMPONENT_SIZE, HORIZONTAL_LONG_COMPONENT_SIZE)
                .addComponent(serialData, HORIZONTAL_LONG_COMPONENT_SIZE, HORIZONTAL_LONG_COMPONENT_SIZE, HORIZONTAL_LONG_COMPONENT_SIZE)
                .addGroup(createHorizontalComponents(layout, otherDataLabel, otherDataData))
                .addGroup(createHorizontalComponents(layout, noteLabel, noteData))
    }

    @Suppress("DuplicatedCode")
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
                .addComponent(crackData, CatalogSwingConstants.VERTICAL_COMPONENT_SIZE, CatalogSwingConstants.VERTICAL_COMPONENT_SIZE, CatalogSwingConstants.VERTICAL_COMPONENT_SIZE)
                .addGap(VERTICAL_GAP_SIZE)
                .addComponent(serialData, CatalogSwingConstants.VERTICAL_COMPONENT_SIZE, CatalogSwingConstants.VERTICAL_COMPONENT_SIZE, CatalogSwingConstants.VERTICAL_COMPONENT_SIZE)
                .addGap(VERTICAL_GAP_SIZE)
                .addGroup(createVerticalComponents(layout, otherDataLabel, otherDataData))
                .addGap(VERTICAL_GAP_SIZE)
                .addGroup(createVerticalComponents(layout, noteLabel, noteData))
    }

}
