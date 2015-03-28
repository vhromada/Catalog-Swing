package cz.vhromada.catalog.gui.book;

import java.util.ArrayList;
import java.util.List;

import javax.swing.GroupLayout;
import javax.swing.JCheckBox;
import javax.swing.JLabel;
import javax.swing.JTextField;

import cz.vhromada.catalog.commons.Language;
import cz.vhromada.catalog.facade.to.BookTO;
import cz.vhromada.catalog.gui.commons.AbstractInfoDialog;
import cz.vhromada.catalog.gui.commons.CatalogSwingConstants;

/**
 * A class represents dialog for book.
 *
 * @author Vladimir Hromada
 */
public class BookInfoDialog extends AbstractInfoDialog<BookTO> {

    /**
     * SerialVersionUID
     */
    private static final long serialVersionUID = 1L;

    /**
     * Label for author
     */
    private JLabel authorLabel = new JLabel("Author");

    /**
     * Spinner for author
     */
    private JTextField authorData = new JTextField();

    /**
     * Label for title
     */
    private JLabel titleLabel = new JLabel("Title");

    /**
     * Text field for title
     */
    private JTextField titleData = new JTextField();

    /**
     * Label for languages
     */
    private JLabel languagesLabel = new JLabel("Languages");

    /**
     * Check box for czech language
     */
    private JCheckBox czechLanguageData = new JCheckBox("Czech");

    /**
     * Check box for english language
     */
    private JCheckBox englishLanguageData = new JCheckBox("English");

    /**
     * Label for note
     */
    private JLabel noteLabel = new JLabel("Note");

    /**
     * Text field for note
     */
    private JTextField noteData = new JTextField();

    /**
     * Creates a new instance of BookInfoDialog.
     */
    public BookInfoDialog() {
        init();
    }

    /**
     * Creates a new instance of BookInfoDialog.
     *
     * @param book TO for book
     * @throws IllegalArgumentException if TO for book is null
     */
    public BookInfoDialog(final BookTO book) {
        super(book);

        init();
        this.authorData.setText(book.getAuthor());
        this.titleData.setText(book.getTitle());
        for (final Language language : book.getLanguages()) {
            initLanguage(language);
        }
        this.noteData.setText(book.getNote());
    }

    @Override
    protected void initComponents() {
        initLabelComponent(authorLabel, authorData);
        initLabelComponent(titleLabel, titleData);
        initLabelComponent(noteLabel, noteData);

        addInputValidator(authorData);
        addInputValidator(titleData);

        languagesLabel.setFocusable(false);
    }

    @Override
    protected BookTO processData(final BookTO objectData) {
        final BookTO book = objectData == null ? new BookTO() : objectData;
        book.setAuthor(authorData.getText());
        book.setTitle(titleData.getText());
        book.setLanguages(getSelectedLanguages());
        book.setNote(noteData.getText());

        return book;
    }

    /**
     * Returns true if input is valid: author isn't empty string, title isn't empty string.
     *
     * @return true if input is valid: author isn't empty string, title isn't empty string,
     */
    @Override
    protected boolean isInputValid() {
        return !authorData.getText().isEmpty() && !titleData.getText().isEmpty();
    }

    @Override
    protected GroupLayout.Group getHorizontalLayoutWithComponents(final GroupLayout layout, final GroupLayout.Group group) {
        return group
                .addGroup(createHorizontalComponents(layout, authorLabel, authorData))
                .addGroup(createHorizontalComponents(layout, titleLabel, titleData))
                .addGroup(createHorizontalComponents(layout, languagesLabel, czechLanguageData))
                .addGroup(createHorizontalSelectableComponent(layout, englishLanguageData))
                .addGroup(createHorizontalComponents(layout, noteLabel, noteData));
    }

    @Override
    protected GroupLayout.Group getVerticalLayoutWithComponents(final GroupLayout layout, final GroupLayout.Group group) {
        return group
                .addGroup(createVerticalComponents(layout, authorLabel, authorData))
                .addGap(VERTICAL_GAP_SIZE)
                .addGroup(createVerticalComponents(layout, titleLabel, titleData))
                .addGap(VERTICAL_GAP_SIZE)
                .addGroup(createVerticalComponents(layout, languagesLabel, czechLanguageData))
                .addGap(VERTICAL_GAP_SIZE)
                .addComponent(englishLanguageData, CatalogSwingConstants.VERTICAL_COMPONENT_SIZE, CatalogSwingConstants.VERTICAL_COMPONENT_SIZE,
                        CatalogSwingConstants.VERTICAL_COMPONENT_SIZE)
                .addGap(VERTICAL_GAP_SIZE)
                .addGroup(createVerticalComponents(layout, noteLabel, noteData));
    }

    /**
     * Initializes language.
     *
     * @param language language
     */
    private void initLanguage(final Language language) {
        if (language != null) {
            switch (language) {
                case CZ:
                    this.czechLanguageData.setSelected(true);
                    break;
                case EN:
                    this.englishLanguageData.setSelected(true);
                    break;
                default:
                    throw new IndexOutOfBoundsException("Bad language");
            }
        }
    }

    /**
     * Returns selected languages.
     *
     * @return selected languages
     */
    private List<Language> getSelectedLanguages() {
        final List<Language> languages = new ArrayList<>();
        if (czechLanguageData.isSelected()) {
            languages.add(Language.CZ);
        }
        if (englishLanguageData.isSelected()) {
            languages.add(Language.EN);
        }

        return languages;
    }

}
