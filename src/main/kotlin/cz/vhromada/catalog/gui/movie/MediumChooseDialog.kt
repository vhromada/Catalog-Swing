package cz.vhromada.catalog.gui.movie

import cz.vhromada.catalog.entity.Medium
import cz.vhromada.catalog.gui.common.CatalogSwingConstants
import cz.vhromada.catalog.gui.common.DialogResult
import cz.vhromada.catalog.gui.common.Picture
import cz.vhromada.catalog.gui.common.TimeDataPanel
import cz.vhromada.common.Time
import java.util.ArrayList
import javax.swing.GroupLayout
import javax.swing.JButton
import javax.swing.JComponent
import javax.swing.JDialog
import javax.swing.JFrame
import javax.swing.JLabel
import javax.swing.JSpinner
import javax.swing.SpinnerNumberModel
import javax.swing.WindowConstants

/**
 * A class represents dialog for choosing medium.
 *
 * @author Vladimir Hromada
 */
class MediumChooseDialog(val media: MutableList<Medium>) : JDialog(JFrame(), "Choose", true) {

    /**
     * Return status
     */
    var returnStatus = DialogResult.CANCEL
        private set

    /**
     * Label for media count
     */
    private val mediaCountLabel = JLabel("Media count")

    /**
     * Spinner for media count
     */
    private val mediaCountData = JSpinner(SpinnerNumberModel(1, 1, MAX_MEDIA_COUNT, 1))

    /**
     * Media panels
     */
    private val mediaPanels = ArrayList<TimeDataPanel>()

    /**
     * Button OK
     */
    private val okButton = JButton("OK", Picture.OK.icon)

    /**
     * Button Cancel
     */
    private val cancelButton = JButton("Cancel", Picture.CANCEL.icon)

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

        for (i in 1..MAX_MEDIA_COUNT) {
            val mediumPanel = TimeDataPanel("Medium $i")
            mediumPanel.isVisible = i == 1
            mediaPanels.add(mediumPanel)
        }

        mediaCountData.addChangeListener {
            val mediaCount = mediaCountData.value as Int
            for (i in mediaPanels.indices) {
                mediaPanels[i].isVisible = i < mediaCount
                pack()
            }
        }

        if (media.isEmpty()) {
            mediaCountData.value = 1
        } else {
            mediaCountData.value = media.size
            for (i in media.indices) {
                mediaPanels[i].length = Time(media[i].length!!)
            }
        }

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
        media.clear()
        val mediaCount = mediaCountData.value as Int
        for (i in 0 until mediaCount) {
            val medium = Medium(id = null,
                    number = i + 1,
                    length = mediaPanels[i].length.length)
            media.add(medium)
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
                .addGroup(createHorizontalComponents(layout, mediaCountLabel, mediaCountData))
        for (mediumPanel in mediaPanels) {
            components.addComponent(mediumPanel, HORIZONTAL_MEDIUM_PANEL_SIZE, HORIZONTAL_MEDIUM_PANEL_SIZE, HORIZONTAL_MEDIUM_PANEL_SIZE)
        }
        components.addGroup(buttons)

        return layout.createSequentialGroup()
                .addGap(HORIZONTAL_GAP_SIZE)
                .addGroup(components)
                .addGap(HORIZONTAL_GAP_SIZE)
    }

    /**
     * Returns horizontal layout for label component with data component.
     *
     * @param layout         layout
     * @param labelComponent label component
     * @param dataComponent  data component
     * @return horizontal layout for label component with data component
     */
    private fun createHorizontalComponents(layout: GroupLayout, labelComponent: JComponent, dataComponent: JComponent): GroupLayout.Group {
        return layout.createSequentialGroup()
                .addComponent(labelComponent, HORIZONTAL_LABEL_DIALOG_SIZE, HORIZONTAL_LABEL_DIALOG_SIZE, HORIZONTAL_LABEL_DIALOG_SIZE)
                .addGap(HORIZONTAL_DATA_GAP_SIZE)
                .addComponent(dataComponent, HORIZONTAL_DATA_DIALOG_SIZE, HORIZONTAL_DATA_DIALOG_SIZE, HORIZONTAL_DATA_DIALOG_SIZE)
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

        val components = layout.createSequentialGroup()
                .addGap(VERTICAL_GAP_SIZE)
                .addGroup(createVerticalComponents(layout, mediaCountLabel, mediaCountData))
                .addGap(VERTICAL_GAP_SIZE)
        for (mediumPanel in mediaPanels) {
            components.addComponent(mediumPanel, CatalogSwingConstants.VERTICAL_BUTTON_SIZE, CatalogSwingConstants.VERTICAL_BUTTON_SIZE, CatalogSwingConstants.VERTICAL_BUTTON_SIZE)
        }
        components.addGap(VERTICAL_GAP_SIZE)
                .addGroup(buttons)
                .addGap(VERTICAL_GAP_SIZE)

        return components
    }

    /**
     * Returns vertical layout for label component with data component.
     *
     * @param layout         layout
     * @param labelComponent label component
     * @param dataComponent  data component
     * @return vertical layout for label component with data component
     */
    private fun createVerticalComponents(layout: GroupLayout, labelComponent: JComponent, dataComponent: JComponent): GroupLayout.Group {
        return layout.createParallelGroup()
                .addComponent(labelComponent, CatalogSwingConstants.VERTICAL_COMPONENT_SIZE, CatalogSwingConstants.VERTICAL_COMPONENT_SIZE, CatalogSwingConstants.VERTICAL_COMPONENT_SIZE)
                .addComponent(dataComponent, CatalogSwingConstants.VERTICAL_COMPONENT_SIZE, CatalogSwingConstants.VERTICAL_COMPONENT_SIZE, CatalogSwingConstants.VERTICAL_COMPONENT_SIZE)
    }

    companion object {

        /**
         * Maximum media count
         */
        private const val MAX_MEDIA_COUNT = 10

        /**
         * Horizontal label size
         */
        private const val HORIZONTAL_LABEL_DIALOG_SIZE = 100

        /**
         * Horizontal data size
         */
        private const val HORIZONTAL_DATA_DIALOG_SIZE = 200

        /**
         * Horizontal button size
         */
        private const val HORIZONTAL_BUTTON_SIZE = 96

        /**
         * Horizontal medium panel size
         */
        private const val HORIZONTAL_MEDIUM_PANEL_SIZE = 310

        /**
         * Horizontal size of gap between label and data
         */
        private const val HORIZONTAL_DATA_GAP_SIZE = 10

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
         * Vertical gap size
         */
        private const val VERTICAL_GAP_SIZE = 20

    }

}
