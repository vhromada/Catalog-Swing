package cz.vhromada.catalog.gui.common;

import javax.swing.ImageIcon;

/**
 * An enumeration represents picture.
 *
 * @author Vladimir Hromada
 */
public enum Picture {

    /**
     * About picture
     */
    ABOUT(new ImageIcon("pics/about.jpg")),

    /**
     * Add picture
     */
    ADD(new ImageIcon("pics/add.jpg")),

    /**
     * Cancel picture
     */
    CANCEL(new ImageIcon("pics/cancel.jpg")),

    /**
     * Catalog application picture
     */
    CATALOG(new ImageIcon("pics/catalog.jpg")),

    /**
     * Choose picture
     */
    CHOOSE(new ImageIcon("pics/choose.jpg")),

    /**
     * Move down picture
     */
    DOWN(new ImageIcon("pics/down.jpg")),

    /**
     * Duplicate picture
     */
    DUPLICATE(new ImageIcon("pics/duplicate.jpg")),

    /**
     * Exit application picture
     */
    EXIT(new ImageIcon("pics/exit.jpg")),

    /**
     * New data picture
     */
    NEW(new ImageIcon("pics/new.jpg")),

    /**
     * OK picture
     */
    OK(new ImageIcon("pics/ok.jpg")),

    /**
     * Remove
     */
    REMOVE(new ImageIcon("pics/remove.jpg")),

    /**
     * Save picture
     */
    SAVE(new ImageIcon("pics/save.jpg")),

    /**
     * Move up picture
     */
    UP(new ImageIcon("pics/up.jpg")),

    /**
     * Update picture
     */
    UPDATE(new ImageIcon("pics/update.jpg"));

    /**
     * Icon
     */
    private final ImageIcon icon;

    /**
     * Creates a new instance of Pictures.
     *
     * @param icon icon
     */
    Picture(final ImageIcon icon) {
        this.icon = icon;
    }

    /**
     * Returns icon.
     *
     * @return icon
     */
    public ImageIcon getIcon() {
        return icon;
    }

}
