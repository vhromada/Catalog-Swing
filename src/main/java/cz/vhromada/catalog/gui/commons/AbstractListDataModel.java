package cz.vhromada.catalog.gui.commons;

import java.util.List;

import javax.swing.AbstractListModel;

import cz.vhromada.result.Result;
import cz.vhromada.result.Status;

/**
 * An abstract class represents data model for list with data.
 *
 * @param <T> type of data
 * @author Vladimir Hromada
 */
public abstract class AbstractListDataModel<T> extends AbstractListModel<String> {

    /**
     * SerialVersionUID
     */
    private static final long serialVersionUID = 1L;

    /**
     * List of data
     */
    private List<T> data;

    @Override
    public int getSize() {
        return data.size();
    }

    @Override
    public String getElementAt(final int index) {
        return getDisplayValue(getObjectAt(index));
    }

    /**
     * Returns data object at the specified index.
     *
     * @param index the requested index
     * @return data object at index
     */
    public T getObjectAt(final int index) {
        return data.get(index);
    }

    /**
     * Updates model.
     */
    public final void update() {
        final Result<List<T>> result = getData();

        if (Status.OK == result.getStatus()) {
            data = result.getData();
        } else {
            throw new IllegalArgumentException("Can't get data. " + result);
        }
    }

    /**
     * Returns result with data.
     *
     * @return result with data.
     */
    protected abstract Result<List<T>> getData();

    /**
     * Returns display value for data.
     *
     * @param dataObject data
     * @return display value for data
     */
    protected abstract String getDisplayValue(T dataObject);

}
