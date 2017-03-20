package cz.vhromada.catalog.gui.song;

import javax.swing.GroupLayout;
import javax.swing.JLabel;

import cz.vhromada.catalog.common.Time;
import cz.vhromada.catalog.entity.Song;
import cz.vhromada.catalog.gui.common.AbstractDataPanel;

/**
 * A class represents panel with song's data.
 *
 * @author Vladimir Hromada
 */
public class SongDataPanel extends AbstractDataPanel<Song> {

    /**
     * SerialVersionUID
     */
    private static final long serialVersionUID = 1L;

    /**
     * Label for name
     */
    private final JLabel nameLabel = new JLabel("Name");

    /**
     * Label with name
     */
    private final JLabel nameData = new JLabel();

    /**
     * Label for length
     */
    private final JLabel lengthLabel = new JLabel("Length");

    /**
     * Label with length
     */
    private final JLabel lengthData = new JLabel();

    /**
     * Label for note
     */
    private final JLabel noteLabel = new JLabel("Note");

    /**
     * Label with note
     */
    private final JLabel noteData = new JLabel();

    /**
     * Creates a new instance of SongDataPanel.
     *
     * @param song song
     * @throws IllegalArgumentException if song is null
     */
    public SongDataPanel(final Song song) {
        updateData(song);

        initData(nameLabel, nameData);
        initData(lengthLabel, lengthData);
        initData(noteLabel, noteData);

        createLayout();
    }

    @Override
    protected void updateComponentData(final Song data) {
        nameData.setText(data.getName());
        lengthData.setText(new Time(data.getLength()).toString());
        noteData.setText(data.getNote());
    }

    @Override
    protected String getCzWikiUrl() {
        throw new IllegalStateException("Getting URL to czech Wikipedia page is not allowed for songs.");
    }

    @Override
    protected String getEnWikiUrl() {
        throw new IllegalStateException("Getting URL to english Wikipedia page is not allowed for songs.");
    }

    @Override
    protected String getCsfdUrl() {
        throw new IllegalStateException("Getting URL to ČSFD page is not allowed for songs.");
    }

    @Override
    protected int getImdbUrl() {
        throw new IllegalStateException("Getting URL to IMDB page is not allowed for songs.");
    }

    @Override
    protected GroupLayout.Group getHorizontalLayoutWithComponents(final GroupLayout layout, final GroupLayout.Group group) {
        return group
                .addGroup(createHorizontalDataComponents(layout, nameLabel, nameData))
                .addGroup(createHorizontalDataComponents(layout, lengthLabel, lengthData))
                .addGroup(createHorizontalDataComponents(layout, noteLabel, noteData));
    }

    @Override
    protected GroupLayout.Group getVerticalLayoutWithComponents(final GroupLayout layout, final GroupLayout.Group group) {
        return group
                .addGroup(createVerticalComponents(layout, nameLabel, nameData))
                .addGap(VERTICAL_GAP_SIZE)
                .addGroup(createVerticalComponents(layout, lengthLabel, lengthData))
                .addGap(VERTICAL_GAP_SIZE)
                .addGroup(createVerticalComponents(layout, noteLabel, noteData))
                .addGap(VERTICAL_GAP_SIZE);
    }

}
