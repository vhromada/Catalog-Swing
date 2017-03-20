package cz.vhromada.catalog.gui.game;

import javax.swing.GroupLayout;
import javax.swing.JButton;
import javax.swing.JLabel;

import cz.vhromada.catalog.entity.Game;
import cz.vhromada.catalog.gui.common.AbstractDataPanel;
import cz.vhromada.catalog.gui.common.WebPageButtonType;

/**
 * A class represents panel with game's data.
 *
 * @author Vladimir Hromada
 */
public class GameDataPanel extends AbstractDataPanel<Game> {

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
     * Label for additional data
     */
    private final JLabel dataLabel = new JLabel("Additional data");

    /**
     * Label with additional data
     */
    private final JLabel dataData = new JLabel();

    /**
     * Label for count of media
     */
    private final JLabel mediaCountLabel = new JLabel("Count of media");

    /**
     * Label with count of media
     */
    private final JLabel mediaCountData = new JLabel();

    /**
     * Label for note
     */
    private final JLabel noteLabel = new JLabel("Note");

    /**
     * Label with note
     */
    private final JLabel noteData = new JLabel();

    /**
     * Button for showing game's czech Wikipedia page
     */
    private final JButton wikiCzButton = new JButton("Czech Wikipedia");

    /**
     * Button for showing game's english Wikipedia page
     */
    private final JButton wikiEnButton = new JButton("English Wikipedia");

    /**
     * URL to czech Wikipedia page about game
     */
    private String wikiCz;

    /**
     * URL to english Wikipedia page about game
     */
    private String wikiEn;

    /**
     * Creates a new instance of GameDataPanel.
     *
     * @param game game
     * @throws IllegalArgumentException if game is null
     */
    public GameDataPanel(final Game game) {
        updateData(game);

        initData(nameLabel, nameData);
        initData(dataLabel, dataData);
        initData(mediaCountLabel, mediaCountData);
        initData(noteLabel, noteData);

        initButton(wikiCzButton, WebPageButtonType.WIKI_CZ);
        initButton(wikiEnButton, WebPageButtonType.WIKI_EN);

        createLayout();
    }

    @Override
    protected void updateComponentData(final Game data) {
        nameData.setText(data.getName());
        dataData.setText(getAdditionalData(data));
        mediaCountData.setText(Integer.toString(data.getMediaCount()));
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
        throw new IllegalStateException("Getting URL to ČSFD page is not allowed for games.");
    }

    @Override
    protected int getImdbUrl() {
        throw new IllegalStateException("Getting URL to IMDB page is not allowed for games.");
    }

    @Override
    protected GroupLayout.Group getHorizontalLayoutWithComponents(final GroupLayout layout, final GroupLayout.Group group) {
        return group
                .addGroup(createHorizontalDataComponents(layout, nameLabel, nameData))
                .addGroup(createHorizontalDataComponents(layout, dataLabel, dataData))
                .addGroup(createHorizontalDataComponents(layout, mediaCountLabel, mediaCountData))
                .addGroup(createHorizontalDataComponents(layout, noteLabel, noteData))
                .addGroup(createHorizontalButtons(layout, wikiCzButton, wikiEnButton));
    }

    @Override
    protected GroupLayout.Group getVerticalLayoutWithComponents(final GroupLayout layout, final GroupLayout.Group group) {
        return group
                .addGroup(createVerticalComponents(layout, nameLabel, nameData))
                .addGap(VERTICAL_GAP_SIZE)
                .addGroup(createVerticalComponents(layout, dataLabel, dataData))
                .addGap(VERTICAL_GAP_SIZE)
                .addGroup(createVerticalComponents(layout, mediaCountLabel, mediaCountData))
                .addGap(VERTICAL_GAP_SIZE)
                .addGroup(createVerticalComponents(layout, noteLabel, noteData))
                .addGap(VERTICAL_GAP_SIZE)
                .addGroup(createVerticalButtons(layout, wikiCzButton, wikiEnButton));
    }

    /**
     * Returns additional data.
     *
     * @param game game
     * @return additional data
     */
    private static String getAdditionalData(final Game game) {
        final StringBuilder result = new StringBuilder();
        if (game.getCrack()) {
            result.append("Crack");
        }
        addAdditionalDataToResult(result, game.getSerialKey(), "serial key");
        addAdditionalDataToResult(result, game.getPatch(), "patch");
        addAdditionalDataToResult(result, game.getTrainer(), "trainer");
        addAdditionalDataToResult(result, game.getTrainerData(), "data for trainer");
        addAdditionalDataToResult(result, game.getEditor(), "editor");
        addAdditionalDataToResult(result, game.getSaves(), "saves");
        if (game.getOtherData() != null && !game.getOtherData().isEmpty()) {
            if (result.length() != 0) {
                result.append(", ");
            }
            result.append(game.getOtherData());
        }

        return result.toString();
    }

}
