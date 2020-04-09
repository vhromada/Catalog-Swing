package cz.vhromada.catalog.gui.main

import cz.vhromada.catalog.gui.common.CatalogSwingConstants
import cz.vhromada.catalog.gui.common.Picture
import org.springframework.context.ConfigurableApplicationContext
import javax.swing.GroupLayout
import javax.swing.JButton
import javax.swing.JFrame
import javax.swing.SwingUtilities
import javax.swing.WindowConstants
import kotlin.system.exitProcess

/**
 * A class represents main screen for selecting options.
 *
 * @author Vladimir Hromada
 */
class Selector(private val context: ConfigurableApplicationContext) : JFrame() {

    /**
     * Button Catalog
     */
    private val catalogButton = JButton("Catalog")

    /**
     * Button Exit
     */
    private val exitButton = JButton("Exit")

    init {
        title = "Catalog - Selector"
        iconImage = Picture.CATALOG.icon.image
        defaultCloseOperation = WindowConstants.EXIT_ON_CLOSE

        catalogButton.addActionListener { catalogAction() }

        exitButton.addActionListener { exitAction() }

        val layout = GroupLayout(contentPane)
        contentPane.layout = layout
        layout.setHorizontalGroup(createHorizontalLayout(layout))
        layout.setVerticalGroup(createVerticalLayout(layout))

        catalogButton.requestFocusInWindow()
        pack()
        setLocationRelativeTo(getRootPane())
    }

    /**
     * Performs action for button Catalog.
     */
    private fun catalogAction() {
        SwingUtilities.invokeLater {
            isVisible = false
            dispose()
            Catalog(context).isVisible = true
        }
    }

    /**
     * Performs action for button Exit.
     */
    private fun exitAction() {
        context.close()
        exitProcess(0)
    }

    /**
     * Returns horizontal layout of components.
     *
     * @param layout layout
     * @return horizontal layout of components
     */
    private fun createHorizontalLayout(layout: GroupLayout): GroupLayout.SequentialGroup {
        val buttons = layout.createParallelGroup()
                .addComponent(catalogButton, HORIZONTAL_BUTTON_SIZE, HORIZONTAL_BUTTON_SIZE, Short.MAX_VALUE.toInt())
                .addComponent(exitButton, HORIZONTAL_BUTTON_SIZE, HORIZONTAL_BUTTON_SIZE, Short.MAX_VALUE.toInt())

        return layout.createSequentialGroup()
                .addGap(0, HORIZONTAL_GAP_SIZE, Short.MAX_VALUE.toInt())
                .addGroup(buttons)
                .addGap(0, HORIZONTAL_GAP_SIZE, Short.MAX_VALUE.toInt())
    }

    /**
     * Returns vertical layout of components.
     *
     * @param layout layout
     * @return vertical layout of components
     */
    private fun createVerticalLayout(layout: GroupLayout): GroupLayout.SequentialGroup {
        return layout.createSequentialGroup()
                .addGap(0, VERTICAL_GAP_SIZE, Short.MAX_VALUE.toInt())
                .addComponent(catalogButton, CatalogSwingConstants.VERTICAL_BUTTON_SIZE, CatalogSwingConstants.VERTICAL_BUTTON_SIZE, Short.MAX_VALUE.toInt())
                .addGap(0, VERTICAL_GAP_SIZE, Short.MAX_VALUE.toInt())
                .addComponent(exitButton, CatalogSwingConstants.VERTICAL_BUTTON_SIZE, CatalogSwingConstants.VERTICAL_BUTTON_SIZE, Short.MAX_VALUE.toInt())
                .addGap(0, VERTICAL_GAP_SIZE, Short.MAX_VALUE.toInt())
    }

    companion object {

        /**
         * Horizontal button size
         */
        private const val HORIZONTAL_BUTTON_SIZE = 130

        /**
         * Horizontal gap size
         */
        private const val HORIZONTAL_GAP_SIZE = 60

        /**
         * Vertical gap size
         */
        private const val VERTICAL_GAP_SIZE = 40
    }

}
