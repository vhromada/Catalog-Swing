package cz.vhromada.catalog.gui.genre

import cz.vhromada.catalog.entity.Genre
import cz.vhromada.catalog.gui.common.AbstractDataPanel
import javax.swing.GroupLayout
import javax.swing.JLabel

/**
 * A class represents panel with genre's data.
 *
 * @author Vladimir Hromada
 */
class GenreDataPanel(genre: Genre) : AbstractDataPanel<Genre>() {

    /**
     * Label for name
     */
    private val nameLabel = JLabel("Name")

    /**
     * Label with name
     */
    private val nameData = JLabel()

    init {
        updateData(genre)

        initData(nameLabel, nameData)

        createLayout()
    }

    override fun updateComponentData(data: Genre) {
        nameData.text = data.name
    }

    override fun getCzWikiUrl(): String {
        error { "Getting URL to czech Wikipedia page is not allowed for genres." }
    }

    override fun getEnWikiUrl(): String {
        error { "Getting URL to english Wikipedia page is not allowed for genres." }
    }

    override fun getCsfdUrl(): String {
        error { "Getting URL to ČSFD page is not allowed for genres." }
    }

    override fun getImdbUrl(): Int {
        error { "Getting URL to IMDB page is not allowed for genres." }
    }

    override fun getHorizontalLayoutWithComponents(layout: GroupLayout, group: GroupLayout.Group): GroupLayout.Group {
        return group
                .addGroup(createHorizontalDataComponents(layout, nameLabel, nameData))
    }

    override fun getVerticalLayoutWithComponents(layout: GroupLayout, group: GroupLayout.Group): GroupLayout.Group {
        return group
                .addGroup(createVerticalComponents(layout, nameLabel, nameData))
    }

}
