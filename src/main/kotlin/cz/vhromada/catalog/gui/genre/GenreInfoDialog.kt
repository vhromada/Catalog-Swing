package cz.vhromada.catalog.gui.genre

import cz.vhromada.catalog.entity.Genre
import cz.vhromada.catalog.gui.common.AbstractInfoDialog
import javax.swing.GroupLayout
import javax.swing.JLabel
import javax.swing.JTextField

/**
 * A class represents dialog for genre.
 *
 * @author Vladimir Hromada
 */
class GenreInfoDialog : AbstractInfoDialog<Genre> {

    /**
     * Label for name
     */
    private val nameLabel = JLabel("Name")

    /**
     * Text field for name
     */
    private val nameData = JTextField()

    /**
     * Creates a new instance of GenreInfoDialog.
     */
    constructor() {
        init()
    }

    /**
     * Creates a new instance of GenreInfoDialog.
     *
     * @param genre genre
     */
    constructor(genre: Genre) : super(genre) {
        init()
        this.nameData.text = genre.name
    }

    override fun initComponents() {
        initLabelComponent(nameLabel, nameData)

        addInputValidator(nameData)
    }

    override fun processData(objectData: Genre?): Genre {
        if (objectData == null) {
            return Genre(id = null,
                    name = nameData.text,
                    position = null)
        }
        return objectData.copy(name = nameData.text)
    }

    /**
     * Returns true if input is valid: name isn't empty string.
     *
     * @return true if input is valid: name isn't empty string
     */
    override fun isInputValid(): Boolean {
        return nameData.text.isNotBlank()
    }

    override fun getHorizontalLayoutWithComponents(layout: GroupLayout, group: GroupLayout.Group): GroupLayout.Group {
        return group.addGroup(createHorizontalComponents(layout, nameLabel, nameData))
    }

    override fun getVerticalLayoutWithComponents(layout: GroupLayout, group: GroupLayout.Group): GroupLayout.Group {
        return group.addGroup(createVerticalComponents(layout, nameLabel, nameData))
    }

}
