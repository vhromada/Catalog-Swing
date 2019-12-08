package cz.vhromada.catalog.gui.picture

import cz.vhromada.catalog.entity.Picture
import cz.vhromada.catalog.facade.PictureFacade
import cz.vhromada.catalog.gui.common.AbstractInfoDialog
import cz.vhromada.catalog.gui.common.AbstractOverviewDataPanel
import javax.swing.JPanel
import javax.swing.JTabbedPane

/**
 * A class represents panel with pictures' data.
 *
 * @author Vladimir Hromada
 */
class PicturesPanel(private val pictureFacade: PictureFacade) : AbstractOverviewDataPanel<Picture>(PicturesListDataModel(pictureFacade)) {

    override fun getInfoDialog(add: Boolean, data: Picture?): AbstractInfoDialog<Picture> {
        return PictureInfoDialog()
    }

    override fun deleteData() {
        pictureFacade.newData()
    }

    override fun addData(data: Picture) {
        pictureFacade.add(data)
    }

    override fun updateData(data: Picture) {
        throw UnsupportedOperationException("Updating data is not allowed for pictures.")
    }

    override fun removeData(data: Picture) {
        pictureFacade.remove(data)
    }

    override fun duplicatesData(data: Picture) {
        throw UnsupportedOperationException("Duplicating data is not allowed for pictures.")
    }

    override fun moveUpData(data: Picture) {
        pictureFacade.moveUp(data)
    }

    override fun moveDownData(data: Picture) {
        pictureFacade.moveDown(data)
    }

    override fun getDataPanel(data: Picture): JPanel {
        return PictureDataPanel(data)
    }

    override fun updateDataOnChange(dataPanel: JTabbedPane, data: Picture) {
        // Nothing
    }

}
