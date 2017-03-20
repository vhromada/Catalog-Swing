package cz.vhromada.catalog.gui.episode;

import javax.swing.GroupLayout;
import javax.swing.JLabel;

import cz.vhromada.catalog.common.Time;
import cz.vhromada.catalog.entity.Episode;
import cz.vhromada.catalog.gui.commons.AbstractDataPanel;

/**
 * A class represents panel with episode's data.
 *
 * @author Vladimir Hromada
 */
public class EpisodeDataPanel extends AbstractDataPanel<Episode> {

    /**
     * SerialVersionUID
     */
    private static final long serialVersionUID = 1L;

    /**
     * Label for number
     */
    private final JLabel numberLabel = new JLabel("Number of episode");

    /**
     * Label with number
     */
    private final JLabel numberData = new JLabel();

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
     * Creates a new instance of EpisodeDataPanel.
     *
     * @param episode episode
     * @throws IllegalArgumentException if episode is null
     */
    public EpisodeDataPanel(final Episode episode) {
        updateData(episode);

        initData(numberLabel, numberData);
        initData(nameLabel, nameData);
        initData(lengthLabel, lengthData);
        initData(noteLabel, noteData);

        createLayout();
    }

    @Override
    protected void updateComponentData(final Episode data) {
        numberData.setText(Integer.toString(data.getNumber()));
        nameData.setText(data.getName());
        lengthData.setText(new Time(data.getLength()).toString());
        noteData.setText(data.getNote());
    }

    @Override
    protected String getCzWikiUrl() {
        throw new IllegalStateException("Getting URL to czech Wikipedia page is not allowed for episodes.");
    }

    @Override
    protected String getEnWikiUrl() {
        throw new IllegalStateException("Getting URL to english Wikipedia page is not allowed for episodes.");
    }

    @Override
    protected String getCsfdUrl() {
        throw new IllegalStateException("Getting URL to ČSFD page is not allowed for episodes.");
    }

    @Override
    protected int getImdbUrl() {
        throw new IllegalStateException("Getting URL to IMDB page is not allowed for episodes.");
    }

    @Override
    protected GroupLayout.Group getHorizontalLayoutWithComponents(final GroupLayout layout, final GroupLayout.Group group) {
        return group
                .addGroup(createHorizontalDataComponents(layout, numberLabel, numberData))
                .addGroup(createHorizontalDataComponents(layout, nameLabel, nameData))
                .addGroup(createHorizontalDataComponents(layout, lengthLabel, lengthData))
                .addGroup(createHorizontalDataComponents(layout, noteLabel, noteData));
    }

    @Override
    protected GroupLayout.Group getVerticalLayoutWithComponents(final GroupLayout layout, final GroupLayout.Group group) {
        return group
                .addGroup(createVerticalComponents(layout, numberLabel, numberData))
                .addGap(VERTICAL_GAP_SIZE)
                .addGroup(createVerticalComponents(layout, nameLabel, nameData))
                .addGap(VERTICAL_GAP_SIZE)
                .addGroup(createVerticalComponents(layout, lengthLabel, lengthData))
                .addGap(VERTICAL_GAP_SIZE)
                .addGroup(createVerticalComponents(layout, noteLabel, noteData))
                .addGap(VERTICAL_GAP_SIZE);
    }

}
