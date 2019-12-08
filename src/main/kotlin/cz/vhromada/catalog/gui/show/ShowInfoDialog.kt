package cz.vhromada.catalog.gui.show

import cz.vhromada.catalog.entity.Genre
import cz.vhromada.catalog.entity.Show
import cz.vhromada.catalog.facade.GenreFacade
import cz.vhromada.catalog.facade.PictureFacade
import cz.vhromada.catalog.gui.common.AbstractInfoDialog
import cz.vhromada.catalog.gui.common.CatalogSwingConstants
import cz.vhromada.catalog.gui.common.Picture
import cz.vhromada.common.utils.Constants
import javax.swing.GroupLayout
import javax.swing.JButton
import javax.swing.JCheckBox
import javax.swing.JLabel
import javax.swing.JSpinner
import javax.swing.JTextField
import javax.swing.SpinnerNumberModel

/**
 * A class represents dialog for show.
 *
 * @author Vladimir Hromada
 */
class ShowInfoDialog : AbstractInfoDialog<Show> {

    /**
     * Facade for genres
     */
    private val genreFacade: GenreFacade

    /**
     * Facade for pictures
     */
    private val pictureFacade: PictureFacade

    /**
     * List of genres
     */
    private val genres: MutableList<Genre>

    /**
     * List of pictures
     */
    private val pictures: MutableList<Int>

    /**
     * Label for czech name
     */
    private val czechNameLabel = JLabel("Czech name")

    /**
     * Text field for czech name
     */
    private val czechNameData = JTextField()

    /**
     * Label for original name
     */
    private val originalNameLabel = JLabel("Original name")

    /**
     * Text field for original name
     */
    private val originalNameData = JTextField()

    /**
     * Label for ČSFD
     */
    private val csfdLabel = JLabel("ČSFD")

    /**
     * Text field for ČSFD
     */
    private val csfdData = JTextField()

    /**
     * Check box for IMDB code
     */
    private val imdbCodeLabel = JCheckBox("IMDB code")

    /**
     * Spinner for IMDB code
     */
    private val imdbCodeData = JSpinner(SpinnerNumberModel(1, 1, Constants.MAX_IMDB_CODE, 1))

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
     * Label for picture
     */
    private val pictureLabel = JLabel("Picture")

    /**
     * Data with picture
     */
    private val pictureData = JLabel()

    /**
     * Button for changing picture
     */
    private val pictureButton = JButton("Change picture", Picture.CHOOSE.icon)

    /**
     * Label for note
     */
    private val noteLabel = JLabel("Note")

    /**
     * Text field for note
     */
    private val noteData = JTextField()

    /**
     * Label for genres
     */
    private val genreLabel = JLabel("Genres")

    /**
     * Data with genres
     */
    private val genreData = JLabel()

    /**
     * Button for genres
     */
    private val genresButton = JButton("Change genres", Picture.CHOOSE.icon)

    /**
     * Creates a new instance of ShowInfoDialog.
     *
     * @param genreFacade   facade for genres
     * @param pictureFacade facade for pictures
     */
    constructor(genreFacade: GenreFacade, pictureFacade: PictureFacade) {
        init()
        this.genreFacade = genreFacade
        this.pictureFacade = pictureFacade
        this.genres = mutableListOf()
        this.pictures = mutableListOf()
        imdbCodeLabel.isSelected = false
        imdbCodeData.isEnabled = false
    }

    /**
     * Creates a new instance of ShowInfoDialog.
     *
     * @param genreFacade   facade for genres
     * @param pictureFacade facade for pictures
     * @param show          show
     */
    constructor(genreFacade: GenreFacade, pictureFacade: PictureFacade, show: Show) : super(show) {
        init()
        this.genreFacade = genreFacade
        this.pictureFacade = pictureFacade
        this.genres = show.genres!!.filterNotNull().toMutableList()
        this.pictures = mutableListOf()
        if (show.picture != null) {
            this.pictures.add(show.picture!!)
        }
        this.czechNameData.text = show.czechName
        this.originalNameData.text = show.originalName
        this.csfdData.text = show.csfd
        if (show.imdbCode!! > 0) {
            this.imdbCodeLabel.isSelected = true
            this.imdbCodeData.value = show.imdbCode
        } else {
            this.imdbCodeLabel.isSelected = false
            this.imdbCodeData.isEnabled = false
        }
        this.wikiCzData.text = show.wikiCz
        this.wikiEnData.text = show.wikiEn
        this.pictureData.text = getPicture(this.pictures)
        this.noteData.text = show.note
        this.genreData.text = getGenres(this.genres)
    }

    @Suppress("DuplicatedCode")
    override fun initComponents() {
        initLabelComponent(czechNameLabel, czechNameData)
        initLabelComponent(originalNameLabel, originalNameData)
        initLabelComponent(csfdLabel, csfdData)
        initLabelComponent(wikiCzLabel, wikiCzData)
        initLabelComponent(wikiEnLabel, wikiEnData)
        initLabelComponent(noteLabel, noteData)

        addInputValidator(czechNameData)
        addInputValidator(originalNameData)

        pictureData.isFocusable = false
        genreData.isFocusable = false

        imdbCodeLabel.addChangeListener { imdbCodeData.isEnabled = imdbCodeLabel.isSelected }

        pictureButton.addActionListener { pictureAction(pictureFacade, pictures, pictureData) }

        genresButton.addActionListener { genresAction(genreFacade, genres, genreData) }
    }

    override fun processData(objectData: Show?): Show {
        if (objectData == null) {
            return Show(id = null,
                    czechName = czechNameData.text,
                    originalName = originalNameData.text,
                    csfd = csfdData.text,
                    imdbCode = if (imdbCodeLabel.isSelected) imdbCodeData.value as Int else -1,
                    wikiEn = wikiEnData.text,
                    wikiCz = wikiCzData.text,
                    picture = if (pictures.isEmpty()) null else pictures[0],
                    note = noteData.text,
                    position = null,
                    genres = genres)
        }
        return objectData.copy(czechName = czechNameData.text,
                originalName = originalNameData.text,
                csfd = csfdData.text,
                imdbCode = if (imdbCodeLabel.isSelected) imdbCodeData.value as Int else -1,
                wikiEn = wikiEnData.text,
                wikiCz = wikiCzData.text,
                picture = if (pictures.isEmpty()) null else pictures[0],
                note = noteData.text,
                genres = genres)
    }

    /**
     * Returns true if input is valid: czech name isn't empty string, original name isn't empty string, genres isn't empty collection.
     *
     * @return true if input is valid: czech name isn't empty string, original name isn't empty string, genres isn't empty collection
     */
    override fun isInputValid(): Boolean {
        return czechNameData.text.isNotBlank() && originalNameData.text.isNotBlank() && genres.isNotEmpty()
    }

    override fun getHorizontalLayoutWithComponents(layout: GroupLayout, group: GroupLayout.Group): GroupLayout.Group {
        return group
                .addGroup(createHorizontalComponents(layout, czechNameLabel, czechNameData))
                .addGroup(createHorizontalComponents(layout, originalNameLabel, originalNameData))
                .addGroup(createHorizontalComponents(layout, csfdLabel, csfdData))
                .addGroup(createHorizontalComponents(layout, imdbCodeLabel, imdbCodeData))
                .addGroup(createHorizontalComponents(layout, wikiCzLabel, wikiCzData))
                .addGroup(createHorizontalComponents(layout, wikiEnLabel, wikiEnData))
                .addGroup(createHorizontalComponents(layout, pictureLabel, pictureData))
                .addComponent(pictureButton, HORIZONTAL_LONG_COMPONENT_SIZE, HORIZONTAL_LONG_COMPONENT_SIZE, HORIZONTAL_LONG_COMPONENT_SIZE)
                .addGroup(createHorizontalComponents(layout, noteLabel, noteData))
                .addGroup(createHorizontalComponents(layout, genreLabel, genreData))
                .addComponent(genresButton, HORIZONTAL_LONG_COMPONENT_SIZE, HORIZONTAL_LONG_COMPONENT_SIZE, HORIZONTAL_LONG_COMPONENT_SIZE)
    }

    @Suppress("DuplicatedCode")
    override fun getVerticalLayoutWithComponents(layout: GroupLayout, group: GroupLayout.Group): GroupLayout.Group {
        return group
                .addGroup(createVerticalComponents(layout, czechNameLabel, czechNameData))
                .addGap(VERTICAL_GAP_SIZE)
                .addGroup(createVerticalComponents(layout, originalNameLabel, originalNameData))
                .addGap(VERTICAL_GAP_SIZE)
                .addGroup(createVerticalComponents(layout, csfdLabel, csfdData))
                .addGap(VERTICAL_GAP_SIZE)
                .addGroup(createVerticalComponents(layout, imdbCodeLabel, imdbCodeData))
                .addGap(VERTICAL_GAP_SIZE)
                .addGroup(createVerticalComponents(layout, wikiCzLabel, wikiCzData))
                .addGap(VERTICAL_GAP_SIZE)
                .addGroup(createVerticalComponents(layout, wikiEnLabel, wikiEnData))
                .addGap(VERTICAL_GAP_SIZE)
                .addGroup(createVerticalComponents(layout, pictureLabel, pictureData))
                .addGap(VERTICAL_GAP_SIZE)
                .addComponent(pictureButton, CatalogSwingConstants.VERTICAL_BUTTON_SIZE, CatalogSwingConstants.VERTICAL_BUTTON_SIZE, CatalogSwingConstants.VERTICAL_BUTTON_SIZE)
                .addGap(VERTICAL_GAP_SIZE)
                .addGroup(createVerticalComponents(layout, noteLabel, noteData))
                .addGap(VERTICAL_GAP_SIZE)
                .addGroup(createVerticalComponents(layout, genreLabel, genreData))
                .addGap(VERTICAL_GAP_SIZE)
                .addComponent(genresButton, CatalogSwingConstants.VERTICAL_BUTTON_SIZE, CatalogSwingConstants.VERTICAL_BUTTON_SIZE, CatalogSwingConstants.VERTICAL_BUTTON_SIZE)
    }

}
