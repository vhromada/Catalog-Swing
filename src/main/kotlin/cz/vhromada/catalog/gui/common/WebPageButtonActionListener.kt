package cz.vhromada.catalog.gui.common

import mu.KotlinLogging
import org.springframework.web.util.UriComponentsBuilder
import java.awt.event.ActionEvent
import java.awt.event.ActionListener
import java.io.IOException

/**
 * Logger
 */
private val logger = KotlinLogging.logger {}

/**
 * Abstract class represents action listener for button fow showing web page.
 *
 * @author Vladimir Hromada
 */
abstract class WebPageButtonActionListener(private val buttonType: WebPageButtonType) : ActionListener {

    /**
     * Returns dynamic part of URL.
     *
     * @return dynamic part of URL
     */
    protected abstract val dynamicUrlPart: String

    override fun actionPerformed(e: ActionEvent) {
        val url = buttonType.baseUrl + dynamicUrlPart
        try {
            Runtime.getRuntime().exec("rundll32.exe url.dll,FileProtocolHandler " + UriComponentsBuilder.fromHttpUrl(url).toUriString())
        } catch (ex: IOException) {
            logger.error("Error in showing page {}.", url, ex)
        }

    }

}
