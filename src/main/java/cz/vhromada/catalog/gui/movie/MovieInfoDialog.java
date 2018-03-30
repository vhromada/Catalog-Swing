package cz.vhromada.catalog.gui.movie;

import java.awt.EventQueue;
import java.util.ArrayList;
import java.util.List;

import javax.swing.ButtonGroup;
import javax.swing.GroupLayout;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JLabel;
import javax.swing.JRadioButton;
import javax.swing.JSpinner;
import javax.swing.JTextField;
import javax.swing.SpinnerNumberModel;

import cz.vhromada.catalog.common.Time;
import cz.vhromada.catalog.entity.Genre;
import cz.vhromada.catalog.entity.Medium;
import cz.vhromada.catalog.entity.Movie;
import cz.vhromada.catalog.facade.GenreFacade;
import cz.vhromada.catalog.facade.PictureFacade;
import cz.vhromada.catalog.gui.common.AbstractInfoDialog;
import cz.vhromada.catalog.gui.common.CatalogSwingConstants;
import cz.vhromada.catalog.gui.common.DialogResult;
import cz.vhromada.catalog.gui.common.Picture;
import cz.vhromada.catalog.utils.Constants;

import org.springframework.util.Assert;

/**
 * A class represents dialog for movie.
 *
 * @author Vladimir Hromada
 */
//CHECKSTYLE.OFF: ClassDataAbstractionCoupling
public class MovieInfoDialog extends AbstractInfoDialog<Movie> {

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
     * List of media
     */
    private List<Medium> media = new ArrayList<>();

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
     * Label for year
     */
    private final JLabel yearLabel = new JLabel("Year");

    /**
     * Spinner for year
     */
    private final JSpinner yearData = new JSpinner(new SpinnerNumberModel(Constants.CURRENT_YEAR, Constants.MIN_YEAR, Constants.CURRENT_YEAR, 1));

    /**
     * Label for language
     */
    private final JLabel languageLabel = new JLabel("Language");

    /**
     * Button group for languages
     */
    private final ButtonGroup languagesButtonGroup = new ButtonGroup();

    /**
     * Radio button for czech language
     */
    private final JRadioButton czechLanguageData = new JRadioButton("Czech", true);

    /**
     * Radio button for english language
     */
    private final JRadioButton englishLanguageData = new JRadioButton("English");

    /**
     * Radio button for french language
     */
    private final JRadioButton frenchLanguageData = new JRadioButton("French");

    /**
     * Radio button for japanese language
     */
    private final JRadioButton japaneseLanguageData = new JRadioButton("Japanese");

    /**
     * Radio button for slovak language
     */
    private final JRadioButton slovakLanguageData = new JRadioButton("Slovak");

    /**
     * Label for subtitles
     */
    private final JLabel subtitlesLabel = new JLabel("Subtitles");

    /**
     * Check box for czech subtitles
     */
    private final JCheckBox czechSubtitlesData = new JCheckBox("Czech");

    /**
     * Check box for english subtitles
     */
    private final JCheckBox englishSubtitlesData = new JCheckBox("English");

    /**
     * Label for media
     */
    private final JLabel mediaLabel = new JLabel("Media");

    /**
     * Data with media
     */
    private final JLabel mediaData = new JLabel();

    /**
     * Button for changing media
     */
    private final JButton mediaButton = new JButton("Change media", Picture.CHOOSE.getIcon());

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
     * Button for changing genres
     */
    private final JButton genresButton = new JButton("Change genres", Picture.CHOOSE.getIcon());

    /**
     * Creates a new instance of MovieInfoDialog.
     *
     * @param genreFacade   facade for genres
     * @param pictureFacade facade for pictures
     * @throws IllegalArgumentException if facade for genres is null
     *                                  or facade for pictures is null
     */
    public MovieInfoDialog(final GenreFacade genreFacade, final PictureFacade pictureFacade) {
        init();
        setGenreFacade(genreFacade);
        setPictureFacade(pictureFacade);
        imdbCodeData.setEnabled(false);
    }

    /**
     * Creates a new instance of MovieInfoDialog.
     *
     * @param genreFacade   facade for genres
     * @param pictureFacade facade for pictures
     * @param movie         movie
     * @throws IllegalArgumentException if facade for genres is null
     *                                  or facade for pictures is null
     *                                  or movie is null
     */
    public MovieInfoDialog(final GenreFacade genreFacade, final PictureFacade pictureFacade, final Movie movie) {
        super(movie);

        init();
        setGenreFacade(genreFacade);
        setPictureFacade(pictureFacade);
        this.genres = movie.getGenres();
        this.media = movie.getMedia();
        if (movie.getPicture() != null) {
            this.pictures.add(movie.getPicture());
        }
        this.czechNameData.setText(movie.getCzechName());
        this.originalNameData.setText(movie.getOriginalName());
        this.yearData.setValue(movie.getYear());
        initLanguage(movie.getLanguage(), this.czechLanguageData, this.englishLanguageData, this.frenchLanguageData, this.japaneseLanguageData,
            this.slovakLanguageData);
        initSubtitles(movie.getSubtitles(), this.czechSubtitlesData, this.englishSubtitlesData);
        this.mediaData.setText(getMedia());
        this.csfdData.setText(movie.getCsfd());
        final int imdbCode = movie.getImdbCode();
        if (imdbCode > 0) {
            this.imdbCodeLabel.setSelected(true);
            this.imdbCodeData.setValue(movie.getImdbCode());
        } else {
            this.imdbCodeLabel.setSelected(false);
            this.imdbCodeData.setEnabled(false);
        }
        this.wikiCzData.setText(movie.getWikiCz());
        this.wikiEnData.setText(movie.getWikiEn());
        this.pictureData.setText(getPicture(this.pictures));
        this.noteData.setText(movie.getNote());
        this.genreData.setText(getGenres(this.genres));
    }

    @Override
    protected void initComponents() {
        initLabelComponent(czechNameLabel, czechNameData);
        initLabelComponent(originalNameLabel, originalNameData);
        initLabelComponent(yearLabel, yearData);
        initLabelComponent(csfdLabel, csfdData);
        initLabelComponent(wikiCzLabel, wikiCzData);
        initLabelComponent(wikiEnLabel, wikiEnData);
        initLabelComponent(pictureLabel, pictureData);
        initLabelComponent(noteLabel, noteData);

        addInputValidator(czechNameData);
        addInputValidator(originalNameData);

        languagesButtonGroup.add(czechLanguageData);
        languagesButtonGroup.add(englishLanguageData);
        languagesButtonGroup.add(frenchLanguageData);
        languagesButtonGroup.add(japaneseLanguageData);
        languagesButtonGroup.add(slovakLanguageData);

        languageLabel.setFocusable(false);
        subtitlesLabel.setFocusable(false);
        mediaData.setFocusable(false);
        pictureData.setFocusable(false);
        genreData.setFocusable(false);

        imdbCodeLabel.addChangeListener(e -> imdbCodeData.setEnabled(imdbCodeLabel.isSelected()));

        mediaButton.addActionListener(e -> mediaAction());

        pictureButton.addActionListener(e -> pictureAction(pictureFacade, pictures, pictureData));

        genresButton.addActionListener(e -> genresAction(genreFacade, genres, genreData));
    }

    @Override
    protected Movie processData(final Movie objectData) {
        final Movie movie = objectData == null ? new Movie() : objectData;
        movie.setCzechName(czechNameData.getText());
        movie.setOriginalName(originalNameData.getText());
        movie.setYear((Integer) yearData.getValue());
        movie.setLanguage(getSelectedLanguage(languagesButtonGroup.getSelection(), czechLanguageData, englishLanguageData, frenchLanguageData,
            japaneseLanguageData));
        movie.setSubtitles(getSelectedSubtitles(czechSubtitlesData, englishSubtitlesData));
        movie.setMedia(media);
        movie.setCsfd(csfdData.getText());
        movie.setImdbCode(imdbCodeLabel.isSelected() ? (Integer) imdbCodeData.getValue() : -1);
        movie.setWikiCz(wikiCzData.getText());
        movie.setWikiEn(wikiEnData.getText());
        movie.setPicture(pictures.isEmpty() ? null : pictures.get(0));
        movie.setNote(noteData.getText());
        movie.setGenres(genres);

        return movie;
    }

    /**
     * Returns true if input is valid: czech name isn't empty string, original name isn't empty string, media isn't empty collection, genres isn't empty
     * collection
     *
     * @return true if input is valid: czech name isn't empty string, original name isn't empty string, media isn't empty collection, genres isn't empty
     * collection
     */
    @Override
    protected boolean isInputValid() {
        return !czechNameData.getText().isEmpty() && !originalNameData.getText().isEmpty() && !media.isEmpty() && !genres.isEmpty();
    }

    @Override
    protected GroupLayout.Group getHorizontalLayoutWithComponents(final GroupLayout layout, final GroupLayout.Group group) {
        return group
            .addGroup(createHorizontalComponents(layout, czechNameLabel, czechNameData))
            .addGroup(createHorizontalComponents(layout, originalNameLabel, originalNameData))
            .addGroup(createHorizontalComponents(layout, yearLabel, yearData))
            .addGroup(createHorizontalComponents(layout, languageLabel, czechLanguageData))
            .addGroup(createHorizontalSelectableComponent(layout, englishLanguageData))
            .addGroup(createHorizontalSelectableComponent(layout, frenchLanguageData))
            .addGroup(createHorizontalSelectableComponent(layout, japaneseLanguageData))
            .addGroup(createHorizontalSelectableComponent(layout, slovakLanguageData))
            .addGroup(createHorizontalComponents(layout, subtitlesLabel, czechSubtitlesData))
            .addGroup(createHorizontalSelectableComponent(layout, englishSubtitlesData))
            .addGroup(createHorizontalComponents(layout, mediaLabel, mediaData))
            .addComponent(mediaButton, HORIZONTAL_LONG_COMPONENT_SIZE, HORIZONTAL_LONG_COMPONENT_SIZE, HORIZONTAL_LONG_COMPONENT_SIZE)
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
            .addGroup(createVerticalComponents(layout, yearLabel, yearData))
            .addGap(VERTICAL_GAP_SIZE)
            .addGroup(createVerticalComponents(layout, languageLabel, czechLanguageData))
            .addGap(VERTICAL_GAP_SIZE)
            .addComponent(englishLanguageData, CatalogSwingConstants.VERTICAL_COMPONENT_SIZE, CatalogSwingConstants.VERTICAL_COMPONENT_SIZE,
                CatalogSwingConstants.VERTICAL_COMPONENT_SIZE)
            .addGap(VERTICAL_GAP_SIZE)
            .addComponent(frenchLanguageData, CatalogSwingConstants.VERTICAL_COMPONENT_SIZE, CatalogSwingConstants.VERTICAL_COMPONENT_SIZE,
                CatalogSwingConstants.VERTICAL_COMPONENT_SIZE)
            .addGap(VERTICAL_GAP_SIZE)
            .addComponent(japaneseLanguageData, CatalogSwingConstants.VERTICAL_COMPONENT_SIZE, CatalogSwingConstants.VERTICAL_COMPONENT_SIZE,
                CatalogSwingConstants.VERTICAL_COMPONENT_SIZE)
            .addGap(VERTICAL_GAP_SIZE)
            .addComponent(slovakLanguageData, CatalogSwingConstants.VERTICAL_COMPONENT_SIZE, CatalogSwingConstants.VERTICAL_COMPONENT_SIZE,
                CatalogSwingConstants.VERTICAL_COMPONENT_SIZE)
            .addGap(VERTICAL_GAP_SIZE)
            .addGroup(createVerticalComponents(layout, subtitlesLabel, czechSubtitlesData))
            .addGap(VERTICAL_GAP_SIZE)
            .addComponent(englishSubtitlesData, CatalogSwingConstants.VERTICAL_COMPONENT_SIZE, CatalogSwingConstants.VERTICAL_COMPONENT_SIZE,
                CatalogSwingConstants.VERTICAL_COMPONENT_SIZE)
            .addGap(VERTICAL_GAP_SIZE)
            .addGroup(createVerticalComponents(layout, mediaLabel, mediaData))
            .addGap(VERTICAL_GAP_SIZE)
            .addComponent(mediaButton, CatalogSwingConstants.VERTICAL_BUTTON_SIZE, CatalogSwingConstants.VERTICAL_BUTTON_SIZE,
                CatalogSwingConstants.VERTICAL_BUTTON_SIZE)
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

    /**
     * Returns media.
     *
     * @return media
     */
    private String getMedia() {
        if (media == null || media.isEmpty()) {
            return "";
        }

        final StringBuilder mediaString = new StringBuilder();
        for (final Medium medium : media) {
            mediaString.append(new Time(medium.getLength()));
            mediaString.append(", ");
        }

        return mediaString.substring(0, mediaString.length() - 2);
    }

    /**
     * Performs action for button Change media.
     */
    private void mediaAction() {
        EventQueue.invokeLater(() -> {
            final MediumChooseDialog dialog = new MediumChooseDialog(new ArrayList<>(media));
            dialog.setVisible(true);
            if (dialog.getReturnStatus() == DialogResult.OK) {
                media = dialog.getMedia();
                mediaData.setText(getMedia());
                setOkButtonEnabled(isInputValid());
            }
        });
    }

}
//CHECKSTYLE.ON: ClassDataAbstractionCoupling
