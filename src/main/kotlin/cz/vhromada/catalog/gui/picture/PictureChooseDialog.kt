package cz.vhromada.catalog.gui.picture

import cz.vhromada.catalog.entity.Picture
import cz.vhromada.catalog.facade.PictureFacade
import cz.vhromada.catalog.gui.common.CatalogSwingConstants
import cz.vhromada.catalog.gui.common.DialogResult
import cz.vhromada.validation.result.Status
import javax.swing.GroupLayout
import javax.swing.ImageIcon
import javax.swing.JButton
import javax.swing.JDialog
import javax.swing.JFrame
import javax.swing.JLabel
import javax.swing.JList
import javax.swing.JScrollPane
import javax.swing.ListSelectionModel
import javax.swing.WindowConstants

/**
 * A class represents dialog for choosing picture.
 *
 * @author Vladimir Hromada
 */
class PictureChooseDialog(
        private val pictureFacade: PictureFacade,
        var picture: Picture) : JDialog(JFrame(), "Choose", true) {

    /**
     * Return status
     */
    var returnStatus = DialogResult.CANCEL
        private set

    /**
     * List with pictures
     */
    private val list = JList<String>()

    /**
     * ScrollPane for list with pictures
     */
    private val listScrollPane = JScrollPane(list)

    /**
     * Label for picture
     */
    private val pictureData = JLabel()

    /**
     * Button OK
     */
    private val okButton = JButton("OK", cz.vhromada.catalog.gui.common.Picture.OK.icon)

    /**
     * Button Cancel
     */
    private val cancelButton = JButton("Cancel", cz.vhromada.catalog.gui.common.Picture.CANCEL.icon)

    /**
     * Data model for list for pictures
     */
    private lateinit var pictureListDataModel: PicturesListDataModel

    init {
        initComponents()
        setIconImage(cz.vhromada.catalog.gui.common.Picture.CHOOSE.icon.image)
        updatePicture(picture.id)
    }

    /**
     * Initializes components.
     */
    @Suppress("DuplicatedCode")
    private fun initComponents() {
        defaultCloseOperation = WindowConstants.DISPOSE_ON_CLOSE
        isResizable = false

        pictureListDataModel = PicturesListDataModel(pictureFacade)
        list.model = pictureListDataModel
        list.selectionMode = ListSelectionModel.SINGLE_SELECTION
        list.selectedIndices = getSelectedIndexes()
        list.addListSelectionListener { selectionChangeAction() }

        okButton.isEnabled = list.selectedIndices.isNotEmpty()
        okButton.addActionListener { okAction() }

        cancelButton.addActionListener { cancelAction() }

        val layout = GroupLayout(getRootPane())
        getRootPane().layout = layout
        layout.setHorizontalGroup(createHorizontalLayout(layout))
        layout.setVerticalGroup(createVerticalLayout(layout))

        pack()
        setLocationRelativeTo(getRootPane())
    }

    /**
     * Performs action for button OK.
     */
    private fun okAction() {
        returnStatus = DialogResult.OK
        val indexes = list.selectedIndices
        for (index in indexes) {
            picture = pictureListDataModel.getObjectAt(index)
        }
        close()
    }

    /**
     * Performs action for button Cancel.
     */
    private fun cancelAction() {
        returnStatus = DialogResult.CANCEL
        close()
    }

    /**
     * Performs action for selection change.
     */
    private fun selectionChangeAction() {
        val indexes = list.selectedIndices
        if (indexes.isNotEmpty()) {
            updatePicture(Integer.valueOf(list.selectedValue))
            okButton.isEnabled = true
        } else {
            updatePicture(null)
            okButton.isEnabled = false
        }
    }

    /**
     * Closes dialog.
     */
    private fun close() {
        isVisible = false
        dispose()
    }

    /**
     * Returns horizontal layout of components.
     *
     * @param layout layout
     * @return horizontal layout of components
     */
    private fun createHorizontalLayout(layout: GroupLayout): GroupLayout.Group {
        val buttons = layout.createSequentialGroup()
                .addGap(HORIZONTAL_BUTTON_GAP_SIZE)
                .addComponent(okButton, HORIZONTAL_BUTTON_SIZE, HORIZONTAL_BUTTON_SIZE, HORIZONTAL_BUTTON_SIZE)
                .addGap(HORIZONTAL_BUTTONS_GAP_SIZE)
                .addComponent(cancelButton, HORIZONTAL_BUTTON_SIZE, HORIZONTAL_BUTTON_SIZE, HORIZONTAL_BUTTON_SIZE)
                .addGap(HORIZONTAL_BUTTON_GAP_SIZE)

        val data = layout.createSequentialGroup()
                .addComponent(listScrollPane, HORIZONTAL_SCROLL_PANE_SIZE, HORIZONTAL_SCROLL_PANE_SIZE, HORIZONTAL_SCROLL_PANE_SIZE)
                .addGap(HORIZONTAL_GAP_SIZE)
                .addComponent(pictureData, HORIZONTAL_PICTURE_SIZE, HORIZONTAL_PICTURE_SIZE, HORIZONTAL_PICTURE_SIZE)

        val components = layout.createParallelGroup()
                .addGroup(data)
                .addGroup(buttons)

        return layout.createSequentialGroup()
                .addGap(HORIZONTAL_GAP_SIZE)
                .addGroup(components)
                .addGap(HORIZONTAL_GAP_SIZE)
    }

    /**
     * Returns vertical layout of components.
     *
     * @param layout layout
     * @return vertical layout of components
     */
    private fun createVerticalLayout(layout: GroupLayout): GroupLayout.Group {
        val buttons = layout.createParallelGroup()
                .addComponent(okButton, CatalogSwingConstants.VERTICAL_BUTTON_SIZE, CatalogSwingConstants.VERTICAL_BUTTON_SIZE, CatalogSwingConstants.VERTICAL_BUTTON_SIZE)
                .addComponent(cancelButton, CatalogSwingConstants.VERTICAL_BUTTON_SIZE, CatalogSwingConstants.VERTICAL_BUTTON_SIZE, CatalogSwingConstants.VERTICAL_BUTTON_SIZE)

        val data = layout.createParallelGroup()
                .addComponent(listScrollPane, VERTICAL_SCROLL_PANE_SIZE, VERTICAL_SCROLL_PANE_SIZE, VERTICAL_SCROLL_PANE_SIZE)
                .addComponent(pictureData, VERTICAL_PICTURE_SIZE, VERTICAL_PICTURE_SIZE, VERTICAL_PICTURE_SIZE)

        return layout.createSequentialGroup()
                .addGap(VERTICAL_GAP_SIZE)
                .addGroup(data)
                .addGap(VERTICAL_GAP_SIZE)
                .addGroup(buttons)
                .addGap(VERTICAL_GAP_SIZE)
    }

    /**
     * Updates picture.
     *
     * @param id picture's ID
     */
    private fun updatePicture(id: Int?) {
        if (id != null) {
            val pictureResult = pictureFacade.get(id)
            if (Status.OK == pictureResult.status) {
                pictureData.icon = ImageIcon(pictureResult.data!!.content)
            } else {
                throw IllegalArgumentException("Can't get data. $pictureResult")
            }
        } else {
            pictureData.icon = null
        }
    }


    /**
     * Returns selected indexes.
     *
     * @return selected indexes
     */
    private fun getSelectedIndexes(): IntArray {
        val indexes = mutableListOf<Int>()
        for (i in 0 until pictureListDataModel.size) {
            if (pictureListDataModel.getObjectAt(i) == picture) {
                indexes.add(i)
            }
        }
        return indexes.toIntArray()
    }

    companion object {

        /**
         * Horizontal picture size
         */
        private const val HORIZONTAL_PICTURE_SIZE = 200

        /**
         * Horizontal scroll pane size
         */
        private const val HORIZONTAL_SCROLL_PANE_SIZE = 310

        /**
         * Horizontal button size
         */
        private const val HORIZONTAL_BUTTON_SIZE = 96

        /**
         * Horizontal button gap size
         */
        private const val HORIZONTAL_BUTTON_GAP_SIZE = 82

        /**
         * Horizontal size of gap between button
         */
        private const val HORIZONTAL_BUTTONS_GAP_SIZE = 154

        /**
         * Horizontal gap size
         */
        private const val HORIZONTAL_GAP_SIZE = 20

        /**
         * Vertical picture size
         */
        private const val VERTICAL_PICTURE_SIZE = 180

        /**
         * Vertical scroll pane size
         */
        private const val VERTICAL_SCROLL_PANE_SIZE = 300

        /**
         * Vertical gap size
         */
        private const val VERTICAL_GAP_SIZE = 20

    }

}
