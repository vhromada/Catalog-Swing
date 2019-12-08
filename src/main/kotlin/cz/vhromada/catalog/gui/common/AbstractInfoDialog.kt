package cz.vhromada.catalog.gui.common

import cz.vhromada.catalog.entity.Genre
import cz.vhromada.catalog.facade.GenreFacade
import cz.vhromada.catalog.facade.PictureFacade
import cz.vhromada.catalog.gui.genre.GenreChooseDialog
import cz.vhromada.catalog.gui.picture.PictureChooseDialog
import cz.vhromada.common.Language
import java.awt.EventQueue
import javax.swing.ButtonModel
import javax.swing.GroupLayout
import javax.swing.JButton
import javax.swing.JCheckBox
import javax.swing.JComponent
import javax.swing.JDialog
import javax.swing.JFrame
import javax.swing.JLabel
import javax.swing.JRadioButton
import javax.swing.JSpinner
import javax.swing.WindowConstants
import javax.swing.text.JTextComponent

/**
 * An abstract class represents dialog for adding or updating data.
 *
 * @param <T> type of data
 * @author Vladimir Hromada
 */
abstract class AbstractInfoDialog<T> : JDialog {

    /**
     * Return status
     */
    var returnStatus = DialogResult.CANCEL
        private set

    /**
     * Data
     */
    var data: T? = null
        private set

    /**
     * Button OK
     */
    private val okButton = JButton("OK", Picture.OK.icon)

    /**
     * Button Cancel
     */
    private val cancelButton = JButton("Cancel", Picture.CANCEL.icon)

    /**
     * Creates a new instance of AbstractInfoDialog.
     */
    constructor() : this("Add", Picture.ADD) {
        okButton.isEnabled = false
    }

    /**
     * Creates a new instance of AbstractInfoDialog.
     *
     * @param data data
     * @throws IllegalArgumentException if data are null
     */
    constructor(data: T) : this("Update", Picture.UPDATE) {
        this.data = data
    }

    /**
     * Creates a new instance of AbstractInfoDialog.
     *
     * @param name    name
     * @param picture picture
     */
    private constructor(name: String, picture: Picture) : super(JFrame(), name, true) {
        initDialog(picture)
        okButton.addActionListener { okAction() }
        cancelButton.addActionListener { cancelAction() }
    }

    /**
     * Initializes components.
     */
    protected abstract fun initComponents()

    /**
     * Returns object with filled data.
     *
     * @param objectData object for filling data
     * @return object with filled data
     */
    protected abstract fun processData(objectData: T?): T?

    /**
     * Returns horizontal layout with added components.
     *
     * @param layout horizontal layout
     * @param group  group in vertical layout
     * @return horizontal layout with added components
     */
    protected abstract fun getHorizontalLayoutWithComponents(layout: GroupLayout, group: GroupLayout.Group): GroupLayout.Group

    /**
     * Returns vertical layout with added components.
     *
     * @param layout vertical layout
     * @param group  group in vertical layout
     * @return vertical layout with added components
     */
    protected abstract fun getVerticalLayoutWithComponents(layout: GroupLayout, group: GroupLayout.Group): GroupLayout.Group

    /**
     * Initializes.
     */
    protected fun init() {
        initComponents()
        createLayout()
    }

    /**
     * Initializes label with component.
     *
     * @param label     label
     * @param component component
     */
    protected fun initLabelComponent(label: JLabel, component: JComponent) {
        label.labelFor = component
        label.isFocusable = false
    }

    /**
     * Adds validator to input.
     *
     * @param input input
     */
    protected fun addInputValidator(input: JTextComponent) {
        input.document.addDocumentListener(object : InputValidator(okButton) {

            override val isInputValid: Boolean
                get() = this@AbstractInfoDialog.isInputValid()

        })
    }

    /**
     * Adds validator to spinner.
     *
     * @param spinner spinner
     */
    protected fun addSpinnerValidator(spinner: JSpinner) {
        spinner.addChangeListener { okButton.isEnabled = isInputValid() }
    }

    /**
     * Returns true if input is valid.
     *
     * @return true if input is valid
     */
    protected open fun isInputValid(): Boolean {
        return true
    }

    /**
     * Sets enabled to button OK.
     *
     * @param enabled true if button should be enabled
     */
    protected fun setOkButtonEnabled(enabled: Boolean) {
        okButton.isEnabled = enabled
    }

    /**
     * Returns genres.
     *
     * @param genres list of genres
     * @return genres
     */
    @Suppress("DuplicatedCode")
    protected fun getGenres(genres: List<Genre>): String {
        if (genres.isEmpty()) {
            return ""
        }

        val genresString = StringBuilder()
        for (genre in genres) {
            genresString.append(genre.name)
            genresString.append(", ")
        }
        return genresString.substring(0, genresString.length - 2)
    }


    /**
     * Returns picture.
     *
     * @param pictures list of pictures
     * @return picture
     */
    protected fun getPicture(pictures: List<Int>): String {
        return if (pictures.isEmpty()) "" else pictures[0].toString()
    }

    /**
     * Performs action for button Genres.
     *
     * @param genreFacade facade for genres
     * @param genres      list of genres
     * @param genreData   data with genres
     */
    protected fun genresAction(genreFacade: GenreFacade, genres: MutableList<Genre>, genreData: JLabel) {
        EventQueue.invokeLater {
            val dialog = GenreChooseDialog(genreFacade, genres)
            dialog.isVisible = true
            if (dialog.returnStatus === DialogResult.OK) {
                genreData.text = getGenres(genres)
                setOkButtonEnabled(isInputValid())
            }
        }
    }

    /**
     * Performs action for button Change pictures.
     *
     * @param pictureFacade facade for pictures
     * @param pictures      list of pictures
     * @param pictureData   data with genres
     */
    protected fun pictureAction(pictureFacade: PictureFacade, pictures: MutableList<Int>, pictureData: JLabel) {
        EventQueue.invokeLater {
            val pictureEntity = cz.vhromada.catalog.entity.Picture(id = if (pictures.isEmpty()) null else pictures[0], content = null, position = null)
            val dialog = PictureChooseDialog(pictureFacade, pictureEntity)
            dialog.isVisible = true
            if (dialog.returnStatus === DialogResult.OK) {
                pictures.clear()
                pictures.add(dialog.picture.id!!)
                pictureData.text = getPicture(pictures)
                setOkButtonEnabled(isInputValid())
            }
        }
    }

    /**
     * Initializes language.
     *
     * @param language             language
     * @param czechLanguageData    radio button for czech language
     * @param englishLanguageData  radio button for english language
     * @param frenchLanguageData   radio button for french language
     * @param japaneseLanguageData radio button for japanese language
     * @param slovakLanguageData   radio button for slovak language
     */
    protected fun initLanguage(language: Language,
                               czechLanguageData: JRadioButton,
                               englishLanguageData: JRadioButton,
                               frenchLanguageData: JRadioButton,
                               japaneseLanguageData: JRadioButton,
                               slovakLanguageData: JRadioButton) {
        when (language) {
            Language.CZ -> czechLanguageData.isSelected = true
            Language.EN -> englishLanguageData.isSelected = true
            Language.FR -> frenchLanguageData.isSelected = true
            Language.JP -> japaneseLanguageData.isSelected = true
            Language.SK -> slovakLanguageData.isSelected = true
        }
    }

    /**
     * Returns selected language.
     *
     * @param model                button model
     * @param czechLanguageData    radio button for czech language
     * @param englishLanguageData  radio button for english language
     * @param frenchLanguageData   radio button for french language
     * @param japaneseLanguageData radio button for japanese language
     * @return selected language
     */
    protected fun getSelectedLanguage(model: ButtonModel,
                                      czechLanguageData: JRadioButton,
                                      englishLanguageData: JRadioButton,
                                      frenchLanguageData: JRadioButton,
                                      japaneseLanguageData: JRadioButton): Language {
        return when (model) {
            czechLanguageData.model -> Language.CZ
            englishLanguageData.model -> Language.EN
            frenchLanguageData.model -> Language.FR
            japaneseLanguageData.model -> Language.JP
            else -> Language.SK
        }
    }

    /**
     * Initializes subtitles.
     *
     * @param subtitles            list of subtitles
     * @param czechSubtitlesData   check box for czech subtitles
     * @param englishSubtitlesData check box for english subtitles
     */
    protected fun initSubtitles(subtitles: List<Language?>, czechSubtitlesData: JCheckBox, englishSubtitlesData: JCheckBox) {
        for (language in subtitles.filterNotNull()) {
            when (language) {
                Language.CZ -> czechSubtitlesData.isSelected = true
                Language.EN -> englishSubtitlesData.isSelected = true
                else -> throw IndexOutOfBoundsException("Bad subtitles")
            }
        }
    }

    /**
     * Returns selected subtitles.
     *
     * @param czechSubtitlesData   check box for czech subtitles
     * @param englishSubtitlesData check box for english subtitles
     * @return selected subtitles
     */
    protected fun getSelectedSubtitles(czechSubtitlesData: JCheckBox, englishSubtitlesData: JCheckBox): List<Language> {
        val subtitles = mutableListOf<Language>()
        if (czechSubtitlesData.isSelected) {
            subtitles.add(Language.CZ)
        }
        if (englishSubtitlesData.isSelected) {
            subtitles.add(Language.EN)
        }
        return subtitles
    }

    /**
     * Creates layout.
     */
    protected fun createLayout() {
        val layout = GroupLayout(getRootPane())
        getRootPane().layout = layout
        layout.setHorizontalGroup(createHorizontalLayout(layout))
        layout.setVerticalGroup(createVerticalLayout(layout))

        pack()
        setLocationRelativeTo(getRootPane())
    }

    /**
     * Returns horizontal layout for label component with data component.
     *
     * @param layout         layout
     * @param labelComponent label component
     * @param dataComponent  data component
     * @return horizontal layout for label component with data component
     */
    protected fun createHorizontalComponents(layout: GroupLayout, labelComponent: JComponent, dataComponent: JComponent): GroupLayout.Group {
        return layout.createSequentialGroup()
                .addComponent(labelComponent, HORIZONTAL_LABEL_DIALOG_SIZE, HORIZONTAL_LABEL_DIALOG_SIZE, HORIZONTAL_LABEL_DIALOG_SIZE)
                .addGap(HORIZONTAL_DATA_GAP_SIZE)
                .addComponent(dataComponent, HORIZONTAL_DATA_DIALOG_SIZE, HORIZONTAL_DATA_DIALOG_SIZE, HORIZONTAL_DATA_DIALOG_SIZE)
    }

    /**
     * Returns horizontal layout for selectable component.
     *
     * @param layout    layout
     * @param component component
     * @return horizontal layout for selectable component
     */
    protected fun createHorizontalSelectableComponent(layout: GroupLayout, component: JComponent): GroupLayout.Group {
        return layout.createSequentialGroup()
                .addGap(HORIZONTAL_SELECTABLE_COMPONENT_GAP_SIZE)
                .addComponent(component, HORIZONTAL_DATA_DIALOG_SIZE, HORIZONTAL_DATA_DIALOG_SIZE, HORIZONTAL_DATA_DIALOG_SIZE)
    }

    /**
     * Returns vertical layout for label component with data component.
     *
     * @param layout         layout
     * @param labelComponent label component
     * @param dataComponent  data component
     * @return vertical layout for label component with data component
     */
    protected fun createVerticalComponents(layout: GroupLayout, labelComponent: JComponent, dataComponent: JComponent): GroupLayout.Group {
        return layout.createParallelGroup()
                .addComponent(labelComponent, CatalogSwingConstants.VERTICAL_COMPONENT_SIZE, CatalogSwingConstants.VERTICAL_COMPONENT_SIZE, CatalogSwingConstants.VERTICAL_COMPONENT_SIZE)
                .addComponent(dataComponent, CatalogSwingConstants.VERTICAL_COMPONENT_SIZE, CatalogSwingConstants.VERTICAL_COMPONENT_SIZE, CatalogSwingConstants.VERTICAL_COMPONENT_SIZE)
    }

    /**
     * Initializes dialog.
     *
     * @param picture
     */
    private fun initDialog(picture: Picture) {
        defaultCloseOperation = WindowConstants.DISPOSE_ON_CLOSE
        isResizable = false
        setIconImage(picture.icon.image)
    }

    /**
     * Performs action for button OK.
     */
    private fun okAction() {
        returnStatus = DialogResult.OK
        data = processData(data)
        close()
    }

    /**
     * Performs action for button Cancel.
     */
    private fun cancelAction() {
        returnStatus = DialogResult.CANCEL
        data = null
        close()
    }

    /**
     * Closes dialog.
     */
    private fun close() {
        isVisible = false
        dispose()
    }

    /**
     * Returns horizontal layout of components.
     *
     * @param layout layout
     * @return horizontal layout of components
     */
    private fun createHorizontalLayout(layout: GroupLayout): GroupLayout.Group {
        val buttons = layout.createSequentialGroup()
                .addGap(HORIZONTAL_BUTTON_GAP_SIZE)
                .addComponent(okButton, HORIZONTAL_BUTTON_SIZE, HORIZONTAL_BUTTON_SIZE, HORIZONTAL_BUTTON_SIZE)
                .addGap(HORIZONTAL_BUTTONS_GAP_SIZE)
                .addComponent(cancelButton, HORIZONTAL_BUTTON_SIZE, HORIZONTAL_BUTTON_SIZE, HORIZONTAL_BUTTON_SIZE)
                .addGap(HORIZONTAL_BUTTON_GAP_SIZE)

        val components = getHorizontalLayoutWithComponents(layout, layout.createParallelGroup())
                .addGroup(buttons)

        return layout.createSequentialGroup()
                .addGap(HORIZONTAL_GAP_SIZE)
                .addGroup(components)
                .addGap(HORIZONTAL_GAP_SIZE)
    }

    /**
     * Returns vertical layout of components.
     *
     * @param layout layout
     * @return vertical layout of components
     */
    private fun createVerticalLayout(layout: GroupLayout): GroupLayout.Group {
        val buttons = layout.createParallelGroup()
                .addComponent(okButton, CatalogSwingConstants.VERTICAL_BUTTON_SIZE, CatalogSwingConstants.VERTICAL_BUTTON_SIZE, CatalogSwingConstants.VERTICAL_BUTTON_SIZE)
                .addComponent(cancelButton, CatalogSwingConstants.VERTICAL_BUTTON_SIZE, CatalogSwingConstants.VERTICAL_BUTTON_SIZE, CatalogSwingConstants.VERTICAL_BUTTON_SIZE)

        val components = layout.createSequentialGroup()
                .addGap(VERTICAL_LONG_GAP_SIZE)

        return getVerticalLayoutWithComponents(layout, components)
                .addGap(VERTICAL_LONG_GAP_SIZE)
                .addGroup(buttons)
                .addGap(VERTICAL_LONG_GAP_SIZE)
    }

    companion object {

        /**
         * Horizontal long component size
         */
        const val HORIZONTAL_LONG_COMPONENT_SIZE = 310

        /**
         * Vertical gap size
         */
        const val VERTICAL_GAP_SIZE = 10

        /**
         * SerialVersionUID
         */
        private const val serialVersionUID = 1L

        /**
         * Horizontal label size
         */
        private const val HORIZONTAL_LABEL_DIALOG_SIZE = 100

        /**
         * Horizontal data size
         */
        private const val HORIZONTAL_DATA_DIALOG_SIZE = 200

        /**
         * Horizontal button size
         */
        private const val HORIZONTAL_BUTTON_SIZE = 96

        /**
         * Horizontal size of gap between label and data
         */
        private const val HORIZONTAL_DATA_GAP_SIZE = 10

        /**
         * Horizontal selectable component gap size
         */
        private const val HORIZONTAL_SELECTABLE_COMPONENT_GAP_SIZE = 110

        /**
         * Horizontal button gap size
         */
        private const val HORIZONTAL_BUTTON_GAP_SIZE = 32

        /**
         * Horizontal size of gap between button
         */
        private const val HORIZONTAL_BUTTONS_GAP_SIZE = 54

        /**
         * Horizontal gap size
         */
        private const val HORIZONTAL_GAP_SIZE = 20

        /**
         * Vertical long gap size
         */
        private const val VERTICAL_LONG_GAP_SIZE = 20

    }

}
