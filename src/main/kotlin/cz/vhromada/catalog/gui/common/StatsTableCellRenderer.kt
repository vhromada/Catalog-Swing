package cz.vhromada.catalog.gui.common

import javax.swing.SwingConstants
import javax.swing.table.DefaultTableCellRenderer

/**
 * A class represents table cell renderer for changing alignment of the cell.
 *
 * @author Vladimir Hromada
 */
class StatsTableCellRenderer : DefaultTableCellRenderer() {

    init {
        verticalAlignment = SwingConstants.CENTER
        horizontalAlignment = SwingConstants.CENTER
    }

}
