package cz.vhromada.catalog.gui.movie;

import java.util.List;

import javax.swing.GroupLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;

import cz.vhromada.catalog.common.Time;
import cz.vhromada.catalog.entity.Medium;
import cz.vhromada.catalog.entity.Movie;
import cz.vhromada.catalog.gui.commons.AbstractDataPanel;
import cz.vhromada.catalog.gui.commons.WebPageButtonType;

import org.springframework.util.Assert;

/**
 * A class represents panel with movie's data.
 *
 * @author Vladimir Hromada
 */
public class MovieDataPanel extends AbstractDataPanel<Movie> {

    /**
     * SerialVersionUID
     */
    private static final long serialVersionUID = 1L;

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
     * Label for media
     */
    private final JLabel mediaLabel = new JLabel("Length of media");

    /**
     * Label with media
     */
    private final JLabel mediaData = new JLabel();

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
     * Button for showing movie's ČSFD page
     */
    private final JButton csfdButton = new JButton("ČSFD");

    /**
     * Button for showing movie's IMDB page
     */
    private final JButton imdbButton = new JButton("IMDB");

    /**
     * Button for showing movie's czech Wikipedia page
     */
    private final JButton wikiCzButton = new JButton("Czech Wikipedia");

    /**
     * Button for showing movie's english Wikipedia page
     */
    private final JButton wikiEnButton = new JButton("English Wikipedia");

    /**
     * URL to ČSFD page about movie
     */
    private String csfd;

    /**
     * IMDB code
     */
    private int imdb;

    /**
     * URL to czech Wikipedia page about movie
     */
    private String wikiCz;

    /**
     * URL to english Wikipedia page about movie
     */
    private String wikiEn;

    /**
     * Creates a new instance of MovieDataPanel.
     *
     * @param movie movie
     * @throws IllegalArgumentException if movie is null
     */
    public MovieDataPanel(final Movie movie) {
        updateData(movie);

        Assert.notNull(movie, "Movie mustn't be null.");

        pictureData.setFocusable(false);

        initData(czechNameLabel, czechNameData);
        initData(originalNameLabel, originalNameData);
        initData(genreLabel, genreData);
        initData(yearLabel, yearData);
        initData(languageLabel, languageData);
        initData(subtitlesLabel, subtitlesData);
        initData(mediaLabel, mediaData);
        initData(totalLengthLabel, totalLengthData);
        initData(noteLabel, noteData);

        initButton(csfdButton, WebPageButtonType.CSFD);
        initButton(imdbButton, WebPageButtonType.IMDB);
        initButton(wikiCzButton, WebPageButtonType.WIKI_CZ);
        initButton(wikiEnButton, WebPageButtonType.WIKI_EN);

        createLayout();
    }

    @Override
    protected void updateComponentData(final Movie data) {
        Assert.notNull(data, "movie");

        final String picture = data.getPicture();
        if (picture.isEmpty()) {
            pictureData.setIcon(null);
        } else {
            pictureData.setIcon(new ImageIcon("posters/" + picture));
        }
        czechNameData.setText(data.getCzechName());
        originalNameData.setText(data.getOriginalName());
        genreData.setText(getGenres(data.getGenres()));
        yearData.setText(Integer.toString(data.getYear()));
        languageData.setText(data.getLanguage().toString());
        subtitlesData.setText(getSubtitles(data.getSubtitles()));
        mediaData.setText(getMedia(data));
        totalLengthData.setText(getMovieLength(data));
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
                .addGroup(createHorizontalButtons(layout, csfdButton, imdbButton, wikiCzButton, wikiEnButton));
    }

    @Override
    protected GroupLayout.Group getVerticalLayoutWithComponents(final GroupLayout layout, final GroupLayout.Group group) {
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
                .addGroup(createVerticalButtons(layout, csfdButton, imdbButton, wikiCzButton, wikiEnButton));
    }

    /**
     * Returns movie's media.
     *
     * @param movie movie
     * @return movie's media
     */
    private static String getMedia(final Movie movie) {
        final List<Medium> media = movie.getMedia();

        if (media == null || media.isEmpty()) {
            return "";
        }

        final StringBuilder subtitlesString = new StringBuilder();
        for (final Medium medium : media) {
            subtitlesString.append(new Time(medium.getLength()));
            subtitlesString.append(", ");
        }

        return subtitlesString.substring(0, subtitlesString.length() - 2);
    }

    /**
     * Returns total length of movie.
     *
     * @param movie movie
     * @return total length of movie
     */
    private static String getMovieLength(final Movie movie) {
        final List<Medium> media = movie.getMedia();

        if (media == null || media.isEmpty()) {
            return new Time(0).toString();
        }

        int totalLength = 0;
        for (final Medium medium : media) {
            totalLength += medium.getLength();
        }
        return new Time(totalLength).toString();
    }

}
