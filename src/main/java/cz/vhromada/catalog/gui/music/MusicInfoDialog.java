package cz.vhromada.catalog.gui.music;

import javax.swing.GroupLayout;
import javax.swing.JLabel;
import javax.swing.JSpinner;
import javax.swing.JTextField;
import javax.swing.SpinnerNumberModel;

import cz.vhromada.catalog.entity.Music;
import cz.vhromada.catalog.gui.common.AbstractInfoDialog;

/**
 * A class represents dialog for music.
 *
 * @author Vladimir Hromada
 */
public class MusicInfoDialog extends AbstractInfoDialog<Music> {

    /**
     * SerialVersionUID
     */
    private static final long serialVersionUID = 1L;

    /**
     * Label for name
     */
    private final JLabel nameLabel = new JLabel("Name");

    /**
     * Text field for name
     */
    private final JTextField nameData = new JTextField();

    /**
     * Label for czech Wikipedia
     */
    private final JLabel wikiCzLabel = new JLabel("Czech Wikipedia");

    /**
     * Text field for czech Wikipedia
     */
    private final JTextField wikiCzData = new JTextField();

    /**
     * Label for english Wikipedia
     */
    private final JLabel wikiEnLabel = new JLabel("English Wikipedia");

    /**
     * Text field for english Wikipedia
     */
    private final JTextField wikiEnData = new JTextField();

    /**
     * Label for count of media
     */
    private final JLabel mediaCountLabel = new JLabel("Count of media");

    /**
     * Spinner for count of media
     */
    private final JSpinner mediaCountData = new JSpinner(new SpinnerNumberModel(1, 1, Integer.MAX_VALUE, 1));

    /**
     * Label for note
     */
    private final JLabel noteLabel = new JLabel("Note");

    /**
     * Text field for note
     */
    private final JTextField noteData = new JTextField();

    /**
     * Creates a new instance of MusicInfoDialog.
     */
    public MusicInfoDialog() {
        init();
    }

    /**
     * Creates a new instance of MusicInfoDialog.
     *
     * @param music music
     * @throws IllegalArgumentException if music is null
     */
    public MusicInfoDialog(final Music music) {
        super(music);

        init();
        this.nameData.setText(music.getName());
        this.wikiCzData.setText(music.getWikiCz());
        this.wikiEnData.setText(music.getWikiEn());
        this.mediaCountData.setValue(music.getMediaCount());
        this.noteData.setText(music.getNote());
    }

    @Override
    protected void initComponents() {
        initLabelComponent(nameLabel, nameData);
        initLabelComponent(wikiCzLabel, wikiCzData);
        initLabelComponent(wikiEnLabel, wikiEnData);
        initLabelComponent(mediaCountLabel, mediaCountData);
        initLabelComponent(noteLabel, noteData);

        addInputValidator(nameData);
    }

    @Override
    protected Music processData(final Music objectData) {
        final Music music = objectData == null ? new Music() : objectData;
        music.setName(nameData.getText());
        music.setWikiCz(wikiCzData.getText());
        music.setWikiEn(wikiEnData.getText());
        music.setMediaCount((Integer) mediaCountData.getValue());
        music.setNote(noteData.getText());

        return music;
    }

    /**
     * Returns true if input is valid: name isn't empty string.
     *
     * @return true if input is valid: name isn't empty string
     */
    @Override
    protected boolean isInputValid() {
        return !nameData.getText().isEmpty();
    }

    @Override
    protected GroupLayout.Group getHorizontalLayoutWithComponents(final GroupLayout layout, final GroupLayout.Group group) {
        return group
            .addGroup(createHorizontalComponents(layout, nameLabel, nameData))
            .addGroup(createHorizontalComponents(layout, wikiCzLabel, wikiCzData))
            .addGroup(createHorizontalComponents(layout, wikiEnLabel, wikiEnData))
            .addGroup(createHorizontalComponents(layout, mediaCountLabel, mediaCountData))
            .addGroup(createHorizontalComponents(layout, noteLabel, noteData));
    }

    @Override
    protected GroupLayout.Group getVerticalLayoutWithComponents(final GroupLayout layout, final GroupLayout.Group group) {
        return group
            .addGroup(createVerticalComponents(layout, nameLabel, nameData))
            .addGap(VERTICAL_GAP_SIZE)
            .addGroup(createVerticalComponents(layout, wikiCzLabel, wikiCzData))
            .addGap(VERTICAL_GAP_SIZE)
            .addGroup(createVerticalComponents(layout, wikiEnLabel, wikiEnData))
            .addGap(VERTICAL_GAP_SIZE)
            .addGroup(createVerticalComponents(layout, mediaCountLabel, mediaCountData))
            .addGap(VERTICAL_GAP_SIZE)
            .addGroup(createVerticalComponents(layout, noteLabel, noteData));
    }

}
