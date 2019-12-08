package cz.vhromada.catalog.gui

import cz.vhromada.catalog.gui.common.CatalogSwingConstants
import cz.vhromada.catalog.gui.common.Picture
import java.awt.Font
import javax.swing.GroupLayout
import javax.swing.JButton
import javax.swing.JDialog
import javax.swing.JFrame
import javax.swing.JLabel
import javax.swing.SwingConstants
import javax.swing.WindowConstants

/**
 * A class represents dialog about.
 *
 * @author Vladimir Hromada
 */
class AboutDialog : JDialog(JFrame(), "About", true) {

    /**
     * Label for name
     */
    private val nameLabel = JLabel("Catalog", Picture.CATALOG.icon, SwingConstants.LEADING)

    /**
     * Label for version
     */
    private val versionLabel = JLabel("Version: 5.0.0")

    /**
     * Label for author
     */
    private val authorLabel = JLabel("Author: Vladimír Hromada")

    /**
     * Label for copyrights
     */
    private val rightsLabel = JLabel("All rights reserved.")

    /**
     * Button OK
     */
    private val okButton = JButton("OK", Picture.OK.icon)

    init {
        setIconImage(Picture.ABOUT.icon.image)
        defaultCloseOperation = WindowConstants.DISPOSE_ON_CLOSE
        isResizable = false

        val font = Font("Tahoma", Font.BOLD, FONT_SIZE)

        initLabels(font, nameLabel, versionLabel, authorLabel, rightsLabel)

        okButton.font = font
        okButton.addActionListener {
            isVisible = false
            dispose()
        }

        val layout = GroupLayout(contentPane)
        contentPane.layout = layout
        layout.setHorizontalGroup(createHorizontalLayout(layout))
        layout.setVerticalGroup(createVerticalLayout(layout))

        okButton.requestFocusInWindow()
        pack()
        setLocationRelativeTo(getRootPane())
    }

    /**
     * Returns horizontal layout of components.
     *
     * @param layout layout
     * @return horizontal layout of components
     */
    private fun createHorizontalLayout(layout: GroupLayout): GroupLayout.SequentialGroup {
        val labels = layout.createParallelGroup()
                .addComponent(nameLabel, HORIZONTAL_LABEL_SIZE, HORIZONTAL_LABEL_SIZE, HORIZONTAL_LABEL_SIZE)
                .addComponent(versionLabel, HORIZONTAL_LABEL_SIZE, HORIZONTAL_LABEL_SIZE, HORIZONTAL_LABEL_SIZE)
                .addComponent(authorLabel, HORIZONTAL_LABEL_SIZE, HORIZONTAL_LABEL_SIZE, HORIZONTAL_LABEL_SIZE)
                .addComponent(rightsLabel, HORIZONTAL_LABEL_SIZE, HORIZONTAL_LABEL_SIZE, HORIZONTAL_LABEL_SIZE)

        val button = layout.createSequentialGroup()
                .addGap(HORIZONTAL_BUTTON_GAP_SIZE)
                .addComponent(okButton, HORIZONTAL_BUTTON_SIZE, HORIZONTAL_BUTTON_SIZE, HORIZONTAL_BUTTON_SIZE)

        val components = layout.createParallelGroup()
                .addGroup(labels)
                .addGroup(button)

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
    private fun createVerticalLayout(layout: GroupLayout): GroupLayout.SequentialGroup {
        return layout.createSequentialGroup()
                .addGap(VERTICAL_GAP_SIZE)
                .addComponent(nameLabel, CatalogSwingConstants.VERTICAL_COMPONENT_SIZE, CatalogSwingConstants.VERTICAL_COMPONENT_SIZE, CatalogSwingConstants.VERTICAL_COMPONENT_SIZE)
                .addGap(VERTICAL_GAP_SIZE)
                .addComponent(versionLabel, CatalogSwingConstants.VERTICAL_COMPONENT_SIZE, CatalogSwingConstants.VERTICAL_COMPONENT_SIZE, CatalogSwingConstants.VERTICAL_COMPONENT_SIZE)
                .addGap(VERTICAL_GAP_SIZE)
                .addComponent(authorLabel, CatalogSwingConstants.VERTICAL_COMPONENT_SIZE, CatalogSwingConstants.VERTICAL_COMPONENT_SIZE, CatalogSwingConstants.VERTICAL_COMPONENT_SIZE)
                .addGap(VERTICAL_GAP_SIZE)
                .addComponent(rightsLabel, CatalogSwingConstants.VERTICAL_COMPONENT_SIZE, CatalogSwingConstants.VERTICAL_COMPONENT_SIZE, CatalogSwingConstants.VERTICAL_COMPONENT_SIZE)
                .addGap(VERTICAL_LONG_GAP_SIZE)
                .addComponent(okButton, CatalogSwingConstants.VERTICAL_BUTTON_SIZE, CatalogSwingConstants.VERTICAL_BUTTON_SIZE, CatalogSwingConstants.VERTICAL_BUTTON_SIZE)
                .addGap(VERTICAL_GAP_SIZE)
    }


    /**
     * Initializes labels.
     *
     * @param font   font
     * @param labels labels
     */
    private fun initLabels(font: Font, vararg labels: JLabel) {
        for (label in labels) {
            label.isFocusable = false
            label.font = font
        }
    }

    companion object {

        /**
         * Horizontal label size
         */
        private const val HORIZONTAL_LABEL_SIZE = 200

        /**
         * Horizontal button size
         */
        private const val HORIZONTAL_BUTTON_SIZE = 76

        /**
         * Horizontal button gap size
         */
        private const val HORIZONTAL_BUTTON_GAP_SIZE = 62

        /**
         * Horizontal gap size
         */
        private const val HORIZONTAL_GAP_SIZE = 20

        /**
         * Vertical gap size
         */
        private const val VERTICAL_GAP_SIZE = 20

        /**
         * Vertical long gap size
         */
        private const val VERTICAL_LONG_GAP_SIZE = 40

        /**
         * Font size
         */
        private const val FONT_SIZE = 12

    }

}
