package cz.vhromada.catalog.gui.book;

import java.util.List;

import javax.swing.GroupLayout;
import javax.swing.JLabel;

import cz.vhromada.catalog.commons.Language;
import cz.vhromada.catalog.facade.to.BookTO;
import cz.vhromada.catalog.gui.commons.AbstractDataPanel;

/**
 * A class represents panel with book's data.
 *
 * @author Vladimir Hromada
 */
public class BookDataPanel extends AbstractDataPanel<BookTO> {

    /**
     * SerialVersionUID
     */
    private static final long serialVersionUID = 1L;

    /**
     * Label for author
     */
    private JLabel authorLabel = new JLabel("Author");

    /**
     * Label with author
     */
    private JLabel authorData = new JLabel();

    /**
     * Label for title
     */
    private JLabel titleLabel = new JLabel("Title");

    /**
     * Label with title
     */
    private JLabel titleData = new JLabel();

    /**
     * Label for languages
     */
    private JLabel languagesLabel = new JLabel("Languages");

    /**
     * Label with languages
     */
    private JLabel languagesData = new JLabel();

    /**
     * Label for note
     */
    private JLabel noteLabel = new JLabel("Note");

    /**
     * Label with note
     */
    private JLabel noteData = new JLabel();

    /**
     * Creates a new instance of BookDataPanel.
     *
     * @param book TO for book
     * @throws IllegalArgumentException if TO for book is null
     */
    public BookDataPanel(final BookTO book) {
        updateData(book);

        initData(authorLabel, authorData);
        initData(titleLabel, titleData);
        initData(languagesLabel, languagesData);
        initData(noteLabel, noteData);

        createLayout();
    }

    @Override
    protected void updateComponentData(final BookTO data) {
        authorData.setText(data.getAuthor());
        titleData.setText(data.getTitle());
        languagesData.setText(getLanguages(data));
        noteData.setText(data.getNote());
    }

    @Override
    protected String getCzWikiUrl() {
        throw new IllegalStateException("Getting URL to czech Wikipedia page is not allowed for books.");
    }

    @Override
    protected String getEnWikiUrl() {
        throw new IllegalStateException("Getting URL to english Wikipedia page is not allowed for books.");
    }

    @Override
    protected String getCsfdUrl() {
        throw new IllegalStateException("Getting URL to ČSFD page is not allowed for books.");
    }

    @Override
    protected int getImdbUrl() {
        throw new IllegalStateException("Getting URL to IMDB page is not allowed for books.");
    }

    @Override
    protected GroupLayout.Group getHorizontalLayoutWithComponents(final GroupLayout layout, final GroupLayout.Group group) {
        return group
                .addGroup(createHorizontalDataComponents(layout, authorLabel, authorData))
                .addGroup(createHorizontalDataComponents(layout, titleLabel, titleData))
                .addGroup(createHorizontalDataComponents(layout, languagesLabel, languagesData))
                .addGroup(createHorizontalDataComponents(layout, noteLabel, noteData));
    }

    @Override
    protected GroupLayout.Group getVerticalLayoutWithComponents(final GroupLayout layout, final GroupLayout.Group group) {
        return group
                .addGroup(createVerticalComponents(layout, authorLabel, authorData))
                .addGap(VERTICAL_GAP_SIZE)
                .addGroup(createVerticalComponents(layout, titleLabel, titleData))
                .addGap(VERTICAL_GAP_SIZE)
                .addGroup(createVerticalComponents(layout, languagesLabel, languagesData))
                .addGap(VERTICAL_GAP_SIZE)
                .addGroup(createVerticalComponents(layout, noteLabel, noteData))
                .addGap(VERTICAL_GAP_SIZE);
    }

    /**
     * Returns book's languages.
     *
     * @param book TO for book
     * @return book's languages
     */
    private static String getLanguages(final BookTO book) {
        final List<Language> languages = book.getLanguages();

        if (languages == null || languages.isEmpty()) {
            return "";
        }

        final StringBuilder result = new StringBuilder();
        for (final Language subtitle : languages) {
            result.append(subtitle);
            result.append(", ");
        }

        return result.substring(0, result.length() - 2);
    }

}
