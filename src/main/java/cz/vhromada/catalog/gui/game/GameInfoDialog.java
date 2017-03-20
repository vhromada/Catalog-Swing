package cz.vhromada.catalog.gui.game;

import javax.swing.GroupLayout;
import javax.swing.JCheckBox;
import javax.swing.JLabel;
import javax.swing.JSpinner;
import javax.swing.JTextField;
import javax.swing.SpinnerNumberModel;

import cz.vhromada.catalog.entity.Game;
import cz.vhromada.catalog.gui.commons.AbstractInfoDialog;
import cz.vhromada.catalog.gui.commons.CatalogSwingConstants;

/**
 * A class represents dialog for game.
 *
 * @author Vladimir Hromada
 */
public class GameInfoDialog extends AbstractInfoDialog<Game> {

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
     * Check box for crack
     */
    private final JCheckBox crackData = new JCheckBox("Crack");

    /**
     * Check box for serial key
     */
    private final JCheckBox serialData = new JCheckBox("Serial key");

    /**
     * Check box for patch
     */
    private final JCheckBox patchData = new JCheckBox("Patch");

    /**
     * Check box for trainer
     */
    private final JCheckBox trainerData = new JCheckBox("Trainer");

    /**
     * Check box for data for trainer
     */
    private final JCheckBox trainerDataData = new JCheckBox("Data for trainer");

    /**
     * Check box for editor
     */
    private final JCheckBox editorData = new JCheckBox("Editor");

    /**
     * Check box for saves
     */
    private final JCheckBox savesData = new JCheckBox("Saves");

    /**
     * Label for other data
     */
    private final JLabel otherDataLabel = new JLabel("Other data");

    /**
     * Text field for other data
     */
    private final JTextField otherDataData = new JTextField();

    /**
     * Label for note
     */
    private final JLabel noteLabel = new JLabel("Note");

    /**
     * Text field for note
     */
    private final JTextField noteData = new JTextField();

    /**
     * Creates a new instance of GameInfoDialog.
     */
    public GameInfoDialog() {
        init();
    }

    /**
     * Creates a new instance of GameInfoDialog.
     *
     * @param game game
     * @throws IllegalArgumentException if game is null
     */
    public GameInfoDialog(final Game game) {
        super(game);

        init();
        this.nameData.setText(game.getName());
        this.wikiCzData.setText(game.getWikiCz());
        this.wikiEnData.setText(game.getWikiEn());
        this.mediaCountData.setValue(game.getMediaCount());
        this.crackData.setSelected(game.getCrack());
        this.serialData.setSelected(game.getSerialKey());
        this.patchData.setSelected(game.getPatch());
        this.trainerData.setSelected(game.getTrainer());
        this.trainerDataData.setSelected(game.getTrainerData());
        this.editorData.setSelected(game.getEditor());
        this.savesData.setSelected(game.getSaves());
        this.otherDataData.setText(game.getOtherData());
        this.noteData.setText(game.getNote());
    }

    @Override
    protected void initComponents() {
        initLabelComponent(nameLabel, nameData);
        initLabelComponent(wikiCzLabel, wikiCzData);
        initLabelComponent(wikiEnLabel, wikiEnData);
        initLabelComponent(mediaCountLabel, mediaCountData);
        initLabelComponent(otherDataLabel, otherDataData);
        initLabelComponent(noteLabel, noteData);

        addInputValidator(nameData);
    }

    @Override
    protected Game processData(final Game objectData) {
        final Game game = objectData == null ? new Game() : objectData;
        game.setName(nameData.getText());
        game.setWikiCz(wikiCzData.getText());
        game.setWikiEn(wikiEnData.getText());
        game.setMediaCount((Integer) mediaCountData.getValue());
        game.setCrack(crackData.isSelected());
        game.setSerialKey(serialData.isSelected());
        game.setPatch(patchData.isSelected());
        game.setTrainer(trainerData.isSelected());
        game.setTrainerData(trainerDataData.isSelected());
        game.setEditor(editorData.isSelected());
        game.setSaves(savesData.isSelected());
        game.setOtherData(otherDataData.getText());
        game.setNote(noteData.getText());

        return game;
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
                .addComponent(crackData, HORIZONTAL_LONG_COMPONENT_SIZE, HORIZONTAL_LONG_COMPONENT_SIZE, HORIZONTAL_LONG_COMPONENT_SIZE)
                .addComponent(serialData, HORIZONTAL_LONG_COMPONENT_SIZE, HORIZONTAL_LONG_COMPONENT_SIZE, HORIZONTAL_LONG_COMPONENT_SIZE)
                .addComponent(patchData, HORIZONTAL_LONG_COMPONENT_SIZE, HORIZONTAL_LONG_COMPONENT_SIZE, HORIZONTAL_LONG_COMPONENT_SIZE)
                .addComponent(trainerData, HORIZONTAL_LONG_COMPONENT_SIZE, HORIZONTAL_LONG_COMPONENT_SIZE, HORIZONTAL_LONG_COMPONENT_SIZE)
                .addComponent(trainerDataData, HORIZONTAL_LONG_COMPONENT_SIZE, HORIZONTAL_LONG_COMPONENT_SIZE, HORIZONTAL_LONG_COMPONENT_SIZE)
                .addComponent(editorData, HORIZONTAL_LONG_COMPONENT_SIZE, HORIZONTAL_LONG_COMPONENT_SIZE, HORIZONTAL_LONG_COMPONENT_SIZE)
                .addComponent(savesData, HORIZONTAL_LONG_COMPONENT_SIZE, HORIZONTAL_LONG_COMPONENT_SIZE, HORIZONTAL_LONG_COMPONENT_SIZE)
                .addGroup(createHorizontalComponents(layout, otherDataLabel, otherDataData))
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
                .addComponent(crackData, CatalogSwingConstants.VERTICAL_COMPONENT_SIZE, CatalogSwingConstants.VERTICAL_COMPONENT_SIZE,
                        CatalogSwingConstants.VERTICAL_COMPONENT_SIZE)
                .addGap(VERTICAL_GAP_SIZE)
                .addComponent(serialData, CatalogSwingConstants.VERTICAL_COMPONENT_SIZE, CatalogSwingConstants.VERTICAL_COMPONENT_SIZE,
                        CatalogSwingConstants.VERTICAL_COMPONENT_SIZE)
                .addGap(VERTICAL_GAP_SIZE)
                .addComponent(patchData, CatalogSwingConstants.VERTICAL_COMPONENT_SIZE, CatalogSwingConstants.VERTICAL_COMPONENT_SIZE,
                        CatalogSwingConstants.VERTICAL_COMPONENT_SIZE)
                .addGap(VERTICAL_GAP_SIZE)
                .addComponent(trainerData, CatalogSwingConstants.VERTICAL_COMPONENT_SIZE, CatalogSwingConstants.VERTICAL_COMPONENT_SIZE,
                        CatalogSwingConstants.VERTICAL_COMPONENT_SIZE)
                .addGap(VERTICAL_GAP_SIZE)
                .addComponent(trainerDataData, CatalogSwingConstants.VERTICAL_COMPONENT_SIZE, CatalogSwingConstants.VERTICAL_COMPONENT_SIZE,
                        CatalogSwingConstants.VERTICAL_COMPONENT_SIZE)
                .addGap(VERTICAL_GAP_SIZE)
                .addComponent(editorData, CatalogSwingConstants.VERTICAL_COMPONENT_SIZE, CatalogSwingConstants.VERTICAL_COMPONENT_SIZE,
                        CatalogSwingConstants.VERTICAL_COMPONENT_SIZE)
                .addGap(VERTICAL_GAP_SIZE)
                .addComponent(savesData, CatalogSwingConstants.VERTICAL_COMPONENT_SIZE, CatalogSwingConstants.VERTICAL_COMPONENT_SIZE,
                        CatalogSwingConstants.VERTICAL_COMPONENT_SIZE)
                .addGap(VERTICAL_GAP_SIZE)
                .addGroup(createVerticalComponents(layout, otherDataLabel, otherDataData))
                .addGap(VERTICAL_GAP_SIZE)
                .addGroup(createVerticalComponents(layout, noteLabel, noteData));
    }

}
