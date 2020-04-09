package cz.vhromada.catalog.gui.common

import cz.vhromada.common.entity.Time
import javax.swing.GroupLayout
import javax.swing.JLabel
import javax.swing.JPanel
import javax.swing.JSpinner
import javax.swing.SpinnerNumberModel

/**
 * A class represents panel for data about time.
 *
 * @author Vladimir Hromada
 */
class TimeDataPanel(labelText: String) : JPanel() {

    /**
     * Label for length
     */
    private val lengthLabel = JLabel()

    /**
     * Spinner for length - hours
     */
    private val lengthHoursData = JSpinner(SpinnerNumberModel(0, 0, MAX_HOURS, 1))

    /**
     * Spinner for length - minutes
     */
    private val lengthMinutesData = JSpinner(SpinnerNumberModel(0, 0, MAX_MINUTES, 1))

    /**
     * Spinner for length - seconds
     */
    private val lengthSecondsData = JSpinner(SpinnerNumberModel(0, 0, MAX_SECONDS, 1))

    /**
     * Length
     */
    var length: Time
        get() {
            val hours = lengthHoursData.value as Int
            val minutes = lengthMinutesData.value as Int
            val seconds = lengthSecondsData.value as Int

            return Time(hours, minutes, seconds)
        }
        set(length) {
            lengthHoursData.value = length.getData(Time.TimeData.HOUR)
            lengthMinutesData.value = length.getData(Time.TimeData.MINUTE)
            lengthSecondsData.value = length.getData(Time.TimeData.SECOND)
        }

    init {
        require(labelText.isNotBlank()) { "Label text mustn't be empty string." }

        this.lengthLabel.isFocusable = false
        this.lengthLabel.text = labelText

        val layout = GroupLayout(this)
        setLayout(layout)
        layout.setHorizontalGroup(createHorizontalLayout(layout))
        layout.setVerticalGroup(createVerticalLayout(layout))
    }

    /**
     * Returns horizontal layout of components.
     *
     * @param layout layout
     * @return horizontal layout of components
     */
    private fun createHorizontalLayout(layout: GroupLayout): GroupLayout.Group {
        return layout.createSequentialGroup()
                .addComponent(lengthLabel, HORIZONTAL_LABEL_SIZE, HORIZONTAL_LABEL_SIZE, HORIZONTAL_LABEL_SIZE)
                .addGap(HORIZONTAL_GAP_SIZE)
                .addComponent(lengthHoursData, HORIZONTAL_TIME_SIZE, HORIZONTAL_TIME_SIZE, HORIZONTAL_TIME_SIZE)
                .addGap(HORIZONTAL_GAP_SIZE)
                .addComponent(lengthMinutesData, HORIZONTAL_TIME_SIZE, HORIZONTAL_TIME_SIZE, HORIZONTAL_TIME_SIZE)
                .addGap(HORIZONTAL_GAP_SIZE)
                .addComponent(lengthSecondsData, HORIZONTAL_TIME_SIZE, HORIZONTAL_TIME_SIZE, HORIZONTAL_TIME_SIZE)
    }

    /**
     * Returns vertical layout of components.
     *
     * @param layout layout
     * @return vertical layout of components
     */
    private fun createVerticalLayout(layout: GroupLayout): GroupLayout.Group {
        return layout.createParallelGroup()
                .addComponent(lengthLabel, CatalogSwingConstants.VERTICAL_COMPONENT_SIZE, CatalogSwingConstants.VERTICAL_COMPONENT_SIZE, CatalogSwingConstants.VERTICAL_COMPONENT_SIZE)
                .addComponent(lengthHoursData, CatalogSwingConstants.VERTICAL_COMPONENT_SIZE, CatalogSwingConstants.VERTICAL_COMPONENT_SIZE, CatalogSwingConstants.VERTICAL_COMPONENT_SIZE)
                .addComponent(lengthMinutesData, CatalogSwingConstants.VERTICAL_COMPONENT_SIZE, CatalogSwingConstants.VERTICAL_COMPONENT_SIZE, CatalogSwingConstants.VERTICAL_COMPONENT_SIZE)
                .addComponent(lengthSecondsData, CatalogSwingConstants.VERTICAL_COMPONENT_SIZE, CatalogSwingConstants.VERTICAL_COMPONENT_SIZE, CatalogSwingConstants.VERTICAL_COMPONENT_SIZE)
    }

    companion object {

        /**
         * Horizontal label size
         */
        private const val HORIZONTAL_LABEL_SIZE = 100

        /**
         * Horizontal time size
         */
        private const val HORIZONTAL_TIME_SIZE = 60

        /**
         * Horizontal gap size
         */
        private const val HORIZONTAL_GAP_SIZE = 10

        /**
         * Maximum hours
         */
        private const val MAX_HOURS = 23

        /**
         * Maximum minutes
         */
        private const val MAX_MINUTES = 59

        /**
         * Maximum seconds
         */
        private const val MAX_SECONDS = 59

    }

}
