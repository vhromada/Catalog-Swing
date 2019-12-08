package cz.vhromada.catalog.gui.common

import cz.vhromada.catalog.entity.Genre
import cz.vhromada.catalog.facade.PictureFacade
import cz.vhromada.common.Language
import cz.vhromada.validation.result.Status
import javax.swing.GroupLayout
import javax.swing.ImageIcon
import javax.swing.JButton
import javax.swing.JComponent
import javax.swing.JLabel
import javax.swing.JPanel

/**
 * An abstract class represents panel with data.
 *
 * @param <T> type of data
 * @author Vladimir Hromada
 */
abstract class AbstractDataPanel<T> : JPanel() {

    /**
     * Updates data.
     *
     * @param data data
     * @throws IllegalArgumentException if data are null
     */
    fun updateData(data: T) {
        updateComponentData(data)
    }

    /**
     * Updates component data.
     *
     * @param data data
     */
    protected abstract fun updateComponentData(data: T)

    /**
     * Initializes data.
     *
     * @param label label
     * @param data  data
     */
    protected fun initData(label: JLabel, data: JLabel) {
        label.isFocusable = false
        label.labelFor = data
        data.isFocusable = false
    }

    /**
     * Initializes button.
     *
     * @param button     button
     * @param buttonType button type
     */
    protected fun initButton(button: JButton, buttonType: WebPageButtonType) {
        button.addActionListener(object : WebPageButtonActionListener(buttonType) {

            override val dynamicUrlPart: String
                get() {
                    return when (buttonType) {
                        WebPageButtonType.WIKI_CZ -> getCzWikiUrl()
                        WebPageButtonType.WIKI_EN -> getEnWikiUrl()
                        WebPageButtonType.CSFD -> getCsfdUrl()
                        WebPageButtonType.IMDB -> getImdbUrl().toString().padStart(7, '0')
                    }
                }
        })
    }

    /**
     * Returns URL to czech Wikipedia page
     *
     * @return URL to czech Wikipedia page
     */
    protected abstract fun getCzWikiUrl(): String

    /**
     * Returns URL to english Wikipedia page
     *
     * @return URL to english Wikipedia page
     */
    protected abstract fun getEnWikiUrl(): String

    /**
     * Returns URL to ČSFD page
     *
     * @return URL to ČSFD page
     */
    protected abstract fun getCsfdUrl(): String

    /**
     * Returns URL to IMDB page
     *
     * @return URL to IMDB page
     */
    protected abstract fun getImdbUrl(): Int

    /**
     * Adds additional data to result.
     *
     * @param result result
     * @param value  value
     * @param data   data
     */
    protected fun addAdditionalDataToResult(result: StringBuilder, value: Boolean, data: String) {
        if (value) {
            if (result.isEmpty()) {
                result.append(data.substring(0, 1).toUpperCase())
                result.append(data.substring(1))
            } else {
                result.append(", ")
                result.append(data)
            }
        }
    }

    /**
     * Returns genres as string.
     *
     * @param genres list of genres
     * @return genres as string
     */
    @Suppress("DuplicatedCode")
    protected fun getGenres(genres: List<Genre?>?): String {
        if (genres.isNullOrEmpty()) {
            return ""
        }

        val genresString = StringBuilder()
        for (genre in genres.filterNotNull()) {
            genresString.append(genre.name)
            genresString.append(", ")
        }
        return genresString.substring(0, genresString.length - 2)
    }

    /**
     * Returns subtitles as string.
     *
     * @param subtitles list of subtitles
     * @return subtitles as string
     */
    protected fun getSubtitles(subtitles: List<Language?>?): String {
        if (subtitles.isNullOrEmpty()) {
            return ""
        }

        val result = StringBuilder()
        for (subtitle in subtitles.filterNotNull()) {
            result.append(subtitle)
            result.append(" / ")
        }
        return result.substring(0, result.length - 3)
    }

    /**
     * Loads picture.
     *
     * @param picture       picture
     * @param pictureFacade facade for pictures
     * @param pictureData   label for picture
     */
    protected fun loadPicture(picture: Int?, pictureFacade: PictureFacade, pictureData: JLabel) {
        if (picture == null) {
            pictureData.icon = null
        } else {
            val pictureResult = pictureFacade.get(picture)
            if (Status.OK == pictureResult.status) {
                pictureData.icon = ImageIcon(pictureResult.data!!.content)
            } else {
                throw IllegalArgumentException("Can't get data. $pictureResult")
            }
        }
    }

    /**
     * Creates layout.
     */
    protected fun createLayout() {
        val layout = GroupLayout(this)
        setLayout(layout)
        layout.setHorizontalGroup(createHorizontalLayout(layout))
        layout.setVerticalGroup(createVerticalLayout(layout))
    }

    /**
     * Returns horizontal layout for components.
     *
     * @param layout layout
     * @param group  group
     * @return horizontal layout for components
     */
    protected abstract fun getHorizontalLayoutWithComponents(layout: GroupLayout, group: GroupLayout.Group): GroupLayout.Group

    /**
     * Returns horizontal layout for label component with data component.
     *
     * @param layout layout
     * @param label  label
     * @param data   data
     * @return horizontal layout for label component with data component
     */
    protected fun createHorizontalDataComponents(layout: GroupLayout, label: JLabel, data: JLabel): GroupLayout.Group {
        return layout.createSequentialGroup()
                .addComponent(label, HORIZONTAL_LABEL_SIZE, HORIZONTAL_LABEL_SIZE, HORIZONTAL_LABEL_SIZE)
                .addGap(HORIZONTAL_GAP_SIZE)
                .addComponent(data, HORIZONTAL_DATA_SIZE, HORIZONTAL_DATA_SIZE, HORIZONTAL_DATA_SIZE)
    }

    /**
     * Returns horizontal layout for buttons.
     *
     * @param layout  layout
     * @param buttons buttons
     * @return horizontal layout for buttons
     */
    protected fun createHorizontalButtons(layout: GroupLayout, vararg buttons: JButton): GroupLayout.Group {
        val result = layout.createSequentialGroup()
        for (i in 0 until buttons.size - 1) {
            result.addComponent(buttons[i], HORIZONTAL_BUTTON_SIZE, HORIZONTAL_BUTTON_SIZE, HORIZONTAL_BUTTON_SIZE)
                    .addGap(HORIZONTAL_GAP_SIZE)
        }
        return result.addComponent(buttons[buttons.size - 1], HORIZONTAL_BUTTON_SIZE, HORIZONTAL_BUTTON_SIZE, HORIZONTAL_BUTTON_SIZE)
    }

    /**
     * Returns vertical layout for components.
     *
     * @param layout layout
     * @param group  group
     * @return vertical layout for components
     */
    protected abstract fun getVerticalLayoutWithComponents(layout: GroupLayout, group: GroupLayout.Group): GroupLayout.Group

    /**
     * Returns vertical layout for label component with data component.
     *
     * @param layout layout
     * @param label  label component
     * @param data   data component
     * @return vertical layout for label component with data component
     */
    protected fun createVerticalComponents(layout: GroupLayout, label: JComponent, data: JComponent): GroupLayout.Group {
        return layout.createParallelGroup()
                .addComponent(label, CatalogSwingConstants.VERTICAL_COMPONENT_SIZE, CatalogSwingConstants.VERTICAL_COMPONENT_SIZE, CatalogSwingConstants.VERTICAL_COMPONENT_SIZE)
                .addComponent(data, CatalogSwingConstants.VERTICAL_COMPONENT_SIZE, CatalogSwingConstants.VERTICAL_COMPONENT_SIZE, CatalogSwingConstants.VERTICAL_COMPONENT_SIZE)
    }

    /**
     * Returns vertical layout for buttons.
     *
     * @param layout  layout
     * @param buttons buttons
     * @return vertical layout for buttons
     */
    protected fun createVerticalButtons(layout: GroupLayout, vararg buttons: JButton): GroupLayout.Group {
        val result = layout.createParallelGroup()
        for (button in buttons) {
            result.addComponent(button, CatalogSwingConstants.VERTICAL_BUTTON_SIZE, CatalogSwingConstants.VERTICAL_BUTTON_SIZE, CatalogSwingConstants.VERTICAL_BUTTON_SIZE)
        }
        return result
    }

    /**
     * Returns horizontal layout for components.
     *
     * @param layout layout
     * @return horizontal layout for components
     */
    private fun createHorizontalLayout(layout: GroupLayout): GroupLayout.Group {
        return layout.createSequentialGroup()
                .addGap(HORIZONTAL_GAP_SIZE)
                .addGroup(getHorizontalLayoutWithComponents(layout, layout.createParallelGroup()))
                .addGap(HORIZONTAL_GAP_SIZE)
    }

    /**
     * Returns vertical layout of components.
     *
     * @param layout layout
     * @return vertical layout of components
     */
    private fun createVerticalLayout(layout: GroupLayout): GroupLayout.Group {
        val components = layout.createSequentialGroup()
                .addGap(VERTICAL_GAP_SIZE)

        return getVerticalLayoutWithComponents(layout, components)
                .addGap(VERTICAL_GAP_SIZE)
    }

    companion object {

        /**
         * Horizontal picture size
         */
        const val HORIZONTAL_PICTURE_SIZE = 200

        /**
         * Vertical picture size
         */
        const val VERTICAL_PICTURE_SIZE = 180

        /**
         * Vertical gap size
         */
        const val VERTICAL_GAP_SIZE = 10

        /**
         * Horizontal label size
         */
        private const val HORIZONTAL_LABEL_SIZE = 150

        /**
         * Horizontal data size
         */
        private const val HORIZONTAL_DATA_SIZE = 600

        /**
         * Horizontal button size
         */
        private const val HORIZONTAL_BUTTON_SIZE = 120

        /**
         * Horizontal gap size
         */
        private const val HORIZONTAL_GAP_SIZE = 10

    }

}
