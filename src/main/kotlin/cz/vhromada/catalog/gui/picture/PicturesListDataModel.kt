package cz.vhromada.catalog.gui.picture

import cz.vhromada.catalog.entity.Picture
import cz.vhromada.catalog.facade.PictureFacade
import cz.vhromada.catalog.gui.common.AbstractListDataModel
import cz.vhromada.common.result.Result

/**
 * A class represents data model for list with pictures.
 *
 * @author Vladimir Hromada
 */
class PicturesListDataModel(private val pictureFacade: PictureFacade) : AbstractListDataModel<Picture>() {

    init {
        update()
    }

    override fun getData(): Result<List<Picture>> {
        return pictureFacade.getAll()
    }

    override fun getDisplayValue(dataObject: Picture): String {
        return dataObject.id.toString()
    }

}
