package cz.vhromada.catalog.gui.picture

import cz.vhromada.catalog.entity.Picture
import cz.vhromada.catalog.gui.common.AbstractInfoDialog
import cz.vhromada.catalog.gui.common.CatalogSwingConstants
import java.io.File
import java.io.IOException
import java.nio.file.Files
import java.nio.file.Paths
import javax.swing.GroupLayout
import javax.swing.JButton
import javax.swing.JFileChooser

/**
 * A class represents dialog for picture.
 *
 * @author Vladimir Hromada
 */
class PictureInfoDialog : AbstractInfoDialog<Picture>() {

    /**
     * Button for changing media
     */
    private val contentButton = JButton("Content", cz.vhromada.catalog.gui.common.Picture.ADD.icon)

    /**
     * File chooser for content
     */
    private val contentChooser = JFileChooser()

    /**
     * File
     */
    private var file: File? = null

    init {
        init()
    }

    override fun initComponents() {
        contentButton.addActionListener { contentAction() }
    }

    override fun processData(objectData: Picture?): Picture? {
        if (file == null) {
            return null
        }

        try {
            return Picture(id = null,
                    content = Files.readAllBytes(Paths.get(file!!.absolutePath)),
                    position = null)
        } catch (ex: IOException) {
            throw RuntimeException("Cannot get file content.", ex)
        }
    }

    override fun getHorizontalLayoutWithComponents(layout: GroupLayout, group: GroupLayout.Group): GroupLayout.Group {
        return group.addComponent(contentButton, HORIZONTAL_LONG_COMPONENT_SIZE, HORIZONTAL_LONG_COMPONENT_SIZE, HORIZONTAL_LONG_COMPONENT_SIZE)
    }

    override fun getVerticalLayoutWithComponents(layout: GroupLayout, group: GroupLayout.Group): GroupLayout.Group {
        return group.addComponent(contentButton, CatalogSwingConstants.VERTICAL_BUTTON_SIZE, CatalogSwingConstants.VERTICAL_BUTTON_SIZE, CatalogSwingConstants.VERTICAL_BUTTON_SIZE)
    }

    /**
     * Performs action for button Upload picture.
     */
    private fun contentAction() {
        val returnValue = contentChooser.showOpenDialog(null)
        if (JFileChooser.APPROVE_OPTION == returnValue) {
            file = contentChooser.selectedFile
            setOkButtonEnabled(true)
        } else {
            file = null
            setOkButtonEnabled(false)
        }
    }

}
