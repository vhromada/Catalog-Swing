package cz.vhromada.catalog.gui.show;

import java.util.List;

import javax.swing.GroupLayout;
import javax.swing.JButton;
import javax.swing.JLabel;

import cz.vhromada.catalog.entity.Episode;
import cz.vhromada.catalog.entity.Season;
import cz.vhromada.catalog.entity.Show;
import cz.vhromada.catalog.facade.EpisodeFacade;
import cz.vhromada.catalog.facade.PictureFacade;
import cz.vhromada.catalog.facade.SeasonFacade;
import cz.vhromada.catalog.gui.common.AbstractDataPanel;
import cz.vhromada.catalog.gui.common.WebPageButtonType;
import cz.vhromada.common.Time;
import cz.vhromada.validation.result.Result;
import cz.vhromada.validation.result.Status;

import org.springframework.util.Assert;

/**
 * A class represents panel with show data.
 *
 * @author Vladimir Hromada
 */
public class ShowDataPanel extends AbstractDataPanel<Show> {

    /**
     * SerialVersionUID
     */
    private static final long serialVersionUID = 1L;

    /**
     * Error message for result with errors
     */
    private static final String RESULT_WITH_ERROR_MESSAGE = "Can't get data. ";

    /**
     * Facade for seasons
     */
    private final SeasonFacade seasonFacade;

    /**
     * Facade for episodes
     */
    private final EpisodeFacade episodeFacade;
    /**
     * Facade for pictures
     */
    private PictureFacade pictureFacade;

    /**
     * Label for picture
     */
    private final JLabel pictureData = new JLabel();

    /**
     * Label for czech name
     */
    private final JLabel czechNameLabel = new JLabel("Czech name");

    /**
     * Label with czech name
     */
    private final JLabel czechNameData = new JLabel();

    /**
     * Label for original name
     */
    private final JLabel originalNameLabel = new JLabel("Original name");

    /**
     * Label with original name
     */
    private final JLabel originalNameData = new JLabel();

    /**
     * Label for genre
     */
    private final JLabel genreLabel = new JLabel("Genre");

    /**
     * Label with genre
     */
    private final JLabel genreData = new JLabel();

    /**
     * Label for count of seasons
     */
    private final JLabel seasonsCountLabel = new JLabel("Count of seasons");

    /**
     * Label with count of seasons
     */
    private final JLabel seasonsCountData = new JLabel();

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
     * Button for showing show ČSFD page
     */
    private final JButton csfdButton = new JButton("ČSFD");

    /**
     * Button for showing show IMDB page
     */
    private final JButton imdbButton = new JButton("IMDB");

    /**
     * Button for showing show czech Wikipedia page
     */
    private final JButton wikiCzButton = new JButton("Czech Wikipedia");

    /**
     * Button for showing show english Wikipedia page
     */
    private final JButton wikiEnButton = new JButton("English Wikipedia");

    /**
     * URL to ČSFD page about show
     */
    private String csfd;

    /**
     * IMDB code
     */
    private int imdb;

    /**
     * URL to czech Wikipedia page about show
     */
    private String wikiCz;

    /**
     * URL to english Wikipedia page about show
     */
    private String wikiEn;

    /**
     * Creates a new instance of ShowDataPanel.
     *
     * @param show          show
     * @param seasonFacade  facade for seasons
     * @param episodeFacade facade for episodes
     * @param pictureFacade facade for pictures
     * @throws IllegalArgumentException if show is null
     *                                  or facade for seasons is null
     *                                  or facade for episodes is null
     *                                  or facade for pictures is null
     */
    public ShowDataPanel(final Show show, final SeasonFacade seasonFacade, final EpisodeFacade episodeFacade, final PictureFacade pictureFacade) {
        Assert.notNull(seasonFacade, "Facade for seasons mustn't be null.");
        Assert.notNull(episodeFacade, "Facade for episodes mustn't be null.");
        Assert.notNull(pictureFacade, "Facade for pictures mustn't be null.");

        this.seasonFacade = seasonFacade;
        this.episodeFacade = episodeFacade;
        this.pictureFacade = pictureFacade;

        updateData(show);

        pictureData.setFocusable(false);

        initData(czechNameLabel, czechNameData);
        initData(originalNameLabel, originalNameData);
        initData(genreLabel, genreData);
        initData(seasonsCountLabel, seasonsCountData);
        initData(episodesCountLabel, episodesCountData);
        initData(totalLengthLabel, totalLengthData);
        initData(noteLabel, noteData);

        initButton(csfdButton, WebPageButtonType.CSFD);
        initButton(imdbButton, WebPageButtonType.IMDB);
        initButton(wikiCzButton, WebPageButtonType.WIKI_CZ);
        initButton(wikiEnButton, WebPageButtonType.WIKI_EN);

        createLayout();
    }

    @Override
    protected void updateComponentData(final Show data) {
        loadPicture(data.getPicture(), pictureFacade, pictureData);

        czechNameData.setText(data.getCzechName());
        originalNameData.setText(data.getOriginalName());
        genreData.setText(getGenres(data.getGenres()));
        seasonsCountData.setText(getSeasonsCount(data));
        episodesCountData.setText(getEpisodesCount(data));
        totalLengthData.setText(getShowLength(data));
        noteData.setText(data.getNote());

        csfd = data.getCsfd();
        imdb = data.getImdbCode();
        wikiCz = data.getWikiCz();
        wikiEn = data.getWikiEn();

        csfdButton.setEnabled(!csfd.isEmpty());
        imdbButton.setEnabled(imdb > 0);
        wikiCzButton.setEnabled(!wikiCz.isEmpty());
        wikiEnButton.setEnabled(!wikiEn.isEmpty());
    }

    @Override
    protected String getCzWikiUrl() {
        return wikiCz;
    }

    @Override
    protected String getEnWikiUrl() {
        return wikiEn;
    }

    @Override
    protected String getCsfdUrl() {
        return csfd;
    }

    @Override
    protected int getImdbUrl() {
        return imdb;
    }

    @Override
    protected GroupLayout.Group getHorizontalLayoutWithComponents(final GroupLayout layout, final GroupLayout.Group group) {
        return group
            .addComponent(pictureData, HORIZONTAL_PICTURE_SIZE, HORIZONTAL_PICTURE_SIZE, HORIZONTAL_PICTURE_SIZE)
            .addGroup(createHorizontalDataComponents(layout, czechNameLabel, czechNameData))
            .addGroup(createHorizontalDataComponents(layout, originalNameLabel, originalNameData))
            .addGroup(createHorizontalDataComponents(layout, genreLabel, genreData))
            .addGroup(createHorizontalDataComponents(layout, seasonsCountLabel, seasonsCountData))
            .addGroup(createHorizontalDataComponents(layout, episodesCountLabel, episodesCountData))
            .addGroup(createHorizontalDataComponents(layout, totalLengthLabel, totalLengthData))
            .addGroup(createHorizontalDataComponents(layout, noteLabel, noteData))
            .addGroup(createHorizontalButtons(layout, csfdButton, imdbButton, wikiCzButton, wikiEnButton));
    }

    @Override
    protected GroupLayout.Group getVerticalLayoutWithComponents(final GroupLayout layout, final GroupLayout.Group group) {
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
            .addGroup(createVerticalButtons(layout, csfdButton, imdbButton, wikiCzButton, wikiEnButton));
    }

    /**
     * Returns count of show seasons.
     *
     * @param show show
     * @return count of show seasons
     */
    private String getSeasonsCount(final Show show) {
        final Result<List<Season>> result = seasonFacade.find(show);

        if (Status.OK == result.getStatus()) {
            return Integer.toString(result.getData().size());
        } else {
            throw new IllegalArgumentException(RESULT_WITH_ERROR_MESSAGE + result);
        }
    }

    /**
     * Returns count of show episodes.
     *
     * @param show show
     * @return count of show episodes
     */
    private String getEpisodesCount(final Show show) {
        final Result<List<Season>> seasonsResult = seasonFacade.find(show);

        if (Status.OK == seasonsResult.getStatus()) {
            int totalCount = 0;
            for (final Season season : seasonsResult.getData()) {
                final Result<List<Episode>> episodesResult = episodeFacade.find(season);
                if (Status.OK == episodesResult.getStatus()) {
                    totalCount += episodesResult.getData().size();
                } else {
                    throw new IllegalArgumentException(RESULT_WITH_ERROR_MESSAGE + episodesResult);
                }
            }
            return Integer.toString(totalCount);
        } else {
            throw new IllegalArgumentException(RESULT_WITH_ERROR_MESSAGE + seasonsResult);
        }
    }

    /**
     * Returns total length of all show seasons.
     *
     * @param show show
     * @return total length of all show seasons
     */
    private String getShowLength(final Show show) {
        final Result<List<Season>> seasonsResult = seasonFacade.find(show);

        if (Status.OK == seasonsResult.getStatus()) {
            int totalLength = 0;
            for (final Season season : seasonsResult.getData()) {
                final Result<List<Episode>> episodesResult = episodeFacade.find(season);
                if (Status.OK == episodesResult.getStatus()) {
                    for (final Episode episode : episodesResult.getData()) {
                        totalLength += episode.getLength();
                    }
                } else {
                    throw new IllegalArgumentException(RESULT_WITH_ERROR_MESSAGE + episodesResult);
                }
            }
            return new Time(totalLength).toString();
        } else {
            throw new IllegalArgumentException(RESULT_WITH_ERROR_MESSAGE + seasonsResult);
        }
    }

}
