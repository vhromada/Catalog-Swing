package cz.vhromada.catalog.gui.music;

import java.util.List;

import javax.swing.GroupLayout;
import javax.swing.JButton;
import javax.swing.JLabel;

import cz.vhromada.catalog.entity.Music;
import cz.vhromada.catalog.entity.Song;
import cz.vhromada.catalog.facade.SongFacade;
import cz.vhromada.catalog.gui.common.AbstractDataPanel;
import cz.vhromada.catalog.gui.common.WebPageButtonType;
import cz.vhromada.common.Time;
import cz.vhromada.result.Result;
import cz.vhromada.result.Status;

import org.springframework.util.Assert;

/**
 * A class represents panel with music data.
 *
 * @author Vladimir Hromada
 */
public class MusicDataPanel extends AbstractDataPanel<Music> {

    /**
     * SerialVersionUID
     */
    private static final long serialVersionUID = 1L;

    /**
     * Facade for songs
     */
    private final SongFacade songFacade;

    /**
     * Label for name
     */
    private final JLabel nameLabel = new JLabel("Name");

    /**
     * Label with name
     */
    private final JLabel nameData = new JLabel();

    /**
     * Label for count of media
     */
    private final JLabel mediaCountLabel = new JLabel("Count of media");

    /**
     * Label with count of media
     */
    private final JLabel mediaCountData = new JLabel();

    /**
     * Label for count of songs
     */
    private final JLabel songsCountLabel = new JLabel("Count of songs");

    /**
     * Label with count of songs
     */
    private final JLabel songsCountData = new JLabel();

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
     * Button for showing music czech Wikipedia page
     */
    private final JButton wikiCzButton = new JButton("Czech Wikipedia");

    /**
     * Button for showing music english Wikipedia page
     */
    private final JButton wikiEnButton = new JButton("English Wikipedia");

    /**
     * URL to czech Wikipedia page about music
     */
    private String wikiCz;

    /**
     * URL to english Wikipedia page about music
     */
    private String wikiEn;

    /**
     * Creates a new instance of MusicDataPanel.
     *
     * @param music      music
     * @param songFacade facade for songs
     * @throws IllegalArgumentException if music is null
     *                                  or facade for songs is null
     */
    public MusicDataPanel(final Music music, final SongFacade songFacade) {
        Assert.notNull(songFacade, "Facade for songs mustn't be null.");

        this.songFacade = songFacade;

        updateData(music);

        initData(nameLabel, nameData);
        initData(mediaCountLabel, mediaCountData);
        initData(songsCountLabel, songsCountData);
        initData(totalLengthLabel, totalLengthData);
        initData(noteLabel, noteData);

        initButton(wikiCzButton, WebPageButtonType.WIKI_CZ);
        initButton(wikiEnButton, WebPageButtonType.WIKI_EN);

        createLayout();
    }

    @Override
    protected void updateComponentData(final Music data) {
        nameData.setText(data.getName());
        mediaCountData.setText(Integer.toString(data.getMediaCount()));
        songsCountData.setText(getSongsCount(data));
        totalLengthData.setText(getMusicLength(data));
        noteData.setText(data.getNote());

        wikiCz = data.getWikiCz();
        wikiEn = data.getWikiEn();

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
        throw new IllegalStateException("Getting URL to ČSFD page is not allowed for music.");
    }

    @Override
    protected int getImdbUrl() {
        throw new IllegalStateException("Getting URL to IMDB page is not allowed for music.");
    }

    @Override
    protected GroupLayout.Group getHorizontalLayoutWithComponents(final GroupLayout layout, final GroupLayout.Group group) {
        return group
            .addGroup(createHorizontalDataComponents(layout, nameLabel, nameData))
            .addGroup(createHorizontalDataComponents(layout, mediaCountLabel, mediaCountData))
            .addGroup(createHorizontalDataComponents(layout, songsCountLabel, songsCountData))
            .addGroup(createHorizontalDataComponents(layout, totalLengthLabel, totalLengthData))
            .addGroup(createHorizontalDataComponents(layout, noteLabel, noteData))
            .addGroup(createHorizontalButtons(layout, wikiCzButton, wikiEnButton));
    }

    @Override
    protected GroupLayout.Group getVerticalLayoutWithComponents(final GroupLayout layout, final GroupLayout.Group group) {
        return group
            .addGroup(createVerticalComponents(layout, nameLabel, nameData))
            .addGap(VERTICAL_GAP_SIZE)
            .addGroup(createVerticalComponents(layout, mediaCountLabel, mediaCountData))
            .addGap(VERTICAL_GAP_SIZE)
            .addGroup(createVerticalComponents(layout, songsCountLabel, songsCountData))
            .addGap(VERTICAL_GAP_SIZE)
            .addGroup(createVerticalComponents(layout, totalLengthLabel, totalLengthData))
            .addGap(VERTICAL_GAP_SIZE)
            .addGroup(createVerticalComponents(layout, noteLabel, noteData))
            .addGap(VERTICAL_GAP_SIZE)
            .addGroup(createVerticalButtons(layout, wikiCzButton, wikiEnButton));
    }

    /**
     * Returns count of music songs.
     *
     * @param music music
     * @return count of music songs
     */
    private String getSongsCount(final Music music) {
        final Result<List<Song>> result = songFacade.find(music);

        if (Status.OK == result.getStatus()) {
            return Integer.toString(result.getData().size());
        } else {
            throw new IllegalArgumentException("Can't get data. " + result);
        }
    }

    /**
     * Returns total length of all music songs.
     *
     * @param music music
     * @return total length of all music songs
     */
    private String getMusicLength(final Music music) {
        final Result<List<Song>> result = songFacade.find(music);

        if (Status.OK == result.getStatus()) {
            int totalLength = 0;
            for (final Song song : result.getData()) {
                totalLength += song.getLength();
            }
            return new Time(totalLength).toString();
        } else {
            throw new IllegalArgumentException("Can't get data. " + result);
        }
    }

}
