package cz.vhromada.catalog.gui.picture

import cz.vhromada.catalog.entity.Picture
import cz.vhromada.catalog.gui.common.AbstractDataPanel
import javax.swing.GroupLayout
import javax.swing.ImageIcon
import javax.swing.JLabel

/**
 * A class represents panel with picture's data.
 *
 * @author Vladimir Hromada
 */
class PictureDataPanel(picture: Picture) : AbstractDataPanel<Picture>() {

    /**
     * Label for picture
     */
    private val pictureData = JLabel()

    init {
        updateData(picture)

        pictureData.isFocusable = false

        createLayout()
    }

    override fun updateComponentData(data: Picture) {
        val picture = data.content
        if (picture == null) {
            pictureData.icon = null
        } else {
            pictureData.icon = ImageIcon(picture)
        }
    }

    override fun getCzWikiUrl(): String {
        error { "Getting URL to czech Wikipedia page is not allowed for pictures." }
    }

    override fun getEnWikiUrl(): String {
        error { "Getting URL to english Wikipedia page is not allowed for pictures." }
    }

    override fun getCsfdUrl(): String {
        error { "Getting URL to ČSFD page is not allowed for pictures." }
    }

    override fun getImdbUrl(): Int {
        error { "Getting URL to IMDB page is not allowed for pictures." }
    }

    override fun getHorizontalLayoutWithComponents(layout: GroupLayout, group: GroupLayout.Group): GroupLayout.Group {
        return group.addComponent(pictureData, HORIZONTAL_PICTURE_SIZE, HORIZONTAL_PICTURE_SIZE, HORIZONTAL_PICTURE_SIZE)
    }

    override fun getVerticalLayoutWithComponents(layout: GroupLayout, group: GroupLayout.Group): GroupLayout.Group {
        return group.addComponent(pictureData, VERTICAL_PICTURE_SIZE, VERTICAL_PICTURE_SIZE, VERTICAL_PICTURE_SIZE)
    }

}
