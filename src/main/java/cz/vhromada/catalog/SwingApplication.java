package cz.vhromada.catalog;

import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

import cz.vhromada.catalog.gui.Selector;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.Import;

/**
 * A class represents Spring boot application.
 *
 * @author Vladimir Hromada
 */
//CHECKSTYLE.OFF: HideUtilityClassConstructor
@SpringBootApplication
@Import(CatalogConfiguration.class)
@SuppressWarnings("NonFinalUtilityClass")
public class SwingApplication {

    /**
     * Logger
     */
    private static final Logger logger = LoggerFactory.getLogger(SwingApplication.class);

    /**
     * Main method.
     *
     * @param args the command line arguments
     */
    //CHECKSTYLE.OFF: UncommentedMain
    @SuppressWarnings("AccessOfSystemProperties")
    public static void main(final String... args) {
        Thread.setDefaultUncaughtExceptionHandler(new ExceptionHandler());
        System.setProperty("sun.awt.exception.handler", ExceptionHandler.class.getName());

        try {
            UIManager.setLookAndFeel("com.sun.java.swing.plaf.windows.WindowsLookAndFeel");

            final ConfigurableApplicationContext context = new SpringApplicationBuilder(SwingApplication.class).headless(false).run(args);
            SwingUtilities.invokeLater(() -> new Selector(context).setVisible(true));
        } catch (final ReflectiveOperationException | UnsupportedLookAndFeelException ex) {
            logger.error("Error in setting look and feel.", ex);
            System.exit(1);
        }
    }
    //CHECKSTYLE.ON: UncommentedMain

    /**
     * A class represents handler for uncaught exception.
     */
    private static class ExceptionHandler implements Thread.UncaughtExceptionHandler {

        @Override
        public void uncaughtException(final Thread t, final Throwable e) {
            logger.error("Exception in Catalog application.", e);
            System.exit(2);
        }

    }

}
//CHECKSTYLE.ON: HideUtilityClassConstructor
