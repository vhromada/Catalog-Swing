package cz.vhromada.catalog.gui.game

import cz.vhromada.catalog.entity.Game
import cz.vhromada.catalog.gui.common.AbstractInfoDialog
import cz.vhromada.catalog.gui.common.CatalogSwingConstants
import javax.swing.GroupLayout
import javax.swing.JCheckBox
import javax.swing.JLabel
import javax.swing.JSpinner
import javax.swing.JTextField
import javax.swing.SpinnerNumberModel

/**
 * A class represents dialog for game.
 *
 * @author Vladimir Hromada
 */
class GameInfoDialog : AbstractInfoDialog<Game> {

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
     * Check box for patch
     */
    private val patchData = JCheckBox("Patch")

    /**
     * Check box for trainer
     */
    private val trainerData = JCheckBox("Trainer")

    /**
     * Check box for data for trainer
     */
    private val trainerDataData = JCheckBox("Data for trainer")

    /**
     * Check box for editor
     */
    private val editorData = JCheckBox("Editor")

    /**
     * Check box for saves
     */
    private val savesData = JCheckBox("Saves")

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
     * Creates a new instance of GameInfoDialog.
     */
    constructor() {
        init()
    }

    /**
     * Creates a new instance of GameInfoDialog.
     *
     * @param game game
     */
    constructor(game: Game) : super(game) {
        init()
        this.nameData.text = game.name
        this.wikiCzData.text = game.wikiCz
        this.wikiEnData.text = game.wikiEn
        this.mediaCountData.value = game.mediaCount
        this.crackData.isSelected = game.crack!!
        this.serialData.isSelected = game.serialKey!!
        this.patchData.isSelected = game.patch!!
        this.trainerData.isSelected = game.trainer!!
        this.trainerDataData.isSelected = game.trainerData!!
        this.editorData.isSelected = game.editor!!
        this.savesData.isSelected = game.saves!!
        this.otherDataData.text = game.otherData
        this.noteData.text = game.note
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

    override fun processData(objectData: Game?): Game {
        if (objectData == null) {
            return Game(id = null,
                    name = nameData.text,
                    wikiCz = wikiCzData.text,
                    wikiEn = wikiEnData.text,
                    mediaCount = mediaCountData.value as Int,
                    crack = crackData.isSelected,
                    serialKey = serialData.isSelected,
                    patch = patchData.isSelected,
                    trainer = trainerData.isSelected,
                    trainerData = trainerDataData.isSelected,
                    editor = editorData.isSelected,
                    saves = savesData.isSelected,
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
                patch = patchData.isSelected,
                trainer = trainerData.isSelected,
                trainerData = trainerDataData.isSelected,
                editor = editorData.isSelected,
                saves = savesData.isSelected,
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
                .addComponent(patchData, HORIZONTAL_LONG_COMPONENT_SIZE, HORIZONTAL_LONG_COMPONENT_SIZE, HORIZONTAL_LONG_COMPONENT_SIZE)
                .addComponent(trainerData, HORIZONTAL_LONG_COMPONENT_SIZE, HORIZONTAL_LONG_COMPONENT_SIZE, HORIZONTAL_LONG_COMPONENT_SIZE)
                .addComponent(trainerDataData, HORIZONTAL_LONG_COMPONENT_SIZE, HORIZONTAL_LONG_COMPONENT_SIZE, HORIZONTAL_LONG_COMPONENT_SIZE)
                .addComponent(editorData, HORIZONTAL_LONG_COMPONENT_SIZE, HORIZONTAL_LONG_COMPONENT_SIZE, HORIZONTAL_LONG_COMPONENT_SIZE)
                .addComponent(savesData, HORIZONTAL_LONG_COMPONENT_SIZE, HORIZONTAL_LONG_COMPONENT_SIZE, HORIZONTAL_LONG_COMPONENT_SIZE)
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
                .addComponent(patchData, CatalogSwingConstants.VERTICAL_COMPONENT_SIZE, CatalogSwingConstants.VERTICAL_COMPONENT_SIZE, CatalogSwingConstants.VERTICAL_COMPONENT_SIZE)
                .addGap(VERTICAL_GAP_SIZE)
                .addComponent(trainerData, CatalogSwingConstants.VERTICAL_COMPONENT_SIZE, CatalogSwingConstants.VERTICAL_COMPONENT_SIZE, CatalogSwingConstants.VERTICAL_COMPONENT_SIZE)
                .addGap(VERTICAL_GAP_SIZE)
                .addComponent(trainerDataData, CatalogSwingConstants.VERTICAL_COMPONENT_SIZE, CatalogSwingConstants.VERTICAL_COMPONENT_SIZE, CatalogSwingConstants.VERTICAL_COMPONENT_SIZE)
                .addGap(VERTICAL_GAP_SIZE)
                .addComponent(editorData, CatalogSwingConstants.VERTICAL_COMPONENT_SIZE, CatalogSwingConstants.VERTICAL_COMPONENT_SIZE, CatalogSwingConstants.VERTICAL_COMPONENT_SIZE)
                .addGap(VERTICAL_GAP_SIZE)
                .addComponent(savesData, CatalogSwingConstants.VERTICAL_COMPONENT_SIZE, CatalogSwingConstants.VERTICAL_COMPONENT_SIZE, CatalogSwingConstants.VERTICAL_COMPONENT_SIZE)
                .addGap(VERTICAL_GAP_SIZE)
                .addGroup(createVerticalComponents(layout, otherDataLabel, otherDataData))
                .addGap(VERTICAL_GAP_SIZE)
                .addGroup(createVerticalComponents(layout, noteLabel, noteData))
    }

}
