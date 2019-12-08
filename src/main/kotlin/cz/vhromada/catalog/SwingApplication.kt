package cz.vhromada.catalog

import cz.vhromada.catalog.gui.Selector
import mu.KotlinLogging
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.builder.SpringApplicationBuilder
import org.springframework.context.annotation.Import
import javax.swing.SwingUtilities
import javax.swing.UIManager
import javax.swing.UnsupportedLookAndFeelException
import kotlin.system.exitProcess

/**
 * A class represents Spring boot application.
 *
 * @author Vladimir Hromada
 */
@SpringBootApplication
@Import(CatalogConfiguration::class)
class SwingApplication

/**
 * Logger
 */
private val logger = KotlinLogging.logger {}

fun main(args: Array<String>) {
    Thread.setDefaultUncaughtExceptionHandler(ExceptionHandler())
    System.setProperty("sun.awt.exception.handler", ExceptionHandler::class.java.name)

    try {
        UIManager.setLookAndFeel("com.sun.java.swing.plaf.windows.WindowsLookAndFeel")

        val context = SpringApplicationBuilder(SwingApplication::class.java).headless(false).run(*args)
        SwingUtilities.invokeLater { Selector(context).isVisible = true }
    } catch (ex: ReflectiveOperationException) {
        logger.error(ex) { "Error in setting look and feel." }
        exitProcess(1)
    } catch (ex: UnsupportedLookAndFeelException) {
        logger.error(ex) { "Error in setting look and feel." }
        exitProcess(1)
    }
}

/**
 * A class represents handler for uncaught exception.
 */
private class ExceptionHandler : Thread.UncaughtExceptionHandler {

    override fun uncaughtException(t: Thread, e: Throwable) {
        logger.error(e) { "Exception in Catalog application." }
        exitProcess(2)
    }

}
