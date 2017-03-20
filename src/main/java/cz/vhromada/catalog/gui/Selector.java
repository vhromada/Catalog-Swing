package cz.vhromada.catalog.gui;

import javax.swing.GroupLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.SwingUtilities;
import javax.swing.WindowConstants;

import cz.vhromada.catalog.gui.common.CatalogSwingConstants;
import cz.vhromada.catalog.gui.common.DialogResult;
import cz.vhromada.catalog.gui.common.Picture;

/**
 * A class represents main screen for selecting options.
 *
 * @author Vladimir Hromada
 */
public class Selector extends JFrame {

    /**
     * SerialVersionUID
     */
    private static final long serialVersionUID = 1L;

    /**
     * Horizontal button size
     */
    private static final int HORIZONTAL_BUTTON_SIZE = 130;

    /**
     * Horizontal gap size
     */
    private static final int HORIZONTAL_GAP_SIZE = 60;

    /**
     * Vertical gap size
     */
    private static final int VERTICAL_GAP_SIZE = 40;

    /**
     * Button Catalog
     */
    private final JButton catalogButton = new JButton("Catalog");

    /**
     * Button Exit
     */
    private final JButton exitButton = new JButton("Exit");

    /**
     * Creates a new instance of Selector.
     */
    public Selector() {
        setTitle("Catalog - Selector");
        setIconImage(Picture.CATALOG.getIcon().getImage());
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

        catalogButton.addActionListener(e -> catalogAction());

        exitButton.addActionListener(e -> System.exit(0));

        final GroupLayout layout = new GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(createHorizontalLayout(layout));
        layout.setVerticalGroup(createVerticalLayout(layout));

        catalogButton.requestFocusInWindow();
        pack();
        setLocationRelativeTo(getRootPane());
    }

    /**
     * Performs action for button Catalog.
     */
    private void catalogAction() {
        SwingUtilities.invokeLater(() -> {
            setVisible(false);
            final LoadingDialog dialog = new LoadingDialog();
            dialog.setVisible(true);
            if (dialog.getReturnStatus() == DialogResult.OK) {
                dispose();
                new Catalog(dialog.getContext()).setVisible(true);
            } else {
                System.exit(0);
            }
        });
    }

    /**
     * Returns horizontal layout of components.
     *
     * @param layout layout
     * @return horizontal layout of components
     */
    private GroupLayout.SequentialGroup createHorizontalLayout(final GroupLayout layout) {
        final GroupLayout.ParallelGroup buttons = layout.createParallelGroup()
                .addComponent(catalogButton, HORIZONTAL_BUTTON_SIZE, HORIZONTAL_BUTTON_SIZE, Short.MAX_VALUE)
                .addComponent(exitButton, HORIZONTAL_BUTTON_SIZE, HORIZONTAL_BUTTON_SIZE, Short.MAX_VALUE);

        return layout.createSequentialGroup()
                .addGap(0, HORIZONTAL_GAP_SIZE, Short.MAX_VALUE)
                .addGroup(buttons)
                .addGap(0, HORIZONTAL_GAP_SIZE, Short.MAX_VALUE);
    }

    /**
     * Returns vertical layout of components.
     *
     * @param layout layout
     * @return vertical layout of components
     */
    private GroupLayout.SequentialGroup createVerticalLayout(final GroupLayout layout) {
        return layout.createSequentialGroup()
                .addGap(0, VERTICAL_GAP_SIZE, Short.MAX_VALUE)
                .addComponent(catalogButton, CatalogSwingConstants.VERTICAL_BUTTON_SIZE, CatalogSwingConstants.VERTICAL_BUTTON_SIZE, Short.MAX_VALUE)
                .addGap(0, VERTICAL_GAP_SIZE, Short.MAX_VALUE)
                .addComponent(exitButton, CatalogSwingConstants.VERTICAL_BUTTON_SIZE, CatalogSwingConstants.VERTICAL_BUTTON_SIZE, Short.MAX_VALUE)
                .addGap(0, VERTICAL_GAP_SIZE, Short.MAX_VALUE);
    }

}
