package cz.vhromada.catalog.gui.picture;

import java.util.ArrayList;
import java.util.List;

import javax.swing.GroupLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JScrollPane;
import javax.swing.ListSelectionModel;
import javax.swing.WindowConstants;

import cz.vhromada.catalog.entity.Picture;
import cz.vhromada.catalog.facade.PictureFacade;
import cz.vhromada.catalog.gui.common.CatalogSwingConstants;
import cz.vhromada.catalog.gui.common.DialogResult;
import cz.vhromada.validation.result.Result;
import cz.vhromada.validation.result.Status;

import org.springframework.util.Assert;

/**
 * A class represents dialog for choosing picture.
 *
 * @author Vladimir Hromada
 */
public final class PictureChooseDialog extends JDialog {

    /**
     * SerialVersionUID
     */
    private static final long serialVersionUID = 1L;

    /**
     * Horizontal picture size
     */
    private static final int HORIZONTAL_PICTURE_SIZE = 200;

    /**
     * Horizontal scroll pane size
     */
    private static final int HORIZONTAL_SCROLL_PANE_SIZE = 310;

    /**
     * Horizontal button size
     */
    private static final int HORIZONTAL_BUTTON_SIZE = 96;

    /**
     * Horizontal button gap size
     */
    private static final int HORIZONTAL_BUTTON_GAP_SIZE = 82;

    /**
     * Horizontal size of gap between button
     */
    private static final int HORIZONTAL_BUTTONS_GAP_SIZE = 154;

    /**
     * Horizontal gap size
     */
    private static final int HORIZONTAL_GAP_SIZE = 20;

    /**
     * Vertical picture size
     */
    private static final int VERTICAL_PICTURE_SIZE = 180;

    /**
     * Vertical scroll pane size
     */
    private static final int VERTICAL_SCROLL_PANE_SIZE = 300;

    /**
     * Vertical gap size
     */
    private static final int VERTICAL_GAP_SIZE = 20;

    /**
     * Return status
     */
    private DialogResult returnStatus = DialogResult.CANCEL;

    /**
     * Facade for pictures
     */
    private final PictureFacade pictureFacade;

    /**
     * Picture
     */
    private Picture picture;

    /**
     * List with pictures
     */
    private final JList<String> list = new JList<>();

    /**
     * ScrollPane for list with pictures
     */
    private final JScrollPane listScrollPane = new JScrollPane(list);

    /**
     * Label for picture
     */
    private final JLabel pictureData = new JLabel();

    /**
     * Button OK
     */
    private final JButton okButton = new JButton("OK", cz.vhromada.catalog.gui.common.Picture.OK.getIcon());

    /**
     * Button Cancel
     */
    private final JButton cancelButton = new JButton("Cancel", cz.vhromada.catalog.gui.common.Picture.CANCEL.getIcon());

    /**
     * Data model for list for pictures
     */
    private PicturesListDataModel pictureListDataModel;

    /**
     * Creates a new instance of PictureChooseDialog.
     *
     * @param pictureFacade facade for pictures
     * @param picture       picture
     * @throws IllegalArgumentException if facade for pictures is null
     *                                  or picture is null
     */
    public PictureChooseDialog(final PictureFacade pictureFacade, final Picture picture) {
        super(new JFrame(), "Choose", true);

        Assert.notNull(pictureFacade, "Facade for pictures mustn't be null.");
        Assert.notNull(picture, "Pictures mustn't be null.");

        this.pictureFacade = pictureFacade;
        this.picture = picture;
        initComponents();
        setIconImage(cz.vhromada.catalog.gui.common.Picture.CHOOSE.getIcon().getImage());
        updatePicture(picture.getId());
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
     * Returns picture.
     *
     * @return picture
     * @throws IllegalStateException if picture for hasn't been set
     */
    public Picture getPicture() {
        Assert.state(picture != null, "Picture mustn't be null.");

        return picture;
    }

    /**
     * Initializes components.
     */
    private void initComponents() {
        setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        setResizable(false);

        pictureListDataModel = new PicturesListDataModel(pictureFacade);
        list.setModel(pictureListDataModel);
        list.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        list.setSelectedIndices(getSelectedIndexes());
        list.addListSelectionListener(e -> selectionChangeAction());

        okButton.setEnabled(list.getSelectedIndices().length > 0);
        okButton.addActionListener(e -> okAction());

        cancelButton.addActionListener(e -> cancelAction());

        final GroupLayout layout = new GroupLayout(getRootPane());
        getRootPane().setLayout(layout);
        layout.setHorizontalGroup(createHorizontalLayout(layout));
        layout.setVerticalGroup(createVerticalLayout(layout));

        pack();
        setLocationRelativeTo(getRootPane());
    }

    /**
     * Performs action for button OK.
     */
    private void okAction() {
        returnStatus = DialogResult.OK;
        final int[] indexes = list.getSelectedIndices();
        for (final int index : indexes) {
            picture = pictureListDataModel.getObjectAt(index);
        }
        close();
    }

    /**
     * Performs action for button Cancel.
     */
    private void cancelAction() {
        returnStatus = DialogResult.CANCEL;
        picture = null;
        close();
    }

    /**
     * Performs action for selection change.
     */
    private void selectionChangeAction() {
        final int[] indexes = list.getSelectedIndices();
        if (indexes.length > 0) {
            updatePicture(Integer.valueOf(list.getSelectedValue()));
            okButton.setEnabled(true);
        } else {
            updatePicture(null);
            okButton.setEnabled(false);
        }
    }

    /**
     * Closes dialog.
     */
    private void close() {
        setVisible(false);
        dispose();
    }

    /**
     * Returns selected indexes.
     *
     * @return selected indexes
     */
    private int[] getSelectedIndexes() {
        final List<Integer> indexes = new ArrayList<>();
        for (int i = 0; i < pictureListDataModel.getSize(); i++) {
            if (pictureListDataModel.getObjectAt(i).equals(picture)) {
                indexes.add(i);
            }
        }

        final int[] result = new int[indexes.size()];
        for (int i = 0; i < indexes.size(); i++) {
            result[i] = indexes.get(i);
        }

        return result;
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

        final GroupLayout.Group data = layout.createSequentialGroup()
            .addComponent(listScrollPane, HORIZONTAL_SCROLL_PANE_SIZE, HORIZONTAL_SCROLL_PANE_SIZE, HORIZONTAL_SCROLL_PANE_SIZE)
            .addGap(HORIZONTAL_GAP_SIZE)
            .addComponent(pictureData, HORIZONTAL_PICTURE_SIZE, HORIZONTAL_PICTURE_SIZE, HORIZONTAL_PICTURE_SIZE);

        final GroupLayout.Group components = layout.createParallelGroup()
            .addGroup(data)
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

        final GroupLayout.Group data = layout.createParallelGroup()
            .addComponent(listScrollPane, VERTICAL_SCROLL_PANE_SIZE, VERTICAL_SCROLL_PANE_SIZE, VERTICAL_SCROLL_PANE_SIZE)
            .addComponent(pictureData, VERTICAL_PICTURE_SIZE, VERTICAL_PICTURE_SIZE, VERTICAL_PICTURE_SIZE);

        return layout.createSequentialGroup()
            .addGap(VERTICAL_GAP_SIZE)
            .addGroup(data)
            .addGap(VERTICAL_GAP_SIZE)
            .addGroup(buttons)
            .addGap(VERTICAL_GAP_SIZE);
    }

    /**
     * Updates picture.
     *
     * @param id picture's ID
     */
    private void updatePicture(final Integer id) {
        if (id != null) {
            final Result<Picture> pictureResult = pictureFacade.get(id);
            if (Status.OK == pictureResult.getStatus()) {
                pictureData.setIcon(new ImageIcon(pictureResult.getData().getContent()));
            } else {
                throw new IllegalArgumentException("Can't get data. " + pictureResult);
            }
        } else {
            pictureData.setIcon(null);
        }
    }

}
