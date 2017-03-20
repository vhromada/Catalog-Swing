package cz.vhromada.catalog.gui.season;

import javax.swing.ButtonGroup;
import javax.swing.GroupLayout;
import javax.swing.JCheckBox;
import javax.swing.JLabel;
import javax.swing.JRadioButton;
import javax.swing.JSpinner;
import javax.swing.JTextField;
import javax.swing.SpinnerNumberModel;

import cz.vhromada.catalog.entity.Season;
import cz.vhromada.catalog.gui.common.AbstractInfoDialog;
import cz.vhromada.catalog.gui.common.CatalogSwingConstants;
import cz.vhromada.catalog.utils.Constants;

/**
 * A class represents dialog for season.
 *
 * @author Vladimir Hromada
 */
public class SeasonInfoDialog extends AbstractInfoDialog<Season> {

    /**
     * SerialVersionUID
     */
    private static final long serialVersionUID = 1L;

    /**
     * Label for season's number
     */
    private final JLabel numberLabel = new JLabel("Number of season");

    /**
     * Spinner for season's number
     */
    private final JSpinner numberData = new JSpinner(new SpinnerNumberModel(1, 1, Integer.MAX_VALUE, 1));

    /**
     * Label for starting year
     */
    private final JLabel startYearLabel = new JLabel("Starting year");

    /**
     * Spinner for starting year
     */
    private final JSpinner startYearData = new JSpinner(new SpinnerNumberModel(Constants.CURRENT_YEAR, Constants.MIN_YEAR, Constants.CURRENT_YEAR, 1));

    /**
     * Label for ending year
     */
    private final JLabel endYearLabel = new JLabel("Ending year");

    /**
     * Spinner for ending year
     */
    private final JSpinner endYearData = new JSpinner(new SpinnerNumberModel(Constants.CURRENT_YEAR, Constants.MIN_YEAR, Constants.CURRENT_YEAR, 1));

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
     * Label for note
     */
    private final JLabel noteLabel = new JLabel("Note");

    /**
     * Text field for note
     */
    private final JTextField noteData = new JTextField();

    /**
     * Creates a new instance of SeasonInfoDialog.
     */
    public SeasonInfoDialog() {
        init();

        setOkButtonEnabled(true);
    }

    /**
     * Creates a new instance of SeasonInfoDialog.
     *
     * @param season  season
     * @throws IllegalArgumentException if season is null
     */
    public SeasonInfoDialog(final Season season) {
        super(season);

        init();
        this.numberData.setValue(season.getNumber());
        this.startYearData.setValue(season.getStartYear());
        this.endYearData.setValue(season.getEndYear());
        initLanguage(season.getLanguage(), this.czechLanguageData, this.englishLanguageData, this.frenchLanguageData, this.japaneseLanguageData,
                this.slovakLanguageData);
        initSubtitles(season.getSubtitles(), this.czechSubtitlesData, this.englishSubtitlesData);
        this.noteData.setText(season.getNote());
    }

    @Override
    protected void initComponents() {
        initLabelComponent(numberLabel, numberData);
        initLabelComponent(startYearLabel, startYearData);
        initLabelComponent(endYearLabel, endYearData);
        initLabelComponent(noteLabel, noteData);

        languagesButtonGroup.add(czechLanguageData);
        languagesButtonGroup.add(englishLanguageData);
        languagesButtonGroup.add(frenchLanguageData);
        languagesButtonGroup.add(japaneseLanguageData);
        languagesButtonGroup.add(slovakLanguageData);

        languageLabel.setFocusable(false);
        subtitlesLabel.setFocusable(false);

        addSpinnerValidator(startYearData);
        addSpinnerValidator(endYearData);
    }

    @Override
    protected Season processData(final Season objectData) {
        final Season season = objectData == null ? new Season() : objectData;
        season.setNumber((Integer) numberData.getValue());
        season.setStartYear((Integer) startYearData.getValue());
        season.setEndYear((Integer) endYearData.getValue());
        season.setLanguage(getSelectedLanguage(languagesButtonGroup.getSelection(), czechLanguageData, englishLanguageData, frenchLanguageData,
                japaneseLanguageData));
        season.setSubtitles(getSelectedSubtitles(czechSubtitlesData, englishSubtitlesData));
        season.setNote(noteData.getText());

        return season;
    }

    /**
     * Returns true if input is valid: starting year isn't greater than ending year.
     *
     * @return true if input is valid: starting year isn't greater than ending year
     */
    @Override
    protected boolean isInputValid() {
        return (Integer) startYearData.getValue() <= (Integer) endYearData.getValue();
    }

    @Override
    protected GroupLayout.Group getHorizontalLayoutWithComponents(final GroupLayout layout, final GroupLayout.Group group) {
        return group
                .addGroup(createHorizontalComponents(layout, numberLabel, numberData))
                .addGroup(createHorizontalComponents(layout, startYearLabel, startYearData))
                .addGroup(createHorizontalComponents(layout, endYearLabel, endYearData))
                .addGroup(createHorizontalComponents(layout, languageLabel, czechLanguageData))
                .addGroup(createHorizontalSelectableComponent(layout, englishLanguageData))
                .addGroup(createHorizontalSelectableComponent(layout, frenchLanguageData))
                .addGroup(createHorizontalSelectableComponent(layout, japaneseLanguageData))
                .addGroup(createHorizontalSelectableComponent(layout, slovakLanguageData))
                .addGroup(createHorizontalComponents(layout, subtitlesLabel, czechSubtitlesData))
                .addGroup(createHorizontalSelectableComponent(layout, englishSubtitlesData))
                .addGroup(createHorizontalComponents(layout, noteLabel, noteData));
    }

    @Override
    protected GroupLayout.Group getVerticalLayoutWithComponents(final GroupLayout layout, final GroupLayout.Group group) {
        return group
                .addGroup(createVerticalComponents(layout, numberLabel, numberData))
                .addGap(VERTICAL_GAP_SIZE)
                .addGroup(createVerticalComponents(layout, startYearLabel, startYearData))
                .addGap(VERTICAL_GAP_SIZE)
                .addGroup(createVerticalComponents(layout, endYearLabel, endYearData))
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
                .addGroup(createVerticalComponents(layout, noteLabel, noteData));
    }

}
