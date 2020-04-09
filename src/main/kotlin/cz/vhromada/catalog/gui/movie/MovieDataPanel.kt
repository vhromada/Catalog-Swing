package cz.vhromada.catalog.gui.movie

import cz.vhromada.catalog.entity.Movie
import cz.vhromada.catalog.facade.PictureFacade
import cz.vhromada.catalog.gui.common.AbstractDataPanel
import cz.vhromada.catalog.gui.common.WebPageButtonType
import cz.vhromada.common.entity.Time
import javax.swing.GroupLayout
import javax.swing.JButton
import javax.swing.JLabel

/**
 * A class represents panel with movie's data.
 *
 * @author Vladimir Hromada
 */
class MovieDataPanel(
        movie: Movie,
        private val pictureFacade: PictureFacade) : AbstractDataPanel<Movie>() {

    /**
     * Label for picture
     */
    private val pictureData = JLabel()

    /**
     * Label for czech name
     */
    private val czechNameLabel = JLabel("Czech name")

    /**
     * Label with czech name
     */
    private val czechNameData = JLabel()

    /**
     * Label for original name
     */
    private val originalNameLabel = JLabel("Original name")

    /**
     * Label with original name
     */
    private val originalNameData = JLabel()

    /**
     * Label for genre
     */
    private val genreLabel = JLabel("Genre")

    /**
     * Label with genre
     */
    private val genreData = JLabel()

    /**
     * Label for year
     */
    private val yearLabel = JLabel("Year")

    /**
     * Label with year
     */
    private val yearData = JLabel()

    /**
     * Label for language
     */
    private val languageLabel = JLabel("Language")

    /**
     * Label with language
     */
    private val languageData = JLabel()

    /**
     * Label for subtitles
     */
    private val subtitlesLabel = JLabel("Subtitles")

    /**
     * Label with subtitles
     */
    private val subtitlesData = JLabel()

    /**
     * Label for media
     */
    private val mediaLabel = JLabel("Length of media")

    /**
     * Label with media
     */
    private val mediaData = JLabel()

    /**
     * Label for total length
     */
    private val totalLengthLabel = JLabel("Total length")

    /**
     * Label with total length
     */
    private val totalLengthData = JLabel()

    /**
     * Label for note
     */
    private val noteLabel = JLabel("Note")

    /**
     * Label with note
     */
    private val noteData = JLabel()

    /**
     * Button for showing movie's ČSFD page
     */
    private val csfdButton = JButton("ČSFD")

    /**
     * Button for showing movie's IMDB page
     */
    private val imdbButton = JButton("IMDB")

    /**
     * Button for showing movie's czech Wikipedia page
     */
    private val wikiCzButton = JButton("Czech Wikipedia")

    /**
     * Button for showing movie's english Wikipedia page
     */
    private val wikiEnButton = JButton("English Wikipedia")

    /**
     * URL to ČSFD page about movie
     */
    private var csfd: String? = null

    /**
     * IMDB code
     */
    private var imdb: Int? = null

    /**
     * URL to czech Wikipedia page about movie
     */
    private var wikiCz: String? = null

    /**
     * URL to english Wikipedia page about movie
     */
    private var wikiEn: String? = null

    init {
        updateData(movie)

        pictureData.isFocusable = false

        initData(czechNameLabel, czechNameData)
        initData(originalNameLabel, originalNameData)
        initData(genreLabel, genreData)
        initData(yearLabel, yearData)
        initData(languageLabel, languageData)
        initData(subtitlesLabel, subtitlesData)
        initData(mediaLabel, mediaData)
        initData(totalLengthLabel, totalLengthData)
        initData(noteLabel, noteData)

        initButton(csfdButton, WebPageButtonType.CSFD)
        initButton(imdbButton, WebPageButtonType.IMDB)
        initButton(wikiCzButton, WebPageButtonType.WIKI_CZ)
        initButton(wikiEnButton, WebPageButtonType.WIKI_EN)

        createLayout()
    }

    @Suppress("DuplicatedCode")
    override fun updateComponentData(data: Movie) {
        loadPicture(data.picture, pictureFacade, pictureData)

        czechNameData.text = data.czechName
        originalNameData.text = data.originalName
        genreData.text = getGenres(data.genres)
        yearData.text = data.year?.toString()
        languageData.text = data.language?.toString()
        subtitlesData.text = getSubtitles(data.subtitles)
        mediaData.text = getMedia(data)
        totalLengthData.text = getMovieLength(data)
        noteData.text = data.note

        csfd = data.csfd
        imdb = data.imdbCode
        wikiCz = data.wikiCz
        wikiEn = data.wikiEn

        csfdButton.isEnabled = !csfd.isNullOrBlank()
        imdbButton.isEnabled = imdb!! > 0
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
        return csfd!!
    }

    override fun getImdbUrl(): Int {
        return imdb!!
    }

    override fun getHorizontalLayoutWithComponents(layout: GroupLayout, group: GroupLayout.Group): GroupLayout.Group {
        return group.addComponent(pictureData, HORIZONTAL_PICTURE_SIZE, HORIZONTAL_PICTURE_SIZE, HORIZONTAL_PICTURE_SIZE)
                .addGroup(createHorizontalDataComponents(layout, czechNameLabel, czechNameData))
                .addGroup(createHorizontalDataComponents(layout, originalNameLabel, originalNameData))
                .addGroup(createHorizontalDataComponents(layout, genreLabel, genreData))
                .addGroup(createHorizontalDataComponents(layout, yearLabel, yearData))
                .addGroup(createHorizontalDataComponents(layout, languageLabel, languageData))
                .addGroup(createHorizontalDataComponents(layout, subtitlesLabel, subtitlesData))
                .addGroup(createHorizontalDataComponents(layout, mediaLabel, mediaData))
                .addGroup(createHorizontalDataComponents(layout, totalLengthLabel, totalLengthData))
                .addGroup(createHorizontalDataComponents(layout, noteLabel, noteData))
                .addGroup(createHorizontalButtons(layout, csfdButton, imdbButton, wikiCzButton, wikiEnButton))
    }

    @Suppress("DuplicatedCode")
    override fun getVerticalLayoutWithComponents(layout: GroupLayout, group: GroupLayout.Group): GroupLayout.Group {
        return group.addComponent(pictureData, VERTICAL_PICTURE_SIZE, VERTICAL_PICTURE_SIZE, VERTICAL_PICTURE_SIZE)
                .addGap(VERTICAL_GAP_SIZE)
                .addGroup(createVerticalComponents(layout, czechNameLabel, czechNameData))
                .addGap(VERTICAL_GAP_SIZE)
                .addGroup(createVerticalComponents(layout, originalNameLabel, originalNameData))
                .addGap(VERTICAL_GAP_SIZE)
                .addGroup(createVerticalComponents(layout, genreLabel, genreData))
                .addGap(VERTICAL_GAP_SIZE)
                .addGroup(createVerticalComponents(layout, yearLabel, yearData))
                .addGap(VERTICAL_GAP_SIZE)
                .addGroup(createVerticalComponents(layout, languageLabel, languageData))
                .addGap(VERTICAL_GAP_SIZE)
                .addGroup(createVerticalComponents(layout, subtitlesLabel, subtitlesData))
                .addGap(VERTICAL_GAP_SIZE)
                .addGroup(createVerticalComponents(layout, mediaLabel, mediaData))
                .addGap(VERTICAL_GAP_SIZE)
                .addGroup(createVerticalComponents(layout, totalLengthLabel, totalLengthData))
                .addGap(VERTICAL_GAP_SIZE)
                .addGroup(createVerticalComponents(layout, noteLabel, noteData))
                .addGap(VERTICAL_GAP_SIZE)
                .addGroup(createVerticalButtons(layout, csfdButton, imdbButton, wikiCzButton, wikiEnButton))
    }

    /**
     * Returns movie's media.
     *
     * @param movie movie
     * @return movie's media
     */
    private fun getMedia(movie: Movie): String {
        val media = movie.media

        if (media.isNullOrEmpty()) {
            return ""
        }

        val subtitlesString = StringBuilder()
        for (medium in media.filterNotNull()) {
            subtitlesString.append(Time(medium.length!!))
            subtitlesString.append(", ")
        }
        return subtitlesString.substring(0, subtitlesString.length - 2)
    }

    /**
     * Returns total length of movie.
     *
     * @param movie movie
     * @return total length of movie
     */
    private fun getMovieLength(movie: Movie): String {
        val media = movie.media

        if (media.isNullOrEmpty()) {
            return Time(0).toString()
        }

        return Time(media.filterNotNull().sumBy { it.length!! }).toString()
    }

}
