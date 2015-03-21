package cz.vhromada.catalog.gui.category;

import javax.swing.GroupLayout;
import javax.swing.JLabel;
import javax.swing.JTextField;

import cz.vhromada.catalog.facade.to.BookCategoryTO;
import cz.vhromada.catalog.gui.commons.AbstractInfoDialog;

/**
 * A class represents dialog for book category.
 *
 * @author Vladimir Hromada
 */
public class BookCategoryInfoDialog extends AbstractInfoDialog<BookCategoryTO> {

    /**
     * SerialVersionUID
     */
    private static final long serialVersionUID = 1L;

    /**
     * Label for name
     */
    private JLabel nameLabel = new JLabel("Name");

    /**
     * Text field for name
     */
    private JTextField nameData = new JTextField();

    /**
     * Label for note
     */
    private JLabel noteLabel = new JLabel("Note");

    /**
     * Text field for note
     */
    private JTextField noteData = new JTextField();

    /**
     * Creates a new instance of BookCategoryInfoDialog.
     */
    public BookCategoryInfoDialog() {
        init();
    }

    /**
     * Creates a new instance of BookCategoryInfoDialog.
     *
     * @param bookCategory TO for book category
     * @throws IllegalArgumentException if TO for book category is null
     */
    public BookCategoryInfoDialog(final BookCategoryTO bookCategory) {
        super(bookCategory);

        init();
        this.nameData.setText(bookCategory.getName());
        this.noteData.setText(bookCategory.getNote());
    }

    @Override
    protected void initComponents() {
        initLabelComponent(nameLabel, nameData);
        initLabelComponent(noteLabel, noteData);

        addInputValidator(nameData);
    }

    @Override
    protected BookCategoryTO processData(final BookCategoryTO objectData) {
        final BookCategoryTO bookCategory = objectData == null ? new BookCategoryTO() : objectData;
        bookCategory.setName(nameData.getText());
        bookCategory.setNote(noteData.getText());

        return bookCategory;
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
                .addGroup(createHorizontalComponents(layout, noteLabel, noteData));
    }

    @Override
    protected GroupLayout.Group getVerticalLayoutWithComponents(final GroupLayout layout, final GroupLayout.Group group) {
        return group
                .addGroup(createVerticalComponents(layout, nameLabel, nameData))
                .addGap(VERTICAL_GAP_SIZE)
                .addGroup(createVerticalComponents(layout, noteLabel, noteData));
    }

}
