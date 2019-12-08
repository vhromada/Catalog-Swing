package cz.vhromada.catalog.gui.common

import javax.swing.ImageIcon

/**
 * An enumeration represents picture.
 *
 * @author Vladimir Hromada
 */
enum class Picture(val icon: ImageIcon) {

    /**
     * About picture
     */
    ABOUT(ImageIcon("pics/about.jpg")),

    /**
     * Add picture
     */
    ADD(ImageIcon("pics/add.jpg")),

    /**
     * Cancel picture
     */
    CANCEL(ImageIcon("pics/cancel.jpg")),

    /**
     * Catalog application picture
     */
    CATALOG(ImageIcon("pics/catalog.jpg")),

    /**
     * Choose picture
     */
    CHOOSE(ImageIcon("pics/choose.jpg")),

    /**
     * Move down picture
     */
    DOWN(ImageIcon("pics/down.jpg")),

    /**
     * Duplicate picture
     */
    DUPLICATE(ImageIcon("pics/duplicate.jpg")),

    /**
     * Exit application picture
     */
    EXIT(ImageIcon("pics/exit.jpg")),

    /**
     * New data picture
     */
    NEW(ImageIcon("pics/new.jpg")),

    /**
     * OK picture
     */
    OK(ImageIcon("pics/ok.jpg")),

    /**
     * Remove
     */
    REMOVE(ImageIcon("pics/remove.jpg")),

    /**
     * Save picture
     */
    SAVE(ImageIcon("pics/save.jpg")),

    /**
     * Move up picture
     */
    UP(ImageIcon("pics/up.jpg")),

    /**
     * Update picture
     */
    UPDATE(ImageIcon("pics/update.jpg"))

}
