package cz.vhromada.catalog.gui.picture;

import javax.swing.JPanel;
import javax.swing.JTabbedPane;

import cz.vhromada.catalog.entity.Picture;
import cz.vhromada.catalog.facade.PictureFacade;
import cz.vhromada.catalog.gui.common.AbstractInfoDialog;
import cz.vhromada.catalog.gui.common.AbstractOverviewDataPanel;

/**
 * A class represents panel with pictures' data.
 *
 * @author Vladimir Hromada
 */
public class PicturesPanel extends AbstractOverviewDataPanel<Picture> {

    /**
     * SerialVersionUID
     */
    private static final long serialVersionUID = 1L;

    /**
     * Facade for pictures
     */
    private final PictureFacade pictureFacade;

    /**
     * Creates a new instance of PicturesPanel.
     *
     * @param pictureFacade facade for pictures
     * @throws IllegalArgumentException if facade for pictures is null
     */
    public PicturesPanel(final PictureFacade pictureFacade) {
        super(getPicturesListDataModel(pictureFacade));

        this.pictureFacade = pictureFacade;
    }

    @Override
    protected AbstractInfoDialog<Picture> getInfoDialog(final boolean add, final Picture data) {
        return new PictureInfoDialog();
    }

    @Override
    protected void deleteData() {
        pictureFacade.newData();
    }

    @Override
    protected void addData(final Picture data) {
        if (data != null) {
            pictureFacade.add(data);
        }
    }

    @Override
    protected void updateData(final Picture data) {
        throw new UnsupportedOperationException("Updating data is not allowed for pictures.");
    }

    @Override
    protected void removeData(final Picture data) {
        pictureFacade.remove(data);
    }

    @Override
    protected void duplicatesData(final Picture data) {
        throw new UnsupportedOperationException("Duplicating data is not allowed for pictures.");
    }

    @Override
    protected void moveUpData(final Picture data) {
        pictureFacade.moveUp(data);
    }

    @Override
    protected void moveDownData(final Picture data) {
        pictureFacade.moveDown(data);
    }

    @Override
    protected JPanel getDataPanel(final Picture data) {
        return new PictureDataPanel(data);
    }

    @Override
    protected void updateDataOnChange(final JTabbedPane dataPanel, final Picture data) {
    }

    /**
     * Returns data model for list with pictures.
     *
     * @param facade facade for pictures
     * @return data model for list with pictures
     * @throws IllegalArgumentException if facade for pictures is null
     */
    private static PicturesListDataModel getPicturesListDataModel(final PictureFacade facade) {
        return new PicturesListDataModel(facade);
    }

}
