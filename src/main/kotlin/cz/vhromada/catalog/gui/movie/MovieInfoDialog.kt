package cz.vhromada.catalog.gui.movie

import cz.vhromada.catalog.entity.Genre
import cz.vhromada.catalog.entity.Medium
import cz.vhromada.catalog.entity.Movie
import cz.vhromada.catalog.facade.GenreFacade
import cz.vhromada.catalog.facade.PictureFacade
import cz.vhromada.catalog.gui.common.AbstractInfoDialog
import cz.vhromada.catalog.gui.common.CatalogSwingConstants
import cz.vhromada.catalog.gui.common.DialogResult
import cz.vhromada.catalog.gui.common.Picture
import cz.vhromada.common.entity.Time
import cz.vhromada.common.utils.Constants
import java.awt.EventQueue
import javax.swing.ButtonGroup
import javax.swing.GroupLayout
import javax.swing.JButton
import javax.swing.JCheckBox
import javax.swing.JLabel
import javax.swing.JRadioButton
import javax.swing.JSpinner
import javax.swing.JTextField
import javax.swing.SpinnerNumberModel

/**
 * A class represents dialog for movie.
 *
 * @author Vladimir Hromada
 */
class MovieInfoDialog : AbstractInfoDialog<Movie> {

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
     * List of media
     */
    private var media: MutableList<Medium>

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
     * Label for year
     */
    private val yearLabel = JLabel("Year")

    /**
     * Spinner for year
     */
    private val yearData = JSpinner(SpinnerNumberModel(Constants.CURRENT_YEAR, Constants.MIN_YEAR, Constants.CURRENT_YEAR, 1))

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
     * Label for media
     */
    private val mediaLabel = JLabel("Media")

    /**
     * Data with media
     */
    private val mediaData = JLabel()

    /**
     * Button for changing media
     */
    private val mediaButton = JButton("Change media", Picture.CHOOSE.icon)

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
     * Button for changing genres
     */
    private val genresButton = JButton("Change genres", Picture.CHOOSE.icon)

    /**
     * Creates a new instance of MovieInfoDialog.
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
        this.media = mutableListOf()
        imdbCodeData.isEnabled = false
    }

    /**
     * Creates a new instance of MovieInfoDialog.
     *
     * @param genreFacade   facade for genres
     * @param pictureFacade facade for pictures
     * @param movie         movie
     */
    constructor(genreFacade: GenreFacade, pictureFacade: PictureFacade, movie: Movie) : super(movie) {
        init()
        this.genreFacade = genreFacade
        this.pictureFacade = pictureFacade
        this.genres = movie.genres!!.filterNotNull().toMutableList()
        this.pictures = mutableListOf()
        if (movie.picture != null) {
            this.pictures.add(movie.picture!!)
        }
        this.media = movie.media!!.filterNotNull().toMutableList()
        this.czechNameData.text = movie.czechName
        this.originalNameData.text = movie.originalName
        this.yearData.value = movie.year
        initLanguage(movie.language!!, this.czechLanguageData, this.englishLanguageData, this.frenchLanguageData, this.japaneseLanguageData, this.slovakLanguageData)
        initSubtitles(movie.subtitles!!, this.czechSubtitlesData, this.englishSubtitlesData)
        this.mediaData.text = getMedia()
        this.csfdData.text = movie.csfd
        if (movie.imdbCode!! > 0) {
            this.imdbCodeLabel.isSelected = true
            this.imdbCodeData.value = movie.imdbCode
        } else {
            this.imdbCodeLabel.isSelected = false
            this.imdbCodeData.isEnabled = false
        }
        this.wikiCzData.text = movie.wikiCz
        this.wikiEnData.text = movie.wikiEn
        this.pictureData.text = getPicture(this.pictures)
        this.noteData.text = movie.note
        this.genreData.text = getGenres(this.genres)
    }

    @Suppress("DuplicatedCode")
    override fun initComponents() {
        initLabelComponent(czechNameLabel, czechNameData)
        initLabelComponent(originalNameLabel, originalNameData)
        initLabelComponent(yearLabel, yearData)
        initLabelComponent(csfdLabel, csfdData)
        initLabelComponent(wikiCzLabel, wikiCzData)
        initLabelComponent(wikiEnLabel, wikiEnData)
        initLabelComponent(pictureLabel, pictureData)
        initLabelComponent(noteLabel, noteData)

        addInputValidator(czechNameData)
        addInputValidator(originalNameData)

        languagesButtonGroup.add(czechLanguageData)
        languagesButtonGroup.add(englishLanguageData)
        languagesButtonGroup.add(frenchLanguageData)
        languagesButtonGroup.add(japaneseLanguageData)
        languagesButtonGroup.add(slovakLanguageData)

        languageLabel.isFocusable = false
        subtitlesLabel.isFocusable = false
        mediaData.isFocusable = false
        pictureData.isFocusable = false
        genreData.isFocusable = false

        imdbCodeLabel.addChangeListener { imdbCodeData.isEnabled = imdbCodeLabel.isSelected }

        mediaButton.addActionListener { mediaAction() }

        pictureButton.addActionListener { pictureAction(pictureFacade, pictures, pictureData) }

        genresButton.addActionListener { genresAction(genreFacade, genres, genreData) }
    }

    override fun processData(objectData: Movie?): Movie {
        if (objectData == null) {
            return Movie(id = null,
                    czechName = czechNameData.text,
                    originalName = originalNameData.text,
                    year = yearData.value as Int,
                    language = getSelectedLanguage(languagesButtonGroup.selection, czechLanguageData, englishLanguageData, frenchLanguageData, japaneseLanguageData),
                    subtitles = getSelectedSubtitles(czechSubtitlesData, englishSubtitlesData),
                    media = media,
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
                year = yearData.value as Int,
                language = getSelectedLanguage(languagesButtonGroup.selection, czechLanguageData, englishLanguageData, frenchLanguageData, japaneseLanguageData),
                subtitles = getSelectedSubtitles(czechSubtitlesData, englishSubtitlesData),
                media = media,
                csfd = csfdData.text,
                imdbCode = if (imdbCodeLabel.isSelected) imdbCodeData.value as Int else -1,
                wikiEn = wikiEnData.text,
                wikiCz = wikiCzData.text,
                picture = if (pictures.isEmpty()) null else pictures[0],
                note = noteData.text,
                genres = genres)
    }

    /**
     * Returns true if input is valid: czech name isn't empty string, original name isn't empty string, media isn't empty collection, genres isn't empty collection
     *
     * @return true if input is valid: czech name isn't empty string, original name isn't empty string, media isn't empty collection, genres isn't empty collection
     */
    override fun isInputValid(): Boolean {
        return czechNameData.text.isNotBlank() && originalNameData.text.isNotBlank() && media.isNotEmpty() && genres.isNotEmpty()
    }

    @Suppress("DuplicatedCode")
    override fun getHorizontalLayoutWithComponents(layout: GroupLayout, group: GroupLayout.Group): GroupLayout.Group {
        return group
                .addGroup(createHorizontalComponents(layout, czechNameLabel, czechNameData))
                .addGroup(createHorizontalComponents(layout, originalNameLabel, originalNameData))
                .addGroup(createHorizontalComponents(layout, yearLabel, yearData))
                .addGroup(createHorizontalComponents(layout, languageLabel, czechLanguageData))
                .addGroup(createHorizontalSelectableComponent(layout, englishLanguageData))
                .addGroup(createHorizontalSelectableComponent(layout, frenchLanguageData))
                .addGroup(createHorizontalSelectableComponent(layout, japaneseLanguageData))
                .addGroup(createHorizontalSelectableComponent(layout, slovakLanguageData))
                .addGroup(createHorizontalComponents(layout, subtitlesLabel, czechSubtitlesData))
                .addGroup(createHorizontalSelectableComponent(layout, englishSubtitlesData))
                .addGroup(createHorizontalComponents(layout, mediaLabel, mediaData))
                .addComponent(mediaButton, HORIZONTAL_LONG_COMPONENT_SIZE, HORIZONTAL_LONG_COMPONENT_SIZE, HORIZONTAL_LONG_COMPONENT_SIZE)
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
                .addGroup(createVerticalComponents(layout, yearLabel, yearData))
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
                .addGroup(createVerticalComponents(layout, mediaLabel, mediaData))
                .addGap(VERTICAL_GAP_SIZE)
                .addComponent(mediaButton, CatalogSwingConstants.VERTICAL_BUTTON_SIZE, CatalogSwingConstants.VERTICAL_BUTTON_SIZE, CatalogSwingConstants.VERTICAL_BUTTON_SIZE)
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

    /**
     * Returns media.
     *
     * @return media
     */
    private fun getMedia(): String {
        if (media.isNullOrEmpty()) {
            return ""
        }

        val mediaString = StringBuilder()
        for (medium in media) {
            mediaString.append(Time(medium.length!!))
            mediaString.append(", ")
        }
        return mediaString.substring(0, mediaString.length - 2)
    }

    /**
     * Performs action for button Change media.
     */
    private fun mediaAction() {
        EventQueue.invokeLater {
            val dialog = MediumChooseDialog(media)
            dialog.isVisible = true
            if (dialog.returnStatus === DialogResult.OK) {
                mediaData.text = getMedia()
                setOkButtonEnabled(isInputValid())
            }
        }
    }

}
