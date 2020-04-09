package cz.vhromada.catalog.gui.main

import cz.vhromada.catalog.facade.EpisodeFacade
import cz.vhromada.catalog.facade.GameFacade
import cz.vhromada.catalog.facade.GenreFacade
import cz.vhromada.catalog.facade.MovieFacade
import cz.vhromada.catalog.facade.MusicFacade
import cz.vhromada.catalog.facade.PictureFacade
import cz.vhromada.catalog.facade.ProgramFacade
import cz.vhromada.catalog.facade.SeasonFacade
import cz.vhromada.catalog.facade.ShowFacade
import cz.vhromada.catalog.facade.SongFacade
import cz.vhromada.catalog.gui.common.Picture
import cz.vhromada.catalog.gui.game.GamesPanel
import cz.vhromada.catalog.gui.genre.GenresPanel
import cz.vhromada.catalog.gui.movie.MoviesPanel
import cz.vhromada.catalog.gui.music.MusicPanel
import cz.vhromada.catalog.gui.picture.PicturesPanel
import cz.vhromada.catalog.gui.program.ProgramsPanel
import cz.vhromada.catalog.gui.show.ShowsPanel
import org.springframework.context.ConfigurableApplicationContext
import java.awt.Frame
import java.awt.event.InputEvent
import java.awt.event.KeyEvent
import java.awt.event.WindowAdapter
import java.awt.event.WindowEvent
import javax.swing.GroupLayout
import javax.swing.JFrame
import javax.swing.JMenu
import javax.swing.JMenuBar
import javax.swing.JMenuItem
import javax.swing.JOptionPane
import javax.swing.JTabbedPane
import javax.swing.KeyStroke
import javax.swing.SwingUtilities
import javax.swing.WindowConstants
import kotlin.system.exitProcess

/**
 * A class represents catalog.
 *
 * @author Vladimir Hromada
 */
class Catalog(private val context: ConfigurableApplicationContext) : JFrame() {

    /**
     * Menu bar
     */
    private val menuBar = JMenuBar()

    /**
     * Menu file
     */
    private val fileMenu = JMenu("File")

    /**
     * Menu item new
     */
    private val newMenuItem = JMenuItem("New", Picture.NEW.icon)

    /**
     * Menu item save
     */
    private val saveMenuItem = JMenuItem("Save", Picture.SAVE.icon)

    /**
     * Menu item selector
     */
    private val selectorMenuItem = JMenuItem("Selector", Picture.CATALOG.icon)

    /**
     * Menu item exit
     */
    private val exitMenuItem = JMenuItem("Exit", Picture.EXIT.icon)

    /**
     * Menu help
     */
    private val helpMenu = JMenu("Help")

    /**
     * Menu item about
     */
    private val aboutMenuItem = JMenuItem("About", Picture.ABOUT.icon)

    /**
     * Tabbed pane
     */
    private val tabbedPane = JTabbedPane()

    /**
     * Panel for movies
     */
    private val moviesPanel: MoviesPanel

    /**
     * Panel for shows
     */
    private val showsPanel: ShowsPanel

    /**
     * Panel for games
     */
    private val gamesPanel: GamesPanel

    /**
     * Panel for music
     */
    private val musicPanel: MusicPanel

    /**
     * Panel for programs
     */
    private val programsPanel: ProgramsPanel

    /**
     * Panel for genres
     */
    private val genresPanel: GenresPanel

    /**
     * Panel for pictures
     */
    private val picturesPanel: PicturesPanel

    /**
     * Facade for movies
     */
    private lateinit var movieFacade: MovieFacade

    /**
     * Facade for shows
     */
    private lateinit var showFacade: ShowFacade

    /**
     * Facade for games
     */
    private lateinit var gameFacade: GameFacade

    /**
     * Facade for music
     */
    private lateinit var musicFacade: MusicFacade

    /**
     * Facade for programs
     */
    private lateinit var programFacade: ProgramFacade

    /**
     * Facade for genres
     */
    private lateinit var genreFacade: GenreFacade

    /**
     * Facade for pictures
     */
    private lateinit var pictureFacade: PictureFacade

    init {
        defaultCloseOperation = WindowConstants.EXIT_ON_CLOSE
        title = "Catalog"
        iconImage = Picture.CATALOG.icon.image
        initFacades()

        initMenuBar()
        initFileMenu()

        aboutMenuItem.accelerator = KeyStroke.getKeyStroke(KeyEvent.VK_F1, 0)
        aboutMenuItem.addActionListener { SwingUtilities.invokeLater { AboutDialog().isVisible = true } }

        moviesPanel = MoviesPanel(movieFacade, genreFacade, pictureFacade)
        showsPanel = ShowsPanel(showFacade, context.getBean(SeasonFacade::class.java), context.getBean(EpisodeFacade::class.java), genreFacade, pictureFacade)
        gamesPanel = GamesPanel(gameFacade)
        musicPanel = MusicPanel(musicFacade, context.getBean(SongFacade::class.java))
        programsPanel = ProgramsPanel(programFacade)
        genresPanel = GenresPanel(genreFacade)
        picturesPanel = PicturesPanel(pictureFacade)

        initTabbedPane()

        addWindowListener(object : WindowAdapter() {

            override fun windowClosing(e: WindowEvent) {
                closing(true)
            }

        })

        val layout = GroupLayout(contentPane)
        contentPane.layout = layout
        layout.setHorizontalGroup(layout.createSequentialGroup().addComponent(tabbedPane, HORIZONTAL_COMPONENT_SIZE, HORIZONTAL_COMPONENT_SIZE, Short.MAX_VALUE.toInt()))
        layout.setVerticalGroup(layout.createSequentialGroup().addComponent(tabbedPane, VERTICAL_COMPONENT_SIZE, VERTICAL_COMPONENT_SIZE, Short.MAX_VALUE.toInt()))

        pack()
        setLocationRelativeTo(getRootPane())
        extendedState = Frame.MAXIMIZED_BOTH
    }

    /**
     * Initializes facades.
     */
    private fun initFacades() {
        movieFacade = context.getBean(MovieFacade::class.java)
        showFacade = context.getBean(ShowFacade::class.java)
        gameFacade = context.getBean(GameFacade::class.java)
        musicFacade = context.getBean(MusicFacade::class.java)
        programFacade = context.getBean(ProgramFacade::class.java)
        genreFacade = context.getBean(GenreFacade::class.java)
        pictureFacade = context.getBean(PictureFacade::class.java)
    }

    /**
     * Initializes menu bar.
     */
    private fun initMenuBar() {
        menuBar.add(fileMenu)
        menuBar.add(helpMenu)

        initMenu(fileMenu, newMenuItem, saveMenuItem, selectorMenuItem, exitMenuItem)
        initMenu(helpMenu, aboutMenuItem)

        jMenuBar = menuBar
    }

    /**
     * Initializes file menu.
     */
    private fun initFileMenu() {
        newMenuItem.accelerator = KeyStroke.getKeyStroke(KeyEvent.VK_N, InputEvent.CTRL_DOWN_MASK)
        newMenuItem.addActionListener { newAction() }

        saveMenuItem.accelerator = KeyStroke.getKeyStroke(KeyEvent.VK_S, InputEvent.CTRL_DOWN_MASK)
        saveMenuItem.addActionListener { saveAction() }

        selectorMenuItem.accelerator = KeyStroke.getKeyStroke(KeyEvent.VK_A, InputEvent.CTRL_DOWN_MASK)
        selectorMenuItem.addActionListener { selectorAction() }

        exitMenuItem.accelerator = KeyStroke.getKeyStroke(KeyEvent.VK_F4, InputEvent.ALT_DOWN_MASK)
        exitMenuItem.addActionListener { exitAction() }
    }

    /**
     * Initializes tabbed pane.
     */
    private fun initTabbedPane() {
        tabbedPane.addTab("Movies", moviesPanel)
        tabbedPane.addTab("Shows", showsPanel)
        tabbedPane.addTab("Games", gamesPanel)
        tabbedPane.addTab("Music", musicPanel)
        tabbedPane.addTab("Programs", programsPanel)
        tabbedPane.addTab("Genres", genresPanel)
        tabbedPane.addTab("Pictures", picturesPanel)
        tabbedPane.addChangeListener {
            moviesPanel.clearSelection()
            showsPanel.clearSelection()
            gamesPanel.clearSelection()
            musicPanel.clearSelection()
            programsPanel.clearSelection()
            genresPanel.clearSelection()
            picturesPanel.clearSelection()
        }
    }

    /**
     * Performs action for button New.
     */
    private fun newAction() {
        moviesPanel.newData()
        showsPanel.newData()
        gamesPanel.newData()
        musicPanel.newData()
        programsPanel.newData()
        genresPanel.newData()
        picturesPanel.newData()
    }

    /**
     * Performs action for button Save.
     */
    private fun saveAction() {
        save()
    }

    /**
     * Performs action for button Selector.
     */
    private fun selectorAction() {
        closing(false)
        SwingUtilities.invokeLater { Selector(context).isVisible = true }
        isVisible = false
        dispose()
    }

    /**
     * Performs action for button Exit.
     */
    private fun exitAction() {
        closing(true)
        exitProcess(0)
    }

    /**
     * Closes form.
     *
     * @param exit true if exiting
     */
    private fun closing(exit: Boolean) {
        val saved = areMediaSaved() && areProgramsSaved() && genresPanel.saved
        if (!saved) {
            val returnStatus = JOptionPane.showConfirmDialog(this, "Save data?", "", JOptionPane.YES_NO_OPTION, JOptionPane.WARNING_MESSAGE)
            if (returnStatus == JOptionPane.YES_OPTION) {
                save()
            }
        }
        if (exit) {
            context.close()
        }
    }

    /**
     * Returns true if media are saved.
     *
     * @return true if media are saved
     */
    private fun areMediaSaved(): Boolean {
        return moviesPanel.saved && showsPanel.saved && musicPanel.saved
    }

    /**
     * Returns true if programs are saved.
     *
     * @return true if programs are saved
     */
    private fun areProgramsSaved(): Boolean {
        return gamesPanel.saved && programsPanel.saved
    }

    /**
     * Saves data.
     */
    private fun save() {
        movieFacade.updatePositions()
        showFacade.updatePositions()
        gameFacade.updatePositions()
        musicFacade.updatePositions()
        programFacade.updatePositions()
        genreFacade.updatePositions()
        pictureFacade.updatePositions()
        moviesPanel.save()
        showsPanel.save()
        gamesPanel.save()
        musicPanel.save()
        programsPanel.save()
        genresPanel.save()
        picturesPanel.save()
    }

    /**
     * Initializes menu.
     *
     * @param menu      menu
     * @param menuItems menu items
     */
    private fun initMenu(menu: JMenu, vararg menuItems: JMenuItem) {
        for (menuItem in menuItems) {
            menu.add(menuItem)
        }
    }

    companion object {

        /**
         * Horizontal component size
         */
        private const val HORIZONTAL_COMPONENT_SIZE = 400

        /**
         * Vertical component size
         */
        private const val VERTICAL_COMPONENT_SIZE = 400

    }

}
