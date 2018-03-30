package cz.vhromada.catalog.gui.picture;

import javax.swing.GroupLayout;
import javax.swing.ImageIcon;
import javax.swing.JLabel;

import cz.vhromada.catalog.entity.Picture;
import cz.vhromada.catalog.gui.common.AbstractDataPanel;

import org.springframework.util.Assert;

/**
 * A class represents panel with picture's data.
 *
 * @author Vladimir Hromada
 */
public class PictureDataPanel extends AbstractDataPanel<Picture> {

    /**
     * SerialVersionUID
     */
    private static final long serialVersionUID = 1L;

    /**
     * Label for picture
     */
    private final JLabel pictureData = new JLabel();

    /**
     * Creates a new instance of PictureDataPanel.
     *
     * @param picture picture
     * @throws IllegalArgumentException if picture is null
     */
    public PictureDataPanel(final Picture picture) {
        updateData(picture);

        pictureData.setFocusable(false);

        createLayout();
    }

    @Override
    protected void updateComponentData(final Picture data) {
        Assert.notNull(data, "picture");

        final byte[] picture = data.getContent();
        if (picture == null) {
            pictureData.setIcon(null);
        } else {
            pictureData.setIcon(new ImageIcon(picture));
        }
    }

    @Override
    protected String getCzWikiUrl() {
        throw new IllegalStateException("Getting URL to czech Wikipedia page is not allowed for pictures.");
    }

    @Override
    protected String getEnWikiUrl() {
        throw new IllegalStateException("Getting URL to english Wikipedia page is not allowed for pictures.");
    }

    @Override
    protected String getCsfdUrl() {
        throw new IllegalStateException("Getting URL to ČSFD page is not allowed for pictures.");
    }

    @Override
    protected int getImdbUrl() {
        throw new IllegalStateException("Getting URL to IMDB page is not allowed for pictures.");
    }

    @Override
    protected GroupLayout.Group getHorizontalLayoutWithComponents(final GroupLayout layout, final GroupLayout.Group group) {
        return group.addComponent(pictureData, HORIZONTAL_PICTURE_SIZE, HORIZONTAL_PICTURE_SIZE, HORIZONTAL_PICTURE_SIZE);
    }

    @Override
    protected GroupLayout.Group getVerticalLayoutWithComponents(final GroupLayout layout, final GroupLayout.Group group) {
        return group.addComponent(pictureData, VERTICAL_PICTURE_SIZE, VERTICAL_PICTURE_SIZE, VERTICAL_PICTURE_SIZE);
    }

}
