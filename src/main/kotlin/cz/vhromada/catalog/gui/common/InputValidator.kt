package cz.vhromada.catalog.gui.common

import javax.swing.JButton
import javax.swing.event.DocumentEvent
import javax.swing.event.DocumentListener

/**
 * An abstract class represents input validator.
 *
 * @author Vladimir Hromada
 */
abstract class InputValidator(private val button: JButton) : DocumentListener {

    /**
     * Returns true if input is valid.
     *
     * @return true if input is valid
     */
    abstract val isInputValid: Boolean

    /**
     * Gives notification that there was an insert into the document.
     *
     * @param e the document event
     */
    override fun insertUpdate(e: DocumentEvent) {
        button.isEnabled = isInputValid
    }

    /**
     * Gives notification that a portion of the document has been removed.
     *
     * @param e the document event
     */
    override fun removeUpdate(e: DocumentEvent) {
        button.isEnabled = isInputValid
    }

    /**
     * Gives notification that an attribute or set of attributes changed.
     *
     * @param e the document event
     */
    override fun changedUpdate(e: DocumentEvent) {
        button.isEnabled = isInputValid
    }

}
