package cz.vhromada.catalog.gui.picture;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

import javax.swing.GroupLayout;
import javax.swing.JButton;
import javax.swing.JFileChooser;

import cz.vhromada.catalog.entity.Picture;
import cz.vhromada.catalog.gui.common.AbstractInfoDialog;
import cz.vhromada.catalog.gui.common.CatalogSwingConstants;

/**
 * A class represents dialog for picture.
 *
 * @author Vladimir Hromada
 */
public class PictureInfoDialog extends AbstractInfoDialog<Picture> {

    /**
     * SerialVersionUID
     */
    private static final long serialVersionUID = 1L;

    /**
     * Button for changing media
     */
    private final JButton contentButton = new JButton("Content", cz.vhromada.catalog.gui.common.Picture.ADD.getIcon());

    /**
     * File chooser for content
     */
    private final JFileChooser contentChooser = new JFileChooser();

    /**
     * File
     */
    private File file;

    /**
     * Creates a new instance of PictureInfoDialog.
     *
     * @throws IllegalArgumentException if facade for genres is null
     */
    public PictureInfoDialog() {
        init();
    }

    @Override
    protected void initComponents() {
        contentButton.addActionListener(e -> contentAction());
    }

    @Override
    protected Picture processData(final Picture objectData) {
        if (file == null) {
            return null;
        }

        try {
            final Picture picture = new Picture();
            picture.setContent(Files.readAllBytes(Paths.get(file.getAbsolutePath())));
            return picture;
        } catch (final IOException ex) {
            throw new RuntimeException("Cannot get file content.", ex);
        }
    }

    @Override
    protected GroupLayout.Group getHorizontalLayoutWithComponents(final GroupLayout layout, final GroupLayout.Group group) {
        return group.addComponent(contentButton, HORIZONTAL_LONG_COMPONENT_SIZE, HORIZONTAL_LONG_COMPONENT_SIZE, HORIZONTAL_LONG_COMPONENT_SIZE);
    }

    @Override
    protected GroupLayout.Group getVerticalLayoutWithComponents(final GroupLayout layout, final GroupLayout.Group group) {
        return group.addComponent(contentButton, CatalogSwingConstants.VERTICAL_BUTTON_SIZE, CatalogSwingConstants.VERTICAL_BUTTON_SIZE,
            CatalogSwingConstants.VERTICAL_BUTTON_SIZE);
    }

    /**
     * Performs action for button Upload picture.
     */
    private void contentAction() {
        final int returnValue = contentChooser.showOpenDialog(null);
        if (JFileChooser.APPROVE_OPTION == returnValue) {
            file = contentChooser.getSelectedFile();
            setOkButtonEnabled(true);
        } else {
            file = null;
            setOkButtonEnabled(false);
        }
    }

}
