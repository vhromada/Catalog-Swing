package cz.vhromada.catalog.gui.show;

import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

import javax.swing.GroupLayout;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JLabel;
import javax.swing.JSpinner;
import javax.swing.JTextField;
import javax.swing.SpinnerNumberModel;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import cz.vhromada.catalog.commons.Constants;
import cz.vhromada.catalog.facade.GenreFacade;
import cz.vhromada.catalog.facade.to.GenreTO;
import cz.vhromada.catalog.facade.to.ShowTO;
import cz.vhromada.catalog.gui.commons.AbstractInfoDialog;
import cz.vhromada.catalog.gui.commons.CatalogSwingConstants;
import cz.vhromada.catalog.gui.commons.DialogResult;
import cz.vhromada.catalog.gui.commons.Picture;
import cz.vhromada.catalog.gui.genre.GenreChooseDialog;
import cz.vhromada.validators.Validators;

/**
 * A class represents dialog for show.
 *
 * @author Vladimir Hromada
 */
public class ShowInfoDialog extends AbstractInfoDialog<ShowTO> {

    /**
     * SerialVersionUID
     */
    private static final long serialVersionUID = 1L;

    /**
     * Facade for genres
     */
    private GenreFacade genreFacade;

    /**
     * List of TO for genre
     */
    private List<GenreTO> genres = new ArrayList<>();

    /**
     * Label for czech name
     */
    private JLabel czechNameLabel = new JLabel("Czech name");

    /**
     * Text field for czech name
     */
    private JTextField czechNameData = new JTextField();

    /**
     * Label for original name
     */
    private JLabel originalNameLabel = new JLabel("Original name");

    /**
     * Text field for original name
     */
    private JTextField originalNameData = new JTextField();

    /**
     * Label for ČSFD
     */
    private JLabel csfdLabel = new JLabel("ČSFD");

    /**
     * Text field for ČSFD
     */
    private JTextField csfdData = new JTextField();

    /**
     * Check box for IMDB code
     */
    private JCheckBox imdbCodeLabel = new JCheckBox("IMDB code");

    /**
     * Spinner for IMDB code
     */
    private JSpinner imdbCodeData = new JSpinner(new SpinnerNumberModel(1, 1, Constants.MAX_IMDB_CODE, 1));

    /**
     * Label for czech Wikipedia
     */
    private JLabel wikiCzLabel = new JLabel("Czech Wikipedia");

    /**
     * Text field for czech Wikipedia
     */
    private JTextField wikiCzData = new JTextField();

    /**
     * Label for english Wikipedia
     */
    private JLabel wikiEnLabel = new JLabel("English Wikipedia");

    /**
     * Text field for english Wikipedia
     */
    private JTextField wikiEnData = new JTextField();

    /**
     * Label for picture
     */
    private JLabel pictureLabel = new JLabel("Picture");

    /**
     * Text field for picture
     */
    private JTextField pictureData = new JTextField();

    /**
     * Label for note
     */
    private JLabel noteLabel = new JLabel("Note");

    /**
     * Text field for note
     */
    private JTextField noteData = new JTextField();

    /**
     * Label for genres
     */
    private JLabel genreLabel = new JLabel("Genres");

    /**
     * Data with genres
     */
    private JLabel genreData = new JLabel();

    /**
     * Button for genres
     */
    private JButton genresButton = new JButton("Change genres", Picture.CHOOSE.getIcon());

    /**
     * Creates a new instance of ShowInfoDialog.
     *
     * @param genreFacade facade for genres
     * @throws IllegalArgumentException if facade for genres is null
     */
    public ShowInfoDialog(final GenreFacade genreFacade) {
        init();
        setGenreFacade(genreFacade);
        imdbCodeLabel.setSelected(false);
        imdbCodeData.setEnabled(false);
    }

    /**
     * Creates a new instance of ShowInfoDialog.
     *
     * @param genreFacade facade for genres
     * @param show        TO for show
     * @throws IllegalArgumentException if facade for genres is null
     *                                  or TO for show is null
     */
    public ShowInfoDialog(final GenreFacade genreFacade, final ShowTO show) {
        super(show);

        init();
        setGenreFacade(genreFacade);
        this.genres = show.getGenres();
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
        this.pictureData.setText(show.getPicture());
        this.noteData.setText(show.getNote());
        this.genreData.setText(getGenres());
    }

    @Override
    protected void initComponents() {
        initLabelComponent(czechNameLabel, czechNameData);
        initLabelComponent(originalNameLabel, originalNameData);
        initLabelComponent(csfdLabel, csfdData);
        initLabelComponent(wikiCzLabel, wikiCzData);
        initLabelComponent(wikiEnLabel, wikiEnData);
        initLabelComponent(pictureLabel, pictureData);
        initLabelComponent(noteLabel, noteData);
        initLabelComponent(genreLabel, genreData);

        addInputValidator(czechNameData);
        addInputValidator(originalNameData);

        genreData.setFocusable(false);

        imdbCodeLabel.addChangeListener(new ChangeListener() {

            @Override
            public void stateChanged(final ChangeEvent e) {
                imdbCodeData.setEnabled(imdbCodeLabel.isSelected());
            }

        });

        genresButton.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(final ActionEvent e) {
                genresAction();
            }

        });
    }

    @Override
    protected ShowTO processData(final ShowTO objectData) {
        final ShowTO show = objectData == null ? new ShowTO() : objectData;
        show.setCzechName(czechNameData.getText());
        show.setOriginalName(originalNameData.getText());
        show.setCsfd(csfdData.getText());
        show.setImdbCode(imdbCodeLabel.isSelected() ? (Integer) imdbCodeData.getValue() : -1);
        show.setWikiCz(wikiCzData.getText());
        show.setWikiEn(wikiEnData.getText());
        show.setPicture(pictureData.getText());
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
        Validators.validateArgumentNotNull(genreFacade, "Facade for genres");

        this.genreFacade = genreFacade;
    }

    /**
     * Returns genres.
     *
     * @return genres
     */
    private String getGenres() {
        if (genres == null || genres.isEmpty()) {
            return "";
        }

        final StringBuilder genresString = new StringBuilder();
        for (final GenreTO genre : genres) {
            genresString.append(genre.getName());
            genresString.append(", ");
        }

        return genresString.substring(0, genresString.length() - 2);
    }

    /**
     * Performs action for button Genres.
     */
    private void genresAction() {
        EventQueue.invokeLater(new Runnable() {

            @Override
            public void run() {
                final GenreChooseDialog dialog = new GenreChooseDialog(genreFacade, new ArrayList<>(genres));
                dialog.setVisible(true);
                if (dialog.getReturnStatus() == DialogResult.OK) {
                    genres.clear();
                    genres.addAll(dialog.getGenres());
                    genreData.setText(getGenres());
                    setOkButtonEnabled(isInputValid());
                }
            }

        });
    }

}
