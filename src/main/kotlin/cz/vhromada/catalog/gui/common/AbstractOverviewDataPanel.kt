package cz.vhromada.catalog.gui.common

import java.awt.event.InputEvent
import java.awt.event.KeyEvent
import javax.swing.GroupLayout
import javax.swing.JList
import javax.swing.JMenuItem
import javax.swing.JPanel
import javax.swing.JPopupMenu
import javax.swing.JScrollPane
import javax.swing.JTabbedPane
import javax.swing.JTable
import javax.swing.KeyStroke
import javax.swing.ListSelectionModel
import javax.swing.SwingUtilities

/**
 * An abstract class represents overview panel with data.
 *
 * @param <T> type of data
 * @author Vladimir Hromada
 */
abstract class AbstractOverviewDataPanel<T> : JPanel {
    /**
     * Popup menu
     */
    private val popupMenu = JPopupMenu()

    /**
     * Menu item for adding data
     */
    private val addPopupMenuItem = JMenuItem("Add", Picture.ADD.icon)

    /**
     * Menu item for updating data
     */
    private val updatePopupMenuItem = JMenuItem("Update", Picture.UPDATE.icon)

    /**
     * Menu item for removing data
     */
    private val removePopupMenuItem = JMenuItem("Remove", Picture.REMOVE.icon)

    /**
     * Menu item for duplicating data
     */
    private val duplicatePopupMenuItem = JMenuItem("Duplicate", Picture.DUPLICATE.icon)

    /**
     * Menu item for moving up data
     */
    private val moveUpPopupMenuItem = JMenuItem("Move up", Picture.UP.icon)

    /**
     * Menu item for moving down data
     */
    private val moveDownPopupMenuItem = JMenuItem("Move down", Picture.DOWN.icon)

    /**
     * List with data
     */
    private val list = JList<String>()

    /**
     * ScrollPane for list with data
     */
    private val listScrollPane = JScrollPane(list)

    /**
     * Tabbed pane with data
     */
    private val tabbedPane = JTabbedPane()

    /**
     * Table with with stats
     */
    private val statsTable = JTable()

    /**
     * ScrollPane for table with stats
     */
    private val statsTableScrollPane = JScrollPane(statsTable)

    /**
     * Data model for list
     */
    private val listDataModel: AbstractListDataModel<T>

    /**
     * Data model for table with stats
     */
    private val statsTableDataModel: AbstractStatsTableDataModel?

    /**
     * True if data is saved
     */
    var saved: Boolean = true
        private set

    /**
     * Creates a new instance of AbstractDataPanel.
     *
     * @param listDataModel data model for list
     */
    constructor(listDataModel: AbstractListDataModel<T>) {
        this.listDataModel = listDataModel
        this.statsTableDataModel = null
        initComponents()
    }

    /**
     * Creates a new instance of AbstractDataPanel.
     *
     * @param listDataModel       data model for list
     * @param statsTableDataModel data model for table with stats
     */
    constructor(listDataModel: AbstractListDataModel<T>, statsTableDataModel: AbstractStatsTableDataModel) {
        this.listDataModel = listDataModel
        this.statsTableDataModel = statsTableDataModel
        initComponents()
    }

    /**
     * Creates new data.
     */
    open fun newData() {
        deleteData()
        listDataModel.update()
        list.clearSelection()
        list.updateUI()
        tabbedPane.removeAll()
        statsTableDataModel?.update()
        statsTable.updateUI()
        saved = true
    }

    /**
     * Clears selection.
     */
    open fun clearSelection() {
        tabbedPane.removeAll()
        list.clearSelection()
    }

    /**
     * Saves.
     */
    open fun save() {
        saved = true
    }

    /**
     * Returns info dialog.
     *
     * @param add  true if dialog is for adding data
     * @param data data
     * @return info dialog
     */
    protected abstract fun getInfoDialog(add: Boolean, data: T?): AbstractInfoDialog<T>

    /**
     * Deletes data.
     */
    protected abstract fun deleteData()

    /**
     * Adds data.
     *
     * @param data data
     */
    protected abstract fun addData(data: T)

    /**
     * Updates data.
     *
     * @param data data
     */
    protected abstract fun updateData(data: T)

    /**
     * Removes data.
     *
     * @param data data
     */
    protected abstract fun removeData(data: T)

    /**
     * Duplicates data.
     *
     * @param data data
     */
    protected abstract fun duplicatesData(data: T)

    /**
     * Moves up data.
     *
     * @param data data
     */
    protected abstract fun moveUpData(data: T)

    /**
     * Moves down data.
     *
     * @param data data
     */
    protected abstract fun moveDownData(data: T)

    /**
     * Returns data panel.
     *
     * @param data data
     * @return data panel
     */
    protected abstract fun getDataPanel(data: T): JPanel

    /**
     * Updates data on change.
     *
     * @param dataPanel tabbed pane with data
     * @param data      data
     */
    protected abstract fun updateDataOnChange(dataPanel: JTabbedPane, data: T)

    /**
     * Updates model.
     *
     * @param data data
     */
    protected fun updateModel(data: T) {
        listDataModel.update()
        list.updateUI()
        getTabbedPanelDataPanel().updateData(data)
        updateState()
    }

    /**
     * Initializes components.
     */
    private fun initComponents() {
        initPopupMenu(addPopupMenuItem, updatePopupMenuItem, removePopupMenuItem, duplicatePopupMenuItem, moveUpPopupMenuItem, moveDownPopupMenuItem)

        addPopupMenuItem.accelerator = KeyStroke.getKeyStroke(KeyEvent.VK_INSERT, 0)
        addPopupMenuItem.addActionListener { addAction() }

        updatePopupMenuItem.accelerator = KeyStroke.getKeyStroke(KeyEvent.VK_F11, 0)
        updatePopupMenuItem.isEnabled = false
        updatePopupMenuItem.addActionListener { updateAction() }

        removePopupMenuItem.accelerator = KeyStroke.getKeyStroke(KeyEvent.VK_DELETE, 0)
        removePopupMenuItem.isEnabled = false
        removePopupMenuItem.addActionListener { removeAction() }

        duplicatePopupMenuItem.accelerator = KeyStroke.getKeyStroke(KeyEvent.VK_C, InputEvent.CTRL_DOWN_MASK)
        duplicatePopupMenuItem.isEnabled = false
        duplicatePopupMenuItem.addActionListener { duplicateAction() }

        moveUpPopupMenuItem.accelerator = KeyStroke.getKeyStroke(KeyEvent.VK_U, InputEvent.CTRL_DOWN_MASK)
        moveUpPopupMenuItem.isEnabled = false
        moveUpPopupMenuItem.addActionListener { moveUpAction() }

        moveDownPopupMenuItem.accelerator = KeyStroke.getKeyStroke(KeyEvent.VK_D, InputEvent.CTRL_DOWN_MASK)
        moveDownPopupMenuItem.isEnabled = false
        moveDownPopupMenuItem.addActionListener { moveDownAction() }

        list.model = listDataModel
        list.selectionMode = ListSelectionModel.SINGLE_SELECTION
        list.componentPopupMenu = popupMenu
        list.selectionModel.addListSelectionListener { listValueChangedAction() }

        initStats()

        val layout = GroupLayout(this)
        setLayout(layout)
        layout.setHorizontalGroup(createHorizontalLayout(layout))
        layout.setVerticalGroup(createVerticalLayout(layout))
    }

    /**
     * Initializes popup menu.
     *
     * @param menuItems popup menu items
     */
    private fun initPopupMenu(vararg menuItems: JMenuItem) {
        for (menuItem in menuItems) {
            popupMenu.add(menuItem)
        }
    }

    /**
     * Initializes stats.
     */
    private fun initStats() {
        if (statsTableDataModel != null) {
            statsTable.model = statsTableDataModel
            statsTable.autoResizeMode = JTable.AUTO_RESIZE_ALL_COLUMNS
            statsTable.isEnabled = false
            statsTable.rowSelectionAllowed = false
            statsTable.setDefaultRenderer(Int::class.java, StatsTableCellRenderer())
            statsTable.setDefaultRenderer(String::class.java, StatsTableCellRenderer())
        }
    }

    /**
     * Performs action for button Add.
     */
    private fun addAction() {
        SwingUtilities.invokeLater {
            val dialog = getInfoDialog(true, null)
            dialog.isVisible = true
            if (dialog.returnStatus === DialogResult.OK) {
                addData(dialog.data!!)
                listDataModel.update()
                list.updateUI()
                list.selectedIndex = list.model.size - 1
                updateState()
            }
        }
    }

    /**
     * Performs action for button Update.
     */
    private fun updateAction() {
        SwingUtilities.invokeLater {
            val dialog = getInfoDialog(false, listDataModel.getObjectAt(list.selectedIndex))
            dialog.isVisible = true
            if (dialog.returnStatus === DialogResult.OK) {
                val data = dialog.data!!
                updateData(data)
                updateModel(data)
            }
        }
    }

    /**
     * Performs action for button Remove.
     */
    private fun removeAction() {
        removeData(listDataModel.getObjectAt(list.selectedIndex))
        listDataModel.update()
        list.updateUI()
        list.clearSelection()
        updateState()
    }

    /**
     * Performs action for button Duplicate.
     */
    private fun duplicateAction() {
        val index = list.selectedIndex
        duplicatesData(listDataModel.getObjectAt(index))
        listDataModel.update()
        list.updateUI()
        list.selectedIndex = index + 1
        updateState()
    }

    /**
     * Performs action for button MoveUp.
     */
    private fun moveUpAction() {
        val index = list.selectedIndex
        moveUpData(listDataModel.getObjectAt(index))
        listDataModel.update()
        list.updateUI()
        list.selectedIndex = index - 1
        saved = false
        if (statsTableDataModel == null) {
            firePropertyChange(UPDATE_PROPERTY, false, true)
        }
    }

    /**
     * Performs action for button MoveDown.
     */
    private fun moveDownAction() {
        val index = list.selectedIndex
        moveDownData(listDataModel.getObjectAt(index))
        listDataModel.update()
        list.updateUI()
        list.selectedIndex = index + 1
        saved = false
        if (statsTableDataModel == null) {
            firePropertyChange(UPDATE_PROPERTY, false, true)
        }
    }

    /**
     * Performs action for change of list value.
     */
    private fun listValueChangedAction() {
        val isSelectedRow = list.selectedIndices.size == 1
        val selectedRow = list.selectedIndex
        val validRowIndex = selectedRow >= 0
        val validSelection = isSelectedRow && validRowIndex
        removePopupMenuItem.isEnabled = validSelection
        updatePopupMenuItem.isEnabled = validSelection
        duplicatePopupMenuItem.isEnabled = validSelection
        tabbedPane.removeAll()
        if (validSelection) {
            val data = listDataModel.getObjectAt(selectedRow)
            tabbedPane.add("Data", getDataPanel(data))
            updateDataOnChange(tabbedPane, data)
        }
        moveUpPopupMenuItem.isEnabled = isSelectedRow && selectedRow > 0
        moveDownPopupMenuItem.isEnabled = validSelection && selectedRow < list.model.size - 1
    }

    /**
     * Updates state.
     */
    private fun updateState() {
        if (statsTableDataModel == null) {
            firePropertyChange(UPDATE_PROPERTY, false, true)
        } else {
            statsTableDataModel.update()
            statsTable.updateUI()
            saved = false
        }
    }

    /**
     * Returns data panel in tabbed pane.
     *
     * @return data panel in tabbed pane
     */
    @Suppress("UNCHECKED_CAST")
    private fun getTabbedPanelDataPanel(): AbstractDataPanel<T> {
        return tabbedPane.getComponentAt(0) as AbstractDataPanel<T>
    }

    /**
     * Returns horizontal layout of components.
     *
     * @param layout layout
     * @return horizontal layout of components
     */
    private fun createHorizontalLayout(layout: GroupLayout): GroupLayout.Group {
        val data = layout.createSequentialGroup()
                .addComponent(listScrollPane, HORIZONTAL_SCROLL_PANE_SIZE, HORIZONTAL_SCROLL_PANE_SIZE, HORIZONTAL_SCROLL_PANE_SIZE)
                .addGap(HORIZONTAL_GAP_SIZE)
                .addComponent(tabbedPane, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE.toInt())

        return if (statsTableDataModel == null) {
            data
        } else {
            layout.createParallelGroup()
                    .addGroup(data)
                    .addComponent(statsTableScrollPane, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE.toInt())
        }
    }

    /**
     * Returns vertical layout of components.
     *
     * @param layout layout
     * @return vertical layout of components
     */
    private fun createVerticalLayout(layout: GroupLayout): GroupLayout.Group {
        val data = layout.createParallelGroup()
                .addComponent(listScrollPane, VERTICAL_DATA_COMPONENT_SIZE, VERTICAL_DATA_COMPONENT_SIZE, Short.MAX_VALUE.toInt())
                .addComponent(tabbedPane, VERTICAL_DATA_COMPONENT_SIZE, VERTICAL_DATA_COMPONENT_SIZE, Short.MAX_VALUE.toInt())

        return if (statsTableDataModel == null) {
            data
        } else {
            layout.createSequentialGroup()
                    .addGroup(data)
                    .addGap(VERTICAL_GAP_SIZE)
                    .addComponent(statsTableScrollPane, VERTICAL_STATS_SCROLL_PANE_SIZE, VERTICAL_STATS_SCROLL_PANE_SIZE, VERTICAL_STATS_SCROLL_PANE_SIZE)
        }
    }

    companion object {

        /**
         * Horizontal scroll pane size
         */
        private const val HORIZONTAL_SCROLL_PANE_SIZE = 200

        /**
         * Horizontal gap size
         */
        private const val HORIZONTAL_GAP_SIZE = 5

        /**
         * Vertical data component size
         */
        private const val VERTICAL_DATA_COMPONENT_SIZE = 200

        /**
         * Vertical size for scroll pane for table with stats
         */
        private const val VERTICAL_STATS_SCROLL_PANE_SIZE = 45

        /**
         * Vertical gap size
         */
        private const val VERTICAL_GAP_SIZE = 2

        /**
         * Update property
         */
        private const val UPDATE_PROPERTY = "update"
    }

}
