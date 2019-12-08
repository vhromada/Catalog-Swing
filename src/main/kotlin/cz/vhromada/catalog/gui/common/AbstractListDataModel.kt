package cz.vhromada.catalog.gui.common

import cz.vhromada.validation.result.Result
import cz.vhromada.validation.result.Status
import javax.swing.AbstractListModel

/**
 * An abstract class represents data model for list with data.
 *
 * @param <T> type of data
 * @author Vladimir Hromada
 */
abstract class AbstractListDataModel<T> : AbstractListModel<String>() {

    /**
     * List of data
     */
    private lateinit var data: List<T>

    override fun getSize(): Int {
        return data.size
    }

    override fun getElementAt(index: Int): String {
        return getDisplayValue(getObjectAt(index))
    }

    /**
     * Returns data object at the specified index.
     *
     * @param index the requested index
     * @return data object at index
     */
    fun getObjectAt(index: Int): T {
        return data[index]
    }

    /**
     * Updates model.
     */
    fun update() {
        val result = getData()

        if (Status.OK == result.status) {
            data = result.data!!
        } else {
            throw IllegalArgumentException("Can't get data. $result")
        }
    }

    /**
     * Returns result with data.
     *
     * @return result with data.
     */
    protected abstract fun getData(): Result<List<T>>

    /**
     * Returns display value for data.
     *
     * @param dataObject data
     * @return display value for data
     */
    protected abstract fun getDisplayValue(dataObject: T): String

}
