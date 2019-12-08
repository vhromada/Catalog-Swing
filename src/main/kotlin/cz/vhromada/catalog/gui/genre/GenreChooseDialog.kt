package cz.vhromada.catalog.gui.genre

import cz.vhromada.catalog.entity.Genre
import cz.vhromada.catalog.facade.GenreFacade
import cz.vhromada.catalog.gui.common.CatalogSwingConstants
import cz.vhromada.catalog.gui.common.DialogResult
import cz.vhromada.catalog.gui.common.Picture
import javax.swing.GroupLayout
import javax.swing.JButton
import javax.swing.JDialog
import javax.swing.JFrame
import javax.swing.JList
import javax.swing.JScrollPane
import javax.swing.ListSelectionModel
import javax.swing.WindowConstants

/**
 * A class represents dialog for choosing genre.
 *
 * @author Vladimir Hromada
 */
class GenreChooseDialog(private val genreFacade: GenreFacade, val genres: MutableList<Genre>) : JDialog(JFrame(), "Choose", true) {

    /**
     * Return status
     */
    var returnStatus = DialogResult.CANCEL
        private set

    /**
     * List with genres
     */
    private val list = JList<String>()

    /**
     * ScrollPane for list with genres
     */
    private val listScrollPane = JScrollPane(list)

    /**
     * Button OK
     */
    private val okButton = JButton("OK", Picture.OK.icon)

    /**
     * Button Cancel
     */
    private val cancelButton = JButton("Cancel", Picture.CANCEL.icon)

    /**
     * Data model for list for genres
     */
    private lateinit var genreListDataModel: GenresListDataModel

    init {
        initComponents()
        setIconImage(Picture.CHOOSE.icon.image)
    }

    /**
     * Initializes components.
     */
    @Suppress("DuplicatedCode")
    private fun initComponents() {
        defaultCloseOperation = WindowConstants.DISPOSE_ON_CLOSE
        isResizable = false

        genreListDataModel = GenresListDataModel(genreFacade)
        list.model = genreListDataModel
        list.selectionMode = ListSelectionModel.MULTIPLE_INTERVAL_SELECTION
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
        genres.clear()
        val indexes = list.selectedIndices
        for (index in indexes) {
            genres.add(genreListDataModel.getObjectAt(index))
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
        okButton.isEnabled = indexes.isNotEmpty()
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


        val components = layout.createParallelGroup()
                .addComponent(listScrollPane, HORIZONTAL_SCROLL_PANE_SIZE, HORIZONTAL_SCROLL_PANE_SIZE, HORIZONTAL_SCROLL_PANE_SIZE)
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

        return layout.createSequentialGroup()
                .addGap(VERTICAL_GAP_SIZE)
                .addComponent(listScrollPane, VERTICAL_SCROLL_PANE_SIZE, VERTICAL_SCROLL_PANE_SIZE, VERTICAL_SCROLL_PANE_SIZE)
                .addGap(VERTICAL_GAP_SIZE)
                .addGroup(buttons)
                .addGap(VERTICAL_GAP_SIZE)
    }


    /**
     * Returns selected indexes.
     *
     * @return selected indexes
     */
    private fun getSelectedIndexes(): IntArray {
        val indexes = mutableListOf<Int>()
        for (i in 0 until genreListDataModel.size) {
            if (genres.contains(genreListDataModel.getObjectAt(i))) {
                indexes.add(i)
            }
        }
        return indexes.toIntArray()
    }


    companion object {

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
        private const val HORIZONTAL_BUTTON_GAP_SIZE = 32

        /**
         * Horizontal size of gap between button
         */
        private const val HORIZONTAL_BUTTONS_GAP_SIZE = 54

        /**
         * Horizontal gap size
         */
        private const val HORIZONTAL_GAP_SIZE = 20

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
