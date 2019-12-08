package cz.vhromada.catalog.gui.season

import cz.vhromada.catalog.entity.Season
import cz.vhromada.catalog.gui.common.AbstractInfoDialog
import cz.vhromada.catalog.gui.common.CatalogSwingConstants
import cz.vhromada.common.utils.Constants
import javax.swing.ButtonGroup
import javax.swing.GroupLayout
import javax.swing.JCheckBox
import javax.swing.JLabel
import javax.swing.JRadioButton
import javax.swing.JSpinner
import javax.swing.JTextField
import javax.swing.SpinnerNumberModel

/**
 * A class represents dialog for season.
 *
 * @author Vladimir Hromada
 */
class SeasonInfoDialog : AbstractInfoDialog<Season> {

    /**
     * Label for season's number
     */
    private val numberLabel = JLabel("Number of season")

    /**
     * Spinner for season's number
     */
    private val numberData = JSpinner(SpinnerNumberModel(1, 1, Integer.MAX_VALUE, 1))

    /**
     * Label for starting year
     */
    private val startYearLabel = JLabel("Starting year")

    /**
     * Spinner for starting year
     */
    private val startYearData = JSpinner(SpinnerNumberModel(Constants.CURRENT_YEAR, Constants.MIN_YEAR, Constants.CURRENT_YEAR, 1))

    /**
     * Label for ending year
     */
    private val endYearLabel = JLabel("Ending year")

    /**
     * Spinner for ending year
     */
    private val endYearData = JSpinner(SpinnerNumberModel(Constants.CURRENT_YEAR, Constants.MIN_YEAR, Constants.CURRENT_YEAR, 1))

    /**
     * Label for language
     */
    private val languageLabel = JLabel("Language")

    /**
     * Button group for languages
     */
    private val languagesButtonGroup = ButtonGroup()

    /**
     * Radio button for czech language
     */
    private val czechLanguageData = JRadioButton("Czech", true)

    /**
     * Radio button for english language
     */
    private val englishLanguageData = JRadioButton("English")

    /**
     * Radio button for french language
     */
    private val frenchLanguageData = JRadioButton("French")

    /**
     * Radio button for japanese language
     */
    private val japaneseLanguageData = JRadioButton("Japanese")

    /**
     * Radio button for slovak language
     */
    private val slovakLanguageData = JRadioButton("Slovak")

    /**
     * Label for subtitles
     */
    private val subtitlesLabel = JLabel("Subtitles")

    /**
     * Check box for czech subtitles
     */
    private val czechSubtitlesData = JCheckBox("Czech")

    /**
     * Check box for english subtitles
     */
    private val englishSubtitlesData = JCheckBox("English")

    /**
     * Label for note
     */
    private val noteLabel = JLabel("Note")

    /**
     * Text field for note
     */
    private val noteData = JTextField()

    /**
     * Creates a new instance of SeasonInfoDialog.
     */
    constructor() {
        init()

        setOkButtonEnabled(true)
    }

    /**
     * Creates a new instance of SeasonInfoDialog.
     *
     * @param season season
     * @throws IllegalArgumentException if season is null
     */
    constructor(season: Season) : super(season) {
        init()
        this.numberData.value = season.number
        this.startYearData.value = season.startYear
        this.endYearData.value = season.endYear
        initLanguage(season.language!!, this.czechLanguageData, this.englishLanguageData, this.frenchLanguageData, this.japaneseLanguageData, this.slovakLanguageData)
        initSubtitles(season.subtitles!!, this.czechSubtitlesData, this.englishSubtitlesData)
        this.noteData.text = season.note
    }

    override fun initComponents() {
        initLabelComponent(numberLabel, numberData)
        initLabelComponent(startYearLabel, startYearData)
        initLabelComponent(endYearLabel, endYearData)
        initLabelComponent(noteLabel, noteData)

        languagesButtonGroup.add(czechLanguageData)
        languagesButtonGroup.add(englishLanguageData)
        languagesButtonGroup.add(frenchLanguageData)
        languagesButtonGroup.add(japaneseLanguageData)
        languagesButtonGroup.add(slovakLanguageData)

        languageLabel.isFocusable = false
        subtitlesLabel.isFocusable = false

        addSpinnerValidator(startYearData)
        addSpinnerValidator(endYearData)
    }

    override fun processData(objectData: Season?): Season {
        if (objectData == null) {
            return Season(id = null,
                    number = numberData.value as Int,
                    startYear = startYearData.value as Int,
                    endYear = endYearData.value as Int,
                    language = getSelectedLanguage(languagesButtonGroup.selection, czechLanguageData, englishLanguageData, frenchLanguageData, japaneseLanguageData),
                    subtitles = getSelectedSubtitles(czechSubtitlesData, englishSubtitlesData),
                    note = noteData.text,
                    position = null)
        }
        return objectData.copy(number = numberData.value as Int,
                startYear = startYearData.value as Int,
                endYear = endYearData.value as Int,
                language = getSelectedLanguage(languagesButtonGroup.selection, czechLanguageData, englishLanguageData, frenchLanguageData, japaneseLanguageData),
                subtitles = getSelectedSubtitles(czechSubtitlesData, englishSubtitlesData),
                note = noteData.text)
    }

    /**
     * Returns true if input is valid: starting year isn't greater than ending year.
     *
     * @return true if input is valid: starting year isn't greater than ending year
     */
    override fun isInputValid(): Boolean {
        return startYearData.value as Int <= endYearData.value as Int
    }

    @Suppress("DuplicatedCode")
    override fun getHorizontalLayoutWithComponents(layout: GroupLayout, group: GroupLayout.Group): GroupLayout.Group {
        return group
                .addGroup(createHorizontalComponents(layout, numberLabel, numberData))
                .addGroup(createHorizontalComponents(layout, startYearLabel, startYearData))
                .addGroup(createHorizontalComponents(layout, endYearLabel, endYearData))
                .addGroup(createHorizontalComponents(layout, languageLabel, czechLanguageData))
                .addGroup(createHorizontalSelectableComponent(layout, englishLanguageData))
                .addGroup(createHorizontalSelectableComponent(layout, frenchLanguageData))
                .addGroup(createHorizontalSelectableComponent(layout, japaneseLanguageData))
                .addGroup(createHorizontalSelectableComponent(layout, slovakLanguageData))
                .addGroup(createHorizontalComponents(layout, subtitlesLabel, czechSubtitlesData))
                .addGroup(createHorizontalSelectableComponent(layout, englishSubtitlesData))
                .addGroup(createHorizontalComponents(layout, noteLabel, noteData))
    }

    @Suppress("DuplicatedCode")
    override fun getVerticalLayoutWithComponents(layout: GroupLayout, group: GroupLayout.Group): GroupLayout.Group {
        return group
                .addGroup(createVerticalComponents(layout, numberLabel, numberData))
                .addGap(VERTICAL_GAP_SIZE)
                .addGroup(createVerticalComponents(layout, startYearLabel, startYearData))
                .addGap(VERTICAL_GAP_SIZE)
                .addGroup(createVerticalComponents(layout, endYearLabel, endYearData))
                .addGap(VERTICAL_GAP_SIZE)
                .addGroup(createVerticalComponents(layout, languageLabel, czechLanguageData))
                .addGap(VERTICAL_GAP_SIZE)
                .addComponent(englishLanguageData, CatalogSwingConstants.VERTICAL_COMPONENT_SIZE, CatalogSwingConstants.VERTICAL_COMPONENT_SIZE, CatalogSwingConstants.VERTICAL_COMPONENT_SIZE)
                .addGap(VERTICAL_GAP_SIZE)
                .addComponent(frenchLanguageData, CatalogSwingConstants.VERTICAL_COMPONENT_SIZE, CatalogSwingConstants.VERTICAL_COMPONENT_SIZE, CatalogSwingConstants.VERTICAL_COMPONENT_SIZE)
                .addGap(VERTICAL_GAP_SIZE)
                .addComponent(japaneseLanguageData, CatalogSwingConstants.VERTICAL_COMPONENT_SIZE, CatalogSwingConstants.VERTICAL_COMPONENT_SIZE, CatalogSwingConstants.VERTICAL_COMPONENT_SIZE)
                .addGap(VERTICAL_GAP_SIZE)
                .addComponent(slovakLanguageData, CatalogSwingConstants.VERTICAL_COMPONENT_SIZE, CatalogSwingConstants.VERTICAL_COMPONENT_SIZE, CatalogSwingConstants.VERTICAL_COMPONENT_SIZE)
                .addGap(VERTICAL_GAP_SIZE)
                .addGroup(createVerticalComponents(layout, subtitlesLabel, czechSubtitlesData))
                .addGap(VERTICAL_GAP_SIZE)
                .addComponent(englishSubtitlesData, CatalogSwingConstants.VERTICAL_COMPONENT_SIZE, CatalogSwingConstants.VERTICAL_COMPONENT_SIZE, CatalogSwingConstants.VERTICAL_COMPONENT_SIZE)
                .addGap(VERTICAL_GAP_SIZE)
                .addGroup(createVerticalComponents(layout, noteLabel, noteData))
    }

}
