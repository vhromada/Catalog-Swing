package cz.vhromada.catalog.gui.show;

import java.util.ArrayList;
import java.util.List;

import javax.swing.GroupLayout;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JLabel;
import javax.swing.JSpinner;
import javax.swing.JTextField;
import javax.swing.SpinnerNumberModel;

import cz.vhromada.catalog.entity.Genre;
import cz.vhromada.catalog.entity.Show;
import cz.vhromada.catalog.facade.GenreFacade;
import cz.vhromada.catalog.facade.PictureFacade;
import cz.vhromada.catalog.gui.common.AbstractInfoDialog;
import cz.vhromada.catalog.gui.common.CatalogSwingConstants;
import cz.vhromada.catalog.gui.common.Picture;
import cz.vhromada.catalog.utils.Constants;

import org.springframework.util.Assert;

/**
 * A class represents dialog for show.
 *
 * @author Vladimir Hromada
 */
public class ShowInfoDialog extends AbstractInfoDialog<Show> {

    /**
     * SerialVersionUID
     */
    private static final long serialVersionUID = 1L;

    /**
     * Facade for genres
     */
    private GenreFacade genreFacade;

    /**
     * Facade for pictures
     */
    private PictureFacade pictureFacade;

    /**
     * List of genres
     */
    private List<Genre> genres = new ArrayList<>();

    /**
     * List of pictures
     */
    private List<Integer> pictures = new ArrayList<>();

    /**
     * Label for czech name
     */
    private final JLabel czechNameLabel = new JLabel("Czech name");

    /**
     * Text field for czech name
     */
    private final JTextField czechNameData = new JTextField();

    /**
     * Label for original name
     */
    private final JLabel originalNameLabel = new JLabel("Original name");

    /**
     * Text field for original name
     */
    private final JTextField originalNameData = new JTextField();

    /**
     * Label for ČSFD
     */
    private final JLabel csfdLabel = new JLabel("ČSFD");

    /**
     * Text field for ČSFD
     */
    private final JTextField csfdData = new JTextField();

    /**
     * Check box for IMDB code
     */
    private final JCheckBox imdbCodeLabel = new JCheckBox("IMDB code");

    /**
     * Spinner for IMDB code
     */
    private final JSpinner imdbCodeData = new JSpinner(new SpinnerNumberModel(1, 1, Constants.MAX_IMDB_CODE, 1));

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
     * Label for picture
     */
    private final JLabel pictureLabel = new JLabel("Picture");

    /**
     * Data with picture
     */
    private final JLabel pictureData = new JLabel();

    /**
     * Button for changing picture
     */
    private final JButton pictureButton = new JButton("Change picture", Picture.CHOOSE.getIcon());

    /**
     * Label for note
     */
    private final JLabel noteLabel = new JLabel("Note");

    /**
     * Text field for note
     */
    private final JTextField noteData = new JTextField();

    /**
     * Label for genres
     */
    private final JLabel genreLabel = new JLabel("Genres");

    /**
     * Data with genres
     */
    private final JLabel genreData = new JLabel();

    /**
     * Button for genres
     */
    private final JButton genresButton = new JButton("Change genres", Picture.CHOOSE.getIcon());

    /**
     * Creates a new instance of ShowInfoDialog.
     *
     * @param genreFacade   facade for genres
     * @param pictureFacade facade for pictures
     * @throws IllegalArgumentException if facade for genres is null
     *                                  or facade for pictures is null
     */
    public ShowInfoDialog(final GenreFacade genreFacade, final PictureFacade pictureFacade) {
        init();
        setGenreFacade(genreFacade);
        setPictureFacade(pictureFacade);
        imdbCodeLabel.setSelected(false);
        imdbCodeData.setEnabled(false);
    }

    /**
     * Creates a new instance of ShowInfoDialog.
     *
     * @param genreFacade   facade for genres
     * @param pictureFacade facade for pictures
     * @param show          show
     * @throws IllegalArgumentException if facade for genres is null
     *                                  or facade for pictures is null
     *                                  or show is null
     */
    public ShowInfoDialog(final GenreFacade genreFacade, final PictureFacade pictureFacade, final Show show) {
        super(show);

        init();
        setGenreFacade(genreFacade);
        setPictureFacade(pictureFacade);
        this.genres = show.getGenres();
        if (show.getPicture() != null) {
            this.pictures.add(show.getPicture());
        }
        this.czechNameData.setText(show.getCzechName());
        this.originalNameData.setText(show.getOriginalName());
        this.csfdData.setText(show.getCsfd());
        final int imdbCode = show.getImdbCode();
        if (imdbCode > 0) {
            this.imdbCodeLabel.setSelected(true);
            this.imdbCodeData.setValue(show.getImdbCode());
        } else {
            this.imdbCodeLabel.setSelected(false);
            this.imdbCodeData.setEnabled(false);
        }
        this.wikiCzData.setText(show.getWikiCz());
        this.wikiEnData.setText(show.getWikiEn());
        this.pictureData.setText(getPicture(this.pictures));
        this.noteData.setText(show.getNote());
        this.genreData.setText(getGenres(this.genres));
    }

    @Override
    protected void initComponents() {
        initLabelComponent(czechNameLabel, czechNameData);
        initLabelComponent(originalNameLabel, originalNameData);
        initLabelComponent(csfdLabel, csfdData);
        initLabelComponent(wikiCzLabel, wikiCzData);
        initLabelComponent(wikiEnLabel, wikiEnData);
        initLabelComponent(noteLabel, noteData);

        addInputValidator(czechNameData);
        addInputValidator(originalNameData);

        pictureData.setFocusable(false);
        genreData.setFocusable(false);

        imdbCodeLabel.addChangeListener(e -> imdbCodeData.setEnabled(imdbCodeLabel.isSelected()));

        pictureButton.addActionListener(e -> pictureAction(pictureFacade, pictures, pictureData));

        genresButton.addActionListener(e -> genresAction(genreFacade, genres, genreData));
    }

    @Override
    protected Show processData(final Show objectData) {
        final Show show = objectData == null ? new Show() : objectData;
        show.setCzechName(czechNameData.getText());
        show.setOriginalName(originalNameData.getText());
        show.setCsfd(csfdData.getText());
        show.setImdbCode(imdbCodeLabel.isSelected() ? (Integer) imdbCodeData.getValue() : -1);
        show.setWikiCz(wikiCzData.getText());
        show.setWikiEn(wikiEnData.getText());
        show.setPicture(pictures.isEmpty() ? null : pictures.get(0));
        show.setNote(noteData.getText());
        show.setGenres(genres);

        return show;
    }

    /**
     * Returns true if input is valid: czech name isn't empty string, original name isn't empty string, genres isn't empty collection.
     *
     * @return true if input is valid: czech name isn't empty string, original name isn't empty string, genres isn't empty collection
     */
    @Override
    protected boolean isInputValid() {
        return !czechNameData.getText().isEmpty() && !originalNameData.getText().isEmpty() && !genres.isEmpty();
    }

    @Override
    protected GroupLayout.Group getHorizontalLayoutWithComponents(final GroupLayout layout, final GroupLayout.Group group) {
        return group
            .addGroup(createHorizontalComponents(layout, czechNameLabel, czechNameData))
            .addGroup(createHorizontalComponents(layout, originalNameLabel, originalNameData))
            .addGroup(createHorizontalComponents(layout, csfdLabel, csfdData))
            .addGroup(createHorizontalComponents(layout, imdbCodeLabel, imdbCodeData))
            .addGroup(createHorizontalComponents(layout, wikiCzLabel, wikiCzData))
            .addGroup(createHorizontalComponents(layout, wikiEnLabel, wikiEnData))
            .addGroup(createHorizontalComponents(layout, pictureLabel, pictureData))
            .addComponent(pictureButton, HORIZONTAL_LONG_COMPONENT_SIZE, HORIZONTAL_LONG_COMPONENT_SIZE, HORIZONTAL_LONG_COMPONENT_SIZE)
            .addGroup(createHorizontalComponents(layout, noteLabel, noteData))
            .addGroup(createHorizontalComponents(layout, genreLabel, genreData))
            .addComponent(genresButton, HORIZONTAL_LONG_COMPONENT_SIZE, HORIZONTAL_LONG_COMPONENT_SIZE, HORIZONTAL_LONG_COMPONENT_SIZE);
    }

    @Override
    protected GroupLayout.Group getVerticalLayoutWithComponents(final GroupLayout layout, final GroupLayout.Group group) {
        return group
            .addGroup(createVerticalComponents(layout, czechNameLabel, czechNameData))
            .addGap(VERTICAL_GAP_SIZE)
            .addGroup(createVerticalComponents(layout, originalNameLabel, originalNameData))
            .addGap(VERTICAL_GAP_SIZE)
            .addGroup(createVerticalComponents(layout, csfdLabel, csfdData))
            .addGap(VERTICAL_GAP_SIZE)
            .addGroup(createVerticalComponents(layout, imdbCodeLabel, imdbCodeData))
            .addGap(VERTICAL_GAP_SIZE)
            .addGroup(createVerticalComponents(layout, wikiCzLabel, wikiCzData))
            .addGap(VERTICAL_GAP_SIZE)
            .addGroup(createVerticalComponents(layout, wikiEnLabel, wikiEnData))
            .addGap(VERTICAL_GAP_SIZE)
            .addGroup(createVerticalComponents(layout, pictureLabel, pictureData))
            .addGap(VERTICAL_GAP_SIZE)
            .addComponent(pictureButton, CatalogSwingConstants.VERTICAL_BUTTON_SIZE, CatalogSwingConstants.VERTICAL_BUTTON_SIZE,
                CatalogSwingConstants.VERTICAL_BUTTON_SIZE)
            .addGap(VERTICAL_GAP_SIZE)
            .addGroup(createVerticalComponents(layout, noteLabel, noteData))
            .addGap(VERTICAL_GAP_SIZE)
            .addGroup(createVerticalComponents(layout, genreLabel, genreData))
            .addGap(VERTICAL_GAP_SIZE)
            .addComponent(genresButton, CatalogSwingConstants.VERTICAL_BUTTON_SIZE, CatalogSwingConstants.VERTICAL_BUTTON_SIZE,
                CatalogSwingConstants.VERTICAL_BUTTON_SIZE);
    }

    /**
     * Initializes facade for genres.
     *
     * @throws IllegalArgumentException if facade for genres is null
     */
    private void setGenreFacade(final GenreFacade genreFacade) {
        Assert.notNull(genreFacade, "Facade for genres mustn't be null.");

        this.genreFacade = genreFacade;
    }

    /**
     * Initializes facade for pictures.
     *
     * @throws IllegalArgumentException if facade for pictures is null
     */
    private void setPictureFacade(final PictureFacade pictureFacade) {
        Assert.notNull(pictureFacade, "Facade for pictures mustn't be null.");

        this.pictureFacade = pictureFacade;
    }

}
