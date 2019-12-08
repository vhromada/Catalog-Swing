package cz.vhromada.catalog.gui.common

import javax.swing.table.AbstractTableModel

/**
 * An abstract class represents data model for table with stats.
 *
 * @author Vladimir Hromada
 */
abstract class AbstractStatsTableDataModel : AbstractTableModel() {

    /**
     * Updates model.
     */
    abstract fun update()

}
