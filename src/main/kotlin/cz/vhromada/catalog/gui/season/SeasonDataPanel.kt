package cz.vhromada.catalog.gui.season

import cz.vhromada.catalog.entity.Season
import cz.vhromada.catalog.facade.EpisodeFacade
import cz.vhromada.catalog.gui.common.AbstractDataPanel
import cz.vhromada.common.entity.Time
import cz.vhromada.common.result.Status
import javax.swing.GroupLayout
import javax.swing.JLabel

/**
 * A class represents panel with season's data.
 *
 * @author Vladimir Hromada
 */
class SeasonDataPanel(
        season: Season,
        private val episodeFacade: EpisodeFacade) : AbstractDataPanel<Season>() {

    /**
     * Label for number
     */
    private val numberLabel = JLabel("Number of season")

    /**
     * Label with number
     */
    private val numberData = JLabel()

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

    init {
        updateData(season)

        initData(numberLabel, numberData)
        initData(yearLabel, yearData)
        initData(languageLabel, languageData)
        initData(subtitlesLabel, subtitlesData)
        initData(episodesCountLabel, episodesCountData)
        initData(totalLengthLabel, totalLengthData)
        initData(noteLabel, noteData)

        createLayout()
    }

    override fun updateComponentData(data: Season) {
        numberData.text = data.number?.toString()
        yearData.text = getYear(data)
        languageData.text = data.language?.toString()
        subtitlesData.text = getSubtitles(data.subtitles)
        episodesCountData.text = getEpisodesCount(data)
        totalLengthData.text = getSeasonLength(data)
        noteData.text = data.note
    }

    override fun getCzWikiUrl(): String {
        error { "Getting URL to czech Wikipedia page is not allowed for seasons." }
    }

    override fun getEnWikiUrl(): String {
        error { "Getting URL to english Wikipedia page is not allowed for seasons." }
    }

    override fun getCsfdUrl(): String {
        error { "Getting URL to ČSFD page is not allowed for seasons." }
    }

    override fun getImdbUrl(): Int {
        error { "Getting URL to IMDB page is not allowed for seasons." }
    }

    override fun getHorizontalLayoutWithComponents(layout: GroupLayout, group: GroupLayout.Group): GroupLayout.Group {
        return group
                .addGroup(createHorizontalDataComponents(layout, numberLabel, numberData))
                .addGroup(createHorizontalDataComponents(layout, yearLabel, yearData))
                .addGroup(createHorizontalDataComponents(layout, languageLabel, languageData))
                .addGroup(createHorizontalDataComponents(layout, subtitlesLabel, subtitlesData))
                .addGroup(createHorizontalDataComponents(layout, episodesCountLabel, episodesCountData))
                .addGroup(createHorizontalDataComponents(layout, totalLengthLabel, totalLengthData))
                .addGroup(createHorizontalDataComponents(layout, noteLabel, noteData))
    }

    @Suppress("DuplicatedCode")
    override fun getVerticalLayoutWithComponents(layout: GroupLayout, group: GroupLayout.Group): GroupLayout.Group {
        return group
                .addGroup(createVerticalComponents(layout, numberLabel, numberData))
                .addGap(VERTICAL_GAP_SIZE)
                .addGroup(createVerticalComponents(layout, yearLabel, yearData))
                .addGap(VERTICAL_GAP_SIZE)
                .addGroup(createVerticalComponents(layout, languageLabel, languageData))
                .addGap(VERTICAL_GAP_SIZE)
                .addGroup(createVerticalComponents(layout, subtitlesLabel, subtitlesData))
                .addGap(VERTICAL_GAP_SIZE)
                .addGroup(createVerticalComponents(layout, episodesCountLabel, episodesCountData))
                .addGap(VERTICAL_GAP_SIZE)
                .addGroup(createVerticalComponents(layout, totalLengthLabel, totalLengthData))
                .addGap(VERTICAL_GAP_SIZE)
                .addGroup(createVerticalComponents(layout, noteLabel, noteData))
                .addGap(VERTICAL_GAP_SIZE)
    }

    /**
     * Returns count of season's episodes.
     *
     * @param season season
     * @return count of season's episodes
     */
    private fun getEpisodesCount(season: Season): String {
        val result = episodeFacade.find(season)

        if (Status.OK == result.status) {
            return result.data!!.size.toString()
        }
        throw IllegalArgumentException("Can't get data. $result")
    }

    /**
     * Returns total length of all season's episodes.
     *
     * @param season season
     * @return total length of all season's episodes
     */
    private fun getSeasonLength(season: Season): String {
        val result = episodeFacade.find(season)

        if (Status.OK == result.status) {
            return Time(result.data!!.sumBy { it.length!! }).toString()
        }
        throw IllegalArgumentException("Can't get data. $result")
    }

    /**
     * Returns season's year.
     *
     * @param season season
     * @return season's year
     */
    private fun getYear(season: Season): String {
        val startYear = season.startYear!!
        val endYear = season.endYear!!

        return if (startYear == endYear) startYear.toString() else "$startYear - $endYear"
    }

}
