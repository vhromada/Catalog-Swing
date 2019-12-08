package cz.vhromada.catalog.gui.common

/**
 * An enumeration represents type of button for showing web page.
 *
 * @author Vladimir Hromada
 */
enum class WebPageButtonType(val baseUrl: String) {

    /**
     * Button for czech Wikipedia
     */
    WIKI_CZ("http://cs.wikipedia.org/wiki/"),

    /**
     * Button for english Wikipedia
     */
    WIKI_EN("http://en.wikipedia.org/wiki/"),

    /**
     * Button for ČSFD
     */
    CSFD("http://www.csfd.cz/film/"),

    /**
     * Button for IMDB
     */
    IMDB("http://www.imdb.com/title/tt")

}
