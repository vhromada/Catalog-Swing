package cz.vhromada.catalog.gui.picture;

import java.util.List;

import cz.vhromada.catalog.entity.Picture;
import cz.vhromada.catalog.facade.PictureFacade;
import cz.vhromada.catalog.gui.common.AbstractListDataModel;
import cz.vhromada.result.Result;

import org.springframework.util.Assert;

/**
 * A class represents data model for list with pictures.
 *
 * @author Vladimir Hromada
 */
public class PicturesListDataModel extends AbstractListDataModel<Picture> {

    /**
     * SerialVersionUID
     */
    private static final long serialVersionUID = 1L;

    /**
     * Facade for pictures
     */
    private final PictureFacade pictureFacade;

    /**
     * Creates a new instance of PicturesListDataModel.
     *
     * @param pictureFacade facade for pictures
     * @throws IllegalArgumentException if facade for pictures is null
     */
    public PicturesListDataModel(final PictureFacade pictureFacade) {
        Assert.notNull(pictureFacade, "Facade for pictures mustn't be null.");

        this.pictureFacade = pictureFacade;
        update();
    }

    @Override
    protected Result<List<Picture>> getData() {
        return pictureFacade.getAll();
    }

    @Override
    protected String getDisplayValue(final Picture dataObject) {
        return String.valueOf(dataObject.getId());
    }

}
