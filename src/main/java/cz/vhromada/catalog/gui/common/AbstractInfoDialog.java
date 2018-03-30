package cz.vhromada.catalog.gui.common;

import java.awt.EventQueue;
import java.util.ArrayList;
import java.util.List;

import javax.swing.ButtonModel;
import javax.swing.GroupLayout;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComponent;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JRadioButton;
import javax.swing.JSpinner;
import javax.swing.WindowConstants;
import javax.swing.text.JTextComponent;

import cz.vhromada.catalog.common.Language;
import cz.vhromada.catalog.entity.Genre;
import cz.vhromada.catalog.facade.GenreFacade;
import cz.vhromada.catalog.facade.PictureFacade;
import cz.vhromada.catalog.gui.genre.GenreChooseDialog;
import cz.vhromada.catalog.gui.picture.PictureChooseDialog;

import org.springframework.util.Assert;

/**
 * An abstract class represents dialog for adding or updating data.
 *
 * @param <T> type of data
 * @author Vladimir Hromada
 */
public abstract class AbstractInfoDialog<T> extends JDialog {

    /**
     * Horizontal long component size
     */
    protected static final int HORIZONTAL_LONG_COMPONENT_SIZE = 310;

    /**
     * Vertical gap size
     */
    protected static final int VERTICAL_GAP_SIZE = 10;

    /**
     * SerialVersionUID
     */
    private static final long serialVersionUID = 1L;

    /**
     * Horizontal label size
     */
    private static final int HORIZONTAL_LABEL_DIALOG_SIZE = 100;

    /**
     * Horizontal data size
     */
    private static final int HORIZONTAL_DATA_DIALOG_SIZE = 200;

    /**
     * Horizontal button size
     */
    private static final int HORIZONTAL_BUTTON_SIZE = 96;

    /**
     * Horizontal size of gap between label and data
     */
    private static final int HORIZONTAL_DATA_GAP_SIZE = 10;

    /**
     * Horizontal selectable component gap size
     */
    private static final int HORIZONTAL_SELECTABLE_COMPONENT_GAP_SIZE = 110;

    /**
     * Horizontal button gap size
     */
    private static final int HORIZONTAL_BUTTON_GAP_SIZE = 32;

    /**
     * Horizontal size of gap between button
     */
    private static final int HORIZONTAL_BUTTONS_GAP_SIZE = 54;

    /**
     * Horizontal gap size
     */
    private static final int HORIZONTAL_GAP_SIZE = 20;

    /**
     * Vertical long gap size
     */
    private static final int VERTICAL_LONG_GAP_SIZE = 20;

    /**
     * Return status
     */
    private DialogResult returnStatus = DialogResult.CANCEL;

    /**
     * Data
     */
    private T data;

    /**
     * Button OK
     */
    private final JButton okButton = new JButton("OK", Picture.OK.getIcon());

    /**
     * Button Cancel
     */
    private final JButton cancelButton = new JButton("Cancel", Picture.CANCEL.getIcon());

    /**
     * Creates a new instance of AbstractInfoDialog.
     */
    public AbstractInfoDialog() {
        this("Add", Picture.ADD);

        okButton.setEnabled(false);
    }

    /**
     * Creates a new instance of AbstractInfoDialog.
     *
     * @param data data
     * @throws IllegalArgumentException if data are null
     */
    public AbstractInfoDialog(final T data) {
        this("Update", Picture.UPDATE);

        Assert.notNull(data, "Data mustn't be null.");

        this.data = data;
    }

    /**
     * Creates a new instance of AbstractInfoDialog.
     *
     * @param name    name
     * @param picture picture
     */
    private AbstractInfoDialog(final String name, final Picture picture) {
        super(new JFrame(), name, true);

        initDialog(picture);

        okButton.addActionListener(e -> okAction());

        cancelButton.addActionListener(e -> cancelAction());
    }

    /**
     * Returns return status.
     *
     * @return return status
     */
    public DialogResult getReturnStatus() {
        return returnStatus;
    }

    /**
     * Returns data.
     *
     * @return data
     * @throws IllegalStateException if data haven't been set
     */
    public T getData() {
        Assert.state(data != null, "Data mustn't be null.");

        return data;
    }

    /**
     * Initializes components.
     */
    protected abstract void initComponents();

    /**
     * Returns object with filled data.
     *
     * @param objectData object for filling data
     * @return object with filled data
     */
    protected abstract T processData(T objectData);

    /**
     * Returns horizontal layout with added components.
     *
     * @param layout horizontal layout
     * @param group  group in vertical layout
     * @return horizontal layout with added components
     */
    protected abstract GroupLayout.Group getHorizontalLayoutWithComponents(GroupLayout layout, GroupLayout.Group group);

    /**
     * Returns vertical layout with added components.
     *
     * @param layout vertical layout
     * @param group  group in vertical layout
     * @return vertical layout with added components
     */
    protected abstract GroupLayout.Group getVerticalLayoutWithComponents(GroupLayout layout, GroupLayout.Group group);

    /**
     * Initializes.
     */
    protected final void init() {
        initComponents();
        createLayout();
    }

    /**
     * Initializes label with component.
     *
     * @param label     label
     * @param component component
     */
    protected static void initLabelComponent(final JLabel label, final JComponent component) {
        label.setLabelFor(component);
        label.setFocusable(false);
    }

    /**
     * Adds validator to input.
     *
     * @param input input
     */
    protected final void addInputValidator(final JTextComponent input) {
        input.getDocument().addDocumentListener(new InputValidator(okButton) {

            @Override
            public boolean isInputValid() {
                return AbstractInfoDialog.this.isInputValid();
            }

        });
    }

    /**
     * Adds validator to spinner.
     *
     * @param spinner spinner
     */
    protected final void addSpinnerValidator(final JSpinner spinner) {
        spinner.addChangeListener(e -> okButton.setEnabled(isInputValid()));
    }

    /**
     * Returns true if input is valid.
     *
     * @return true if input is valid
     */
    protected boolean isInputValid() {
        return true;
    }

    /**
     * Sets enabled to button OK.
     *
     * @param enabled true if button should be enabled
     */
    protected final void setOkButtonEnabled(final boolean enabled) {
        okButton.setEnabled(enabled);
    }

    /**
     * Returns genres.
     *
     * @param genres list of genres
     * @return genres
     */
    protected static String getGenres(final List<Genre> genres) {
        if (genres == null || genres.isEmpty()) {
            return "";
        }

        final StringBuilder genresString = new StringBuilder();
        for (final Genre genre : genres) {
            genresString.append(genre.getName());
            genresString.append(", ");
        }

        return genresString.substring(0, genresString.length() - 2);
    }


    /**
     * Returns picture.
     *
     * @param pictures list of pictures
     * @return picture
     */
    protected static String getPicture(final List<Integer> pictures) {
        if (pictures == null || pictures.isEmpty()) {
            return "";
        }

        return String.valueOf(pictures.get(0));
    }

    /**
     * Performs action for button Genres.
     *
     * @param genreFacade facade for genres
     * @param genres      list of genres
     * @param genreData   data with genres
     */
    protected final void genresAction(final GenreFacade genreFacade, final List<Genre> genres, final JLabel genreData) {
        EventQueue.invokeLater(() -> {
            final GenreChooseDialog dialog = new GenreChooseDialog(genreFacade, new ArrayList<>(genres));
            dialog.setVisible(true);
            if (dialog.getReturnStatus() == DialogResult.OK) {
                genres.clear();
                genres.addAll(dialog.getGenres());
                genreData.setText(getGenres(genres));
                setOkButtonEnabled(isInputValid());
            }
        });
    }

    /**
     * Performs action for button Change pictures.
     *
     * @param pictureFacade facade for pictures
     * @param pictures      list of pictures
     * @param pictureData   data with genres
     */
    protected void pictureAction(final PictureFacade pictureFacade, final List<Integer> pictures, final JLabel pictureData) {
        EventQueue.invokeLater(() -> {
            final cz.vhromada.catalog.entity.Picture pictureEntity = new cz.vhromada.catalog.entity.Picture();
            pictureEntity.setId(pictures.isEmpty() ? null : pictures.get(0));
            final PictureChooseDialog dialog = new PictureChooseDialog(pictureFacade, pictureEntity);
            dialog.setVisible(true);
            if (dialog.getReturnStatus() == DialogResult.OK) {
                pictures.clear();
                pictures.add(dialog.getPicture().getId());
                pictureData.setText(getPicture(pictures));
                setOkButtonEnabled(isInputValid());
            }
        });
    }

    /**
     * Initializes language.
     *
     * @param language             language
     * @param czechLanguageData    radio button for czech language
     * @param englishLanguageData  radio button for english language
     * @param frenchLanguageData   radio button for french language
     * @param japaneseLanguageData radio button for japanese language
     * @param slovakLanguageData   radio button for slovak language
     */
    protected static void initLanguage(final Language language, final JRadioButton czechLanguageData, final JRadioButton englishLanguageData,
        final JRadioButton frenchLanguageData, final JRadioButton japaneseLanguageData, final JRadioButton slovakLanguageData) {
        switch (language) {
            case CZ:
                czechLanguageData.setSelected(true);
                break;
            case EN:
                englishLanguageData.setSelected(true);
                break;
            case FR:
                frenchLanguageData.setSelected(true);
                break;
            case JP:
                japaneseLanguageData.setSelected(true);
                break;
            case SK:
                slovakLanguageData.setSelected(true);
                break;
            default:
                throw new IndexOutOfBoundsException("Bad language");
        }
    }

    /**
     * Returns selected language.
     *
     * @param model                button model
     * @param czechLanguageData    radio button for czech language
     * @param englishLanguageData  radio button for english language
     * @param frenchLanguageData   radio button for french language
     * @param japaneseLanguageData radio button for japanese language
     * @return selected language
     */
    //CHECKSTYLE.OFF: ReturnCount
    protected static Language getSelectedLanguage(final ButtonModel model, final JRadioButton czechLanguageData, final JRadioButton englishLanguageData,
        final JRadioButton frenchLanguageData, final JRadioButton japaneseLanguageData) {
        if (model.equals(czechLanguageData.getModel())) {
            return Language.CZ;
        }
        if (model.equals(englishLanguageData.getModel())) {
            return Language.EN;
        }
        if (model.equals(frenchLanguageData.getModel())) {
            return Language.FR;
        }
        if (model.equals(japaneseLanguageData.getModel())) {
            return Language.JP;
        }

        return Language.SK;
    }
    //CHECKSTYLE.ON: ReturnCount

    /**
     * Initializes subtitles.
     *
     * @param subtitles            list of subtitles
     * @param czechSubtitlesData   check box for czech subtitles
     * @param englishSubtitlesData check box for english subtitles
     */
    protected static void initSubtitles(final List<Language> subtitles, final JCheckBox czechSubtitlesData, final JCheckBox englishSubtitlesData) {
        for (final Language language : subtitles) {
            if (language != null) {
                switch (language) {
                    case CZ:
                        czechSubtitlesData.setSelected(true);
                        break;
                    case EN:
                        englishSubtitlesData.setSelected(true);
                        break;
                    default:
                        throw new IndexOutOfBoundsException("Bad subtitles");
                }
            }
        }
    }

    /**
     * Returns selected subtitles.
     *
     * @param czechSubtitlesData   check box for czech subtitles
     * @param englishSubtitlesData check box for english subtitles
     * @return selected subtitles
     */
    protected static List<Language> getSelectedSubtitles(final JCheckBox czechSubtitlesData, final JCheckBox englishSubtitlesData) {
        final List<Language> subtitles = new ArrayList<>();
        if (czechSubtitlesData.isSelected()) {
            subtitles.add(Language.CZ);
        }
        if (englishSubtitlesData.isSelected()) {
            subtitles.add(Language.EN);
        }

        return subtitles;
    }

    /**
     * Creates layout.
     */
    protected final void createLayout() {
        final GroupLayout layout = new GroupLayout(getRootPane());
        getRootPane().setLayout(layout);
        layout.setHorizontalGroup(createHorizontalLayout(layout));
        layout.setVerticalGroup(createVerticalLayout(layout));

        pack();
        setLocationRelativeTo(getRootPane());
    }

    /**
     * Returns horizontal layout for label component with data component.
     *
     * @param layout         layout
     * @param labelComponent label component
     * @param dataComponent  data component
     * @return horizontal layout for label component with data component
     */
    protected final GroupLayout.Group createHorizontalComponents(final GroupLayout layout, final JComponent labelComponent, final JComponent dataComponent) {
        return layout.createSequentialGroup()
            .addComponent(labelComponent, HORIZONTAL_LABEL_DIALOG_SIZE, HORIZONTAL_LABEL_DIALOG_SIZE, HORIZONTAL_LABEL_DIALOG_SIZE)
            .addGap(HORIZONTAL_DATA_GAP_SIZE)
            .addComponent(dataComponent, HORIZONTAL_DATA_DIALOG_SIZE, HORIZONTAL_DATA_DIALOG_SIZE, HORIZONTAL_DATA_DIALOG_SIZE);
    }

    /**
     * Returns horizontal layout for selectable component.
     *
     * @param layout    layout
     * @param component component
     * @return horizontal layout for selectable component
     */
    protected final GroupLayout.Group createHorizontalSelectableComponent(final GroupLayout layout, final JComponent component) {
        return layout.createSequentialGroup()
            .addGap(HORIZONTAL_SELECTABLE_COMPONENT_GAP_SIZE)
            .addComponent(component, HORIZONTAL_DATA_DIALOG_SIZE, HORIZONTAL_DATA_DIALOG_SIZE, HORIZONTAL_DATA_DIALOG_SIZE);
    }

    /**
     * Returns vertical layout for label component with data component.
     *
     * @param layout         layout
     * @param labelComponent label component
     * @param dataComponent  data component
     * @return vertical layout for label component with data component
     */
    protected final GroupLayout.Group createVerticalComponents(final GroupLayout layout, final JComponent labelComponent, final JComponent dataComponent) {
        return layout.createParallelGroup()
            .addComponent(labelComponent, CatalogSwingConstants.VERTICAL_COMPONENT_SIZE, CatalogSwingConstants.VERTICAL_COMPONENT_SIZE,
                CatalogSwingConstants.VERTICAL_COMPONENT_SIZE)
            .addComponent(dataComponent, CatalogSwingConstants.VERTICAL_COMPONENT_SIZE, CatalogSwingConstants.VERTICAL_COMPONENT_SIZE,
                CatalogSwingConstants.VERTICAL_COMPONENT_SIZE);
    }

    /**
     * Initializes dialog.
     *
     * @param picture
     */
    private void initDialog(final Picture picture) {
        setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        setResizable(false);
        setIconImage(picture.getIcon().getImage());
    }

    /**
     * Performs action for button OK.
     */
    private void okAction() {
        returnStatus = DialogResult.OK;
        data = processData(data);
        close();
    }

    /**
     * Performs action for button Cancel.
     */
    private void cancelAction() {
        returnStatus = DialogResult.CANCEL;
        data = null;
        close();
    }

    /**
     * Closes dialog.
     */
    private void close() {
        setVisible(false);
        dispose();
    }

    /**
     * Returns horizontal layout of components.
     *
     * @param layout layout
     * @return horizontal layout of components
     */
    private GroupLayout.Group createHorizontalLayout(final GroupLayout layout) {
        final GroupLayout.Group buttons = layout.createSequentialGroup()
            .addGap(HORIZONTAL_BUTTON_GAP_SIZE)
            .addComponent(okButton, HORIZONTAL_BUTTON_SIZE, HORIZONTAL_BUTTON_SIZE, HORIZONTAL_BUTTON_SIZE)
            .addGap(HORIZONTAL_BUTTONS_GAP_SIZE)
            .addComponent(cancelButton, HORIZONTAL_BUTTON_SIZE, HORIZONTAL_BUTTON_SIZE, HORIZONTAL_BUTTON_SIZE)
            .addGap(HORIZONTAL_BUTTON_GAP_SIZE);


        final GroupLayout.Group components = getHorizontalLayoutWithComponents(layout, layout.createParallelGroup())
            .addGroup(buttons);

        return layout.createSequentialGroup()
            .addGap(HORIZONTAL_GAP_SIZE)
            .addGroup(components)
            .addGap(HORIZONTAL_GAP_SIZE);
    }

    /**
     * Returns vertical layout of components.
     *
     * @param layout layout
     * @return vertical layout of components
     */
    private GroupLayout.Group createVerticalLayout(final GroupLayout layout) {
        final GroupLayout.Group buttons = layout.createParallelGroup()
            .addComponent(okButton, CatalogSwingConstants.VERTICAL_BUTTON_SIZE, CatalogSwingConstants.VERTICAL_BUTTON_SIZE,
                CatalogSwingConstants.VERTICAL_BUTTON_SIZE)
            .addComponent(cancelButton, CatalogSwingConstants.VERTICAL_BUTTON_SIZE, CatalogSwingConstants.VERTICAL_BUTTON_SIZE,
                CatalogSwingConstants.VERTICAL_BUTTON_SIZE);

        final GroupLayout.Group components = layout.createSequentialGroup()
            .addGap(VERTICAL_LONG_GAP_SIZE);

        return getVerticalLayoutWithComponents(layout, components)
            .addGap(VERTICAL_LONG_GAP_SIZE)
            .addGroup(buttons)
            .addGap(VERTICAL_LONG_GAP_SIZE);
    }

}
