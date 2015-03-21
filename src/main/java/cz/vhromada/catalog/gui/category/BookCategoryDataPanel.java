package cz.vhromada.catalog.gui.category;

import javax.swing.GroupLayout;
import javax.swing.JLabel;

import cz.vhromada.catalog.facade.to.BookCategoryTO;
import cz.vhromada.catalog.gui.commons.AbstractDataPanel;

/**
 * A class represents panel with book category's data.
 *
 * @author Vladimir Hromada
 */
public class BookCategoryDataPanel extends AbstractDataPanel<BookCategoryTO> {

    /**
     * SerialVersionUID
     */
    private static final long serialVersionUID = 1L;

    /**
     * Label for name
     */
    private JLabel nameLabel = new JLabel("Name");

    /**
     * Label with name
     */
    private JLabel nameData = new JLabel();

    /**
     * Label for note
     */
    private JLabel noteLabel = new JLabel("Note");

    /**
     * Label with note
     */
    private JLabel noteData = new JLabel();

    /**
     * Creates a new instance of BookCategoryDataPanel.
     *
     * @param bookCategory TO for book category
     * @throws IllegalArgumentException if book category is null
     */
    public BookCategoryDataPanel(final BookCategoryTO bookCategory) {
        updateData(bookCategory);

        initData(nameLabel, nameData);
        initData(noteLabel, noteData);

        createLayout();
    }

    @Override
    protected void updateComponentData(final BookCategoryTO data) {
        nameData.setText(data.getName());
        noteData.setText(data.getNote());
    }

    @Override
    protected String getCzWikiUrl() {
        throw new IllegalStateException("Getting URL to czech Wikipedia page is not allowed for book categories.");
    }

    @Override
    protected String getEnWikiUrl() {
        throw new IllegalStateException("Getting URL to english Wikipedia page is not allowed for book categories.");
    }

    @Override
    protected String getCsfdUrl() {
        throw new IllegalStateException("Getting URL to ČSFD page is not allowed for book categories.");
    }

    @Override
    protected int getImdbUrl() {
        throw new IllegalStateException("Getting URL to IMDB page is not allowed for book categories.");
    }

    @Override
    protected GroupLayout.Group getHorizontalLayoutWithComponents(final GroupLayout layout, final GroupLayout.Group group) {
        return group
                .addGroup(createHorizontalDataComponents(layout, nameLabel, nameData))
                .addGroup(createHorizontalDataComponents(layout, noteLabel, noteData));
    }

    @Override
    protected GroupLayout.Group getVerticalLayoutWithComponents(final GroupLayout layout, final GroupLayout.Group group) {
        return group
                .addGroup(createVerticalComponents(layout, nameLabel, nameData))
                .addGap(VERTICAL_GAP_SIZE)
                .addGroup(createVerticalComponents(layout, noteLabel, noteData));
    }

}
