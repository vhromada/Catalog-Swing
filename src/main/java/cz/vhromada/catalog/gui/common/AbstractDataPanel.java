package cz.vhromada.catalog.gui.common;

import java.util.List;

import javax.swing.GroupLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JPanel;

import cz.vhromada.catalog.entity.Genre;
import cz.vhromada.catalog.entity.Picture;
import cz.vhromada.catalog.facade.PictureFacade;
import cz.vhromada.common.Language;
import cz.vhromada.result.Result;
import cz.vhromada.result.Status;

import org.springframework.util.Assert;

/**
 * An abstract class represents panel with data.
 *
 * @param <T> type of data
 * @author Vladimir Hromada
 */
public abstract class AbstractDataPanel<T> extends JPanel {

    /**
     * Horizontal picture size
     */
    protected static final int HORIZONTAL_PICTURE_SIZE = 200;

    /**
     * Vertical picture size
     */
    protected static final int VERTICAL_PICTURE_SIZE = 180;

    /**
     * Vertical gap size
     */
    protected static final int VERTICAL_GAP_SIZE = 10;

    /**
     * SerialVersionUID
     */
    private static final long serialVersionUID = 1L;

    /**
     * Horizontal label size
     */
    private static final int HORIZONTAL_LABEL_SIZE = 150;

    /**
     * Horizontal data size
     */
    private static final int HORIZONTAL_DATA_SIZE = 600;

    /**
     * Horizontal button size
     */
    private static final int HORIZONTAL_BUTTON_SIZE = 120;

    /**
     * Horizontal gap size
     */
    private static final int HORIZONTAL_GAP_SIZE = 10;

    /**
     * Updates data.
     *
     * @param data data
     * @throws IllegalArgumentException if data are null
     */
    public final void updateData(final T data) {
        Assert.notNull(data, "Data mustn't be null.");

        updateComponentData(data);
    }

    /**
     * Updates component data.
     *
     * @param data data
     */
    protected abstract void updateComponentData(T data);

    /**
     * Initializes data.
     *
     * @param label label
     * @param data  data
     */
    protected static void initData(final JLabel label, final JLabel data) {
        label.setFocusable(false);
        label.setLabelFor(data);
        data.setFocusable(false);
    }

    /**
     * Initializes button.
     *
     * @param button     button
     * @param buttonType button type
     */
    protected final void initButton(final JButton button, final WebPageButtonType buttonType) {
        button.addActionListener(new WebPageButtonActionListener(buttonType) {

            @Override
            protected String getDynamicUrlPart() {
                switch (buttonType) {
                    case WIKI_CZ:
                        return getCzWikiUrl();
                    case WIKI_EN:
                        return getEnWikiUrl();
                    case CSFD:
                        return getCsfdUrl();
                    case IMDB:
                        return Integer.toString(getImdbUrl());
                    default:
                        throw new IllegalArgumentException("Unknown button type - " + buttonType);
                }
            }

        });
    }

    /**
     * Returns URL to czech Wikipedia page
     *
     * @return URL to czech Wikipedia page
     */
    protected abstract String getCzWikiUrl();

    /**
     * Returns URL to english Wikipedia page
     *
     * @return URL to english Wikipedia page
     */
    protected abstract String getEnWikiUrl();

    /**
     * Returns URL to ČSFD page
     *
     * @return URL to ČSFD page
     */
    protected abstract String getCsfdUrl();

    /**
     * Returns URL to IMDB page
     *
     * @return URL to IMDB page
     */
    protected abstract int getImdbUrl();

    /**
     * Adds additional data to result.
     *
     * @param result result
     * @param value  value
     * @param data   data
     */
    protected static void addAdditionalDataToResult(final StringBuilder result, final boolean value, final String data) {
        if (value) {
            if (result.length() == 0) {
                result.append(data.substring(0, 1).toUpperCase());
                result.append(data.substring(1));
            } else {
                result.append(", ");
                result.append(data);
            }
        }
    }

    /**
     * Returns genres as string.
     *
     * @param genres list of genres
     * @return genres as string
     */
    protected static String getGenres(final List<Genre> genres) {
        if (genres == null || genres.isEmpty()) {
            return "";
        }

        final StringBuilder genresString = new StringBuilder();
        for (final Genre genre : genres) {
            genresString.append(genre.getName());
            genresString.append(", ");
        }

        return genresString.substring(0, genresString.length() - 2);
    }

    /**
     * Returns subtitles as string.
     *
     * @param subtitles list of subtitles
     * @return subtitles as string
     */
    protected static String getSubtitles(final List<Language> subtitles) {
        if (subtitles == null || subtitles.isEmpty()) {
            return "";
        }

        final StringBuilder result = new StringBuilder();
        for (final Language subtitle : subtitles) {
            result.append(subtitle);
            result.append(" / ");
        }

        return result.substring(0, result.length() - 3);
    }

    /**
     * Loads picture.
     *
     * @param picture       picture
     * @param pictureFacade facade for pictures
     * @param pictureData   label for picture
     */
    protected static void loadPicture(final Integer picture, final PictureFacade pictureFacade, final JLabel pictureData) {
        if (picture == null) {
            pictureData.setIcon(null);
        } else {
            final Result<Picture> pictureResult = pictureFacade.get(picture);
            if (Status.OK == pictureResult.getStatus()) {
                pictureData.setIcon(new ImageIcon(pictureResult.getData().getContent()));
            } else {
                throw new IllegalArgumentException("Can't get data. " + pictureResult);
            }
        }
    }

    /**
     * Creates layout.
     */
    protected final void createLayout() {
        final GroupLayout layout = new GroupLayout(this);
        setLayout(layout);
        layout.setHorizontalGroup(createHorizontalLayout(layout));
        layout.setVerticalGroup(createVerticalLayout(layout));
    }

    /**
     * Returns horizontal layout for components.
     *
     * @param layout layout
     * @param group  group
     * @return horizontal layout for components
     */
    protected abstract GroupLayout.Group getHorizontalLayoutWithComponents(GroupLayout layout, GroupLayout.Group group);

    /**
     * Returns horizontal layout for label component with data component.
     *
     * @param layout layout
     * @param label  label
     * @param data   data
     * @return horizontal layout for label component with data component
     */
    protected GroupLayout.Group createHorizontalDataComponents(final GroupLayout layout, final JLabel label, final JLabel data) {
        return layout.createSequentialGroup()
            .addComponent(label, HORIZONTAL_LABEL_SIZE, HORIZONTAL_LABEL_SIZE, HORIZONTAL_LABEL_SIZE)
            .addGap(HORIZONTAL_GAP_SIZE)
            .addComponent(data, HORIZONTAL_DATA_SIZE, HORIZONTAL_DATA_SIZE, HORIZONTAL_DATA_SIZE);
    }

    /**
     * Returns horizontal layout for buttons.
     *
     * @param layout  layout
     * @param buttons buttons
     * @return horizontal layout for buttons
     */
    protected GroupLayout.Group createHorizontalButtons(final GroupLayout layout, final JButton... buttons) {
        final GroupLayout.Group result = layout.createSequentialGroup();
        for (int i = 0; i < buttons.length - 1; i++) {
            result.addComponent(buttons[i], HORIZONTAL_BUTTON_SIZE, HORIZONTAL_BUTTON_SIZE, HORIZONTAL_BUTTON_SIZE)
                .addGap(HORIZONTAL_GAP_SIZE);
        }

        return result.addComponent(buttons[buttons.length - 1], HORIZONTAL_BUTTON_SIZE, HORIZONTAL_BUTTON_SIZE, HORIZONTAL_BUTTON_SIZE);
    }

    /**
     * Returns vertical layout for components.
     *
     * @param layout layout
     * @param group  group
     * @return vertical layout for components
     */
    protected abstract GroupLayout.Group getVerticalLayoutWithComponents(GroupLayout layout, GroupLayout.Group group);

    /**
     * Returns vertical layout for label component with data component.
     *
     * @param layout layout
     * @param label  label component
     * @param data   data component
     * @return vertical layout for label component with data component
     */
    protected GroupLayout.Group createVerticalComponents(final GroupLayout layout, final JComponent label, final JComponent data) {
        return layout.createParallelGroup()
            .addComponent(label, CatalogSwingConstants.VERTICAL_COMPONENT_SIZE, CatalogSwingConstants.VERTICAL_COMPONENT_SIZE,
                CatalogSwingConstants.VERTICAL_COMPONENT_SIZE)
            .addComponent(data, CatalogSwingConstants.VERTICAL_COMPONENT_SIZE, CatalogSwingConstants.VERTICAL_COMPONENT_SIZE,
                CatalogSwingConstants.VERTICAL_COMPONENT_SIZE);
    }

    /**
     * Returns vertical layout for buttons.
     *
     * @param layout  layout
     * @param buttons buttons
     * @return vertical layout for buttons
     */
    protected GroupLayout.Group createVerticalButtons(final GroupLayout layout, final JButton... buttons) {
        final GroupLayout.Group result = layout.createParallelGroup();
        for (final JButton button : buttons) {
            result.addComponent(button, CatalogSwingConstants.VERTICAL_BUTTON_SIZE, CatalogSwingConstants.VERTICAL_BUTTON_SIZE,
                CatalogSwingConstants.VERTICAL_BUTTON_SIZE);
        }

        return result;
    }

    /**
     * Returns horizontal layout for components.
     *
     * @param layout layout
     * @return horizontal layout for components
     */
    private GroupLayout.Group createHorizontalLayout(final GroupLayout layout) {
        return layout.createSequentialGroup()
            .addGap(HORIZONTAL_GAP_SIZE)
            .addGroup(getHorizontalLayoutWithComponents(layout, layout.createParallelGroup()))
            .addGap(HORIZONTAL_GAP_SIZE);
    }

    /**
     * Returns vertical layout of components.
     *
     * @param layout layout
     * @return vertical layout of components
     */
    private GroupLayout.Group createVerticalLayout(final GroupLayout layout) {
        final GroupLayout.Group components = layout.createSequentialGroup()
            .addGap(VERTICAL_GAP_SIZE);

        return getVerticalLayoutWithComponents(layout, components)
            .addGap(VERTICAL_GAP_SIZE);
    }

}
