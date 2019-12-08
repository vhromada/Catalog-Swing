package cz.vhromada.catalog.gui.show

import cz.vhromada.catalog.entity.Show
import cz.vhromada.catalog.facade.EpisodeFacade
import cz.vhromada.catalog.facade.PictureFacade
import cz.vhromada.catalog.facade.SeasonFacade
import cz.vhromada.catalog.gui.common.AbstractDataPanel
import cz.vhromada.catalog.gui.common.WebPageButtonType
import cz.vhromada.common.Time
import cz.vhromada.validation.result.Status
import javax.swing.GroupLayout
import javax.swing.JButton
import javax.swing.JLabel

/**
 * A class represents panel with show data.
 *
 * @author Vladimir Hromada
 */
class ShowDataPanel(
        show: Show,
        private val seasonFacade: SeasonFacade,
        private val episodeFacade: EpisodeFacade,
        private val pictureFacade: PictureFacade) : AbstractDataPanel<Show>() {

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
     * Label for count of seasons
     */
    private val seasonsCountLabel = JLabel("Count of seasons")

    /**
     * Label with count of seasons
     */
    private val seasonsCountData = JLabel()

    /**
     * Label for count of episodes
     */
    private val episodesCountLabel = JLabel("Count of episodes")

    /**
     * Label with count of episodes
     */
    private val episodesCountData = JLabel()

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
     * Button for showing show ČSFD page
     */
    private val csfdButton = JButton("ČSFD")

    /**
     * Button for showing show IMDB page
     */
    private val imdbButton = JButton("IMDB")

    /**
     * Button for showing show czech Wikipedia page
     */
    private val wikiCzButton = JButton("Czech Wikipedia")

    /**
     * Button for showing show english Wikipedia page
     */
    private val wikiEnButton = JButton("English Wikipedia")

    /**
     * URL to ČSFD page about show
     */
    private var csfd: String? = null

    /**
     * IMDB code
     */
    private var imdb: Int? = null

    /**
     * URL to czech Wikipedia page about show
     */
    private var wikiCz: String? = null

    /**
     * URL to english Wikipedia page about show
     */
    private var wikiEn: String? = null

    init {
        updateData(show)

        pictureData.isFocusable = false

        initData(czechNameLabel, czechNameData)
        initData(originalNameLabel, originalNameData)
        initData(genreLabel, genreData)
        initData(seasonsCountLabel, seasonsCountData)
        initData(episodesCountLabel, episodesCountData)
        initData(totalLengthLabel, totalLengthData)
        initData(noteLabel, noteData)

        initButton(csfdButton, WebPageButtonType.CSFD)
        initButton(imdbButton, WebPageButtonType.IMDB)
        initButton(wikiCzButton, WebPageButtonType.WIKI_CZ)
        initButton(wikiEnButton, WebPageButtonType.WIKI_EN)

        createLayout()
    }

    @Suppress("DuplicatedCode")
    override fun updateComponentData(data: Show) {
        loadPicture(data.picture, pictureFacade, pictureData)

        czechNameData.text = data.czechName
        originalNameData.text = data.originalName
        genreData.text = getGenres(data.genres)
        seasonsCountData.text = getSeasonsCount(data)
        episodesCountData.text = getEpisodesCount(data)
        totalLengthData.text = getShowLength(data)
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
        return group
                .addComponent(pictureData, HORIZONTAL_PICTURE_SIZE, HORIZONTAL_PICTURE_SIZE, HORIZONTAL_PICTURE_SIZE)
                .addGroup(createHorizontalDataComponents(layout, czechNameLabel, czechNameData))
                .addGroup(createHorizontalDataComponents(layout, originalNameLabel, originalNameData))
                .addGroup(createHorizontalDataComponents(layout, genreLabel, genreData))
                .addGroup(createHorizontalDataComponents(layout, seasonsCountLabel, seasonsCountData))
                .addGroup(createHorizontalDataComponents(layout, episodesCountLabel, episodesCountData))
                .addGroup(createHorizontalDataComponents(layout, totalLengthLabel, totalLengthData))
                .addGroup(createHorizontalDataComponents(layout, noteLabel, noteData))
                .addGroup(createHorizontalButtons(layout, csfdButton, imdbButton, wikiCzButton, wikiEnButton))
    }

    @Suppress("DuplicatedCode")
    override fun getVerticalLayoutWithComponents(layout: GroupLayout, group: GroupLayout.Group): GroupLayout.Group {
        return group
                .addComponent(pictureData, VERTICAL_PICTURE_SIZE, VERTICAL_PICTURE_SIZE, VERTICAL_PICTURE_SIZE)
                .addGap(VERTICAL_GAP_SIZE)
                .addGroup(createVerticalComponents(layout, czechNameLabel, czechNameData))
                .addGap(VERTICAL_GAP_SIZE)
                .addGroup(createVerticalComponents(layout, originalNameLabel, originalNameData))
                .addGap(VERTICAL_GAP_SIZE)
                .addGroup(createVerticalComponents(layout, genreLabel, genreData))
                .addGap(VERTICAL_GAP_SIZE)
                .addGroup(createVerticalComponents(layout, seasonsCountLabel, seasonsCountData))
                .addGap(VERTICAL_GAP_SIZE)
                .addGroup(createVerticalComponents(layout, episodesCountLabel, episodesCountData))
                .addGap(VERTICAL_GAP_SIZE)
                .addGroup(createVerticalComponents(layout, totalLengthLabel, totalLengthData))
                .addGap(VERTICAL_GAP_SIZE)
                .addGroup(createVerticalComponents(layout, noteLabel, noteData))
                .addGap(VERTICAL_GAP_SIZE)
                .addGroup(createVerticalButtons(layout, csfdButton, imdbButton, wikiCzButton, wikiEnButton))
    }

    /**
     * Returns count of show seasons.
     *
     * @param show show
     * @return count of show seasons
     */
    private fun getSeasonsCount(show: Show): String {
        val result = seasonFacade.find(show)

        if (Status.OK == result.status) {
            return result.data!!.size.toString()
        }
        throw IllegalArgumentException(RESULT_WITH_ERROR_MESSAGE + result)
    }

    /**
     * Returns count of show episodes.
     *
     * @param show show
     * @return count of show episodes
     */
    private fun getEpisodesCount(show: Show): String {
        val seasonsResult = seasonFacade.find(show)

        if (Status.OK == seasonsResult.status) {
            var totalCount = 0
            for (season in seasonsResult.data!!) {
                val episodesResult = episodeFacade.find(season)
                if (Status.OK == episodesResult.status) {
                    totalCount += episodesResult.data!!.size
                } else {
                    throw IllegalArgumentException(RESULT_WITH_ERROR_MESSAGE + episodesResult)
                }
            }
            return totalCount.toString()
        }
        throw IllegalArgumentException(RESULT_WITH_ERROR_MESSAGE + seasonsResult)
    }

    /**
     * Returns total length of all show seasons.
     *
     * @param show show
     * @return total length of all show seasons
     */
    private fun getShowLength(show: Show): String {
        val seasonsResult = seasonFacade.find(show)

        if (Status.OK == seasonsResult.status) {
            var totalLength = 0
            for (season in seasonsResult.data!!) {
                val episodesResult = episodeFacade.find(season)
                if (Status.OK == episodesResult.status) {
                    totalLength += episodesResult.data!!.sumBy { it.length!! }
                } else {
                    throw IllegalArgumentException(RESULT_WITH_ERROR_MESSAGE + episodesResult)
                }
            }
            return Time(totalLength).toString()
        }
        throw IllegalArgumentException(RESULT_WITH_ERROR_MESSAGE + seasonsResult)
    }

    companion object {

        /**
         * Error message for result with errors
         */
        private const val RESULT_WITH_ERROR_MESSAGE = "Can't get data. "

    }

}
