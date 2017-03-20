package cz.vhromada.catalog.gui.season;

import java.util.List;

import javax.swing.GroupLayout;
import javax.swing.JLabel;

import cz.vhromada.catalog.common.Time;
import cz.vhromada.catalog.entity.Episode;
import cz.vhromada.catalog.entity.Season;
import cz.vhromada.catalog.facade.EpisodeFacade;
import cz.vhromada.catalog.gui.common.AbstractDataPanel;
import cz.vhromada.result.Result;
import cz.vhromada.result.Status;

import org.springframework.util.Assert;

/**
 * A class represents panel with season's data.
 *
 * @author Vladimir Hromada
 */
public class SeasonDataPanel extends AbstractDataPanel<Season> {

    /**
     * SerialVersionUID
     */
    private static final long serialVersionUID = 1L;

    /**
     * Facade for episodes
     */
    private final EpisodeFacade episodeFacade;

    /**
     * Label for number
     */
    private final JLabel numberLabel = new JLabel("Number of season");

    /**
     * Label with number
     */
    private final JLabel numberData = new JLabel();

    /**
     * Label for year
     */
    private final JLabel yearLabel = new JLabel("Year");

    /**
     * Label with year
     */
    private final JLabel yearData = new JLabel();

    /**
     * Label for language
     */
    private final JLabel languageLabel = new JLabel("Language");

    /**
     * Label with language
     */
    private final JLabel languageData = new JLabel();

    /**
     * Label for subtitles
     */
    private final JLabel subtitlesLabel = new JLabel("Subtitles");

    /**
     * Label with subtitles
     */
    private final JLabel subtitlesData = new JLabel();

    /**
     * Label for count of episodes
     */
    private final JLabel episodesCountLabel = new JLabel("Count of episodes");

    /**
     * Label with count of episodes
     */
    private final JLabel episodesCountData = new JLabel();

    /**
     * Label for total length
     */
    private final JLabel totalLengthLabel = new JLabel("Total length");

    /**
     * Label with total length
     */
    private final JLabel totalLengthData = new JLabel();

    /**
     * Label for note
     */
    private final JLabel noteLabel = new JLabel("Note");

    /**
     * Label with note
     */
    private final JLabel noteData = new JLabel();

    /**
     * Creates a new instance of SeasonDataPanel.
     *
     * @param season         season
     * @param episodeFacade facade for episodes
     * @throws IllegalArgumentException if season is null
     *                                  or facade for episodes is null
     */
    public SeasonDataPanel(final Season season, final EpisodeFacade episodeFacade) {
        Assert.notNull(episodeFacade, "Facade for episodes mustn't be null.");

        this.episodeFacade = episodeFacade;

        updateData(season);

        initData(numberLabel, numberData);
        initData(yearLabel, yearData);
        initData(languageLabel, languageData);
        initData(subtitlesLabel, subtitlesData);
        initData(episodesCountLabel, episodesCountData);
        initData(totalLengthLabel, totalLengthData);
        initData(noteLabel, noteData);

        createLayout();
    }

    @Override
    protected void updateComponentData(final Season data) {
        numberData.setText(Integer.toString(data.getNumber()));
        yearData.setText(getYear(data));
        languageData.setText(data.getLanguage().toString());
        subtitlesData.setText(getSubtitles(data.getSubtitles()));
        episodesCountData.setText(getEpisodesCount(data));
        totalLengthData.setText(getSeasonLength(data));
        noteData.setText(data.getNote());
    }

    @Override
    protected String getCzWikiUrl() {
        throw new IllegalStateException("Getting URL to czech Wikipedia page is not allowed for seasons.");
    }

    @Override
    protected String getEnWikiUrl() {
        throw new IllegalStateException("Getting URL to english Wikipedia page is not allowed for seasons.");
    }

    @Override
    protected String getCsfdUrl() {
        throw new IllegalStateException("Getting URL to ČSFD page is not allowed for seasons.");
    }

    @Override
    protected int getImdbUrl() {
        throw new IllegalStateException("Getting URL to IMDB page is not allowed for seasons.");
    }

    @Override
    protected GroupLayout.Group getHorizontalLayoutWithComponents(final GroupLayout layout, final GroupLayout.Group group) {
        return group
                .addGroup(createHorizontalDataComponents(layout, numberLabel, numberData))
                .addGroup(createHorizontalDataComponents(layout, yearLabel, yearData))
                .addGroup(createHorizontalDataComponents(layout, languageLabel, languageData))
                .addGroup(createHorizontalDataComponents(layout, subtitlesLabel, subtitlesData))
                .addGroup(createHorizontalDataComponents(layout, episodesCountLabel, episodesCountData))
                .addGroup(createHorizontalDataComponents(layout, totalLengthLabel, totalLengthData))
                .addGroup(createHorizontalDataComponents(layout, noteLabel, noteData));
    }

    @Override
    protected GroupLayout.Group getVerticalLayoutWithComponents(final GroupLayout layout, final GroupLayout.Group group) {
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
                .addGap(VERTICAL_GAP_SIZE);
    }

    /**
     * Returns season's year.
     *
     * @param season  season
     * @return season's year
     */
    private static String getYear(final Season season) {
        final int startYear = season.getStartYear();
        final int endYear = season.getEndYear();

        return startYear == endYear ? Integer.toString(startYear) : startYear + " - " + endYear;
    }

    /**
     * Returns count of season's episodes.
     *
     * @param season  season
     * @return count of season's episodes
     */
    private String getEpisodesCount(final Season season) {
        final Result<List<Episode>> result = episodeFacade.find(season);

        if (Status.OK == result.getStatus()) {
            return Integer.toString(result.getData().size());
        } else {
            throw new IllegalArgumentException("Can't get data. " + result);
        }
    }

    /**
     * Returns total length of all season's episodes.
     *
     * @param season  season
     * @return total length of all season's episodes
     */
    private String getSeasonLength(final Season season) {
        final Result<List<Episode>> result = episodeFacade.find(season);

        if (Status.OK == result.getStatus()) {
            int totalLength = 0;
            for (final Episode episode : result.getData()) {
                totalLength += episode.getLength();
            }
            return new Time(totalLength).toString();
        } else {
            throw new IllegalArgumentException("Can't get data. " + result);
        }
    }

}
