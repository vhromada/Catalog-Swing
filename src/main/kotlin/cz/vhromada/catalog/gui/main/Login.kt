package cz.vhromada.catalog.gui.main

import cz.vhromada.catalog.gui.common.CatalogSwingConstants
import cz.vhromada.catalog.gui.common.InputValidator
import cz.vhromada.catalog.gui.common.Picture
import org.springframework.context.ConfigurableApplicationContext
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.AuthenticationException
import org.springframework.security.core.context.SecurityContextHolder
import java.awt.Color
import javax.swing.GroupLayout
import javax.swing.JButton
import javax.swing.JComponent
import javax.swing.JFrame
import javax.swing.JLabel
import javax.swing.JPasswordField
import javax.swing.JTextField
import javax.swing.SwingUtilities
import javax.swing.WindowConstants
import javax.swing.event.DocumentEvent
import javax.swing.text.JTextComponent


/**
 * A class represents main screen for login.
 *
 * @author Vladimir Hromada
 */
class Login(private val context: ConfigurableApplicationContext) : JFrame() {

    /**
     * Label for username
     */
    private val usernameLabel = JLabel("Username")

    /**
     * Text field for username
     */
    private val usernameData = JTextField()

    /**
     * Label for password
     */
    private val passwordLabel = JLabel("Password")

    /**
     * Text field for password
     */
    private val passwordData = JPasswordField()

    /**
     * Button Login
     */
    private val loginButton = JButton("Login")

    /**
     * Label for bad credentials
     */
    private val badCredentialsLabel = JLabel("Bad credentials")

    init {
        title = "Catalog - Login"
        iconImage = Picture.CATALOG.icon.image
        defaultCloseOperation = WindowConstants.EXIT_ON_CLOSE

        initLabelComponent(usernameLabel, usernameData)
        initLabelComponent(passwordLabel, passwordData)

        addInputValidator(usernameData)
        addInputValidator(passwordData)

        loginButton.isEnabled = false
        loginButton.addActionListener { loginAction() }

        badCredentialsLabel.foreground = Color.RED
        badCredentialsLabel.isVisible = false

        val layout = GroupLayout(contentPane)
        contentPane.layout = layout
        layout.setHorizontalGroup(createHorizontalLayout(layout))
        layout.setVerticalGroup(createVerticalLayout(layout))

        isResizable = false
        pack()
        setLocationRelativeTo(getRootPane())
    }

    /**
     * Performs action for button Login.
     */
    private fun loginAction() {
        SwingUtilities.invokeLater {
            try {
                val authenticationToken = UsernamePasswordAuthenticationToken(usernameData.text, String(passwordData.password))
                val authentication = context.getBean(AuthenticationManager::class.java).authenticate(authenticationToken)
                SecurityContextHolder.getContext().authentication = authentication
                isVisible = false
                dispose()
                Selector(context).isVisible = true
            } catch (ex: AuthenticationException) {
                badCredentialsLabel.isVisible = true
            }
        }
    }

    /**
     * Initializes label with component.
     *
     * @param label     label
     * @param component component
     */
    private fun initLabelComponent(label: JLabel, component: JComponent) {
        label.labelFor = component
        label.isFocusable = false
    }

    /**
     * Adds validator to input.
     *
     * @param input input
     */
    private fun addInputValidator(input: JTextComponent) {
        input.document.addDocumentListener(object : InputValidator(loginButton) {

            override val isInputValid: Boolean
                get() = usernameData.text.isNotBlank() && passwordData.password.isNotEmpty()

            override fun insertUpdate(e: DocumentEvent) {
                super.insertUpdate(e)
                badCredentialsLabel.isVisible = false
            }

            override fun removeUpdate(e: DocumentEvent) {
                super.removeUpdate(e)
                badCredentialsLabel.isVisible = false
            }

            override fun changedUpdate(e: DocumentEvent) {
                super.changedUpdate(e)
                badCredentialsLabel.isVisible = false
            }

        })
    }

    /**
     * Returns horizontal layout of components.
     *
     * @param layout layout
     * @return horizontal layout of components
     */
    private fun createHorizontalLayout(layout: GroupLayout): GroupLayout.SequentialGroup {
        val credentials = layout.createSequentialGroup()
                .addGap(HORIZONTAL_LABEL_SIZE)
                .addComponent(badCredentialsLabel)
                .addGap(HORIZONTAL_LABEL_SIZE)

        val button = layout.createSequentialGroup()
                .addGap(HORIZONTAL_LABEL_SIZE)
                .addComponent(loginButton, HORIZONTAL_BUTTON_SIZE, HORIZONTAL_BUTTON_SIZE, HORIZONTAL_BUTTON_SIZE)
                .addGap(HORIZONTAL_LABEL_SIZE)

        val components = layout.createParallelGroup()
                .addGroup(createHorizontalComponents(layout, usernameLabel, usernameData))
                .addGroup(createHorizontalComponents(layout, passwordLabel, passwordData))
                .addGroup(button)
                .addGroup(credentials)

        return layout.createSequentialGroup()
                .addGap(HORIZONTAL_GAP_SIZE)
                .addGroup(components)
                .addGap(HORIZONTAL_GAP_SIZE)
    }

    /**
     * Returns horizontal layout for label component with data component.
     *
     * @param layout         layout
     * @param labelComponent label component
     * @param dataComponent  data component
     * @return horizontal layout for label component with data component
     */
    private fun createHorizontalComponents(layout: GroupLayout, labelComponent: JComponent, dataComponent: JComponent): GroupLayout.Group {
        return layout.createSequentialGroup()
                .addComponent(labelComponent, HORIZONTAL_LABEL_SIZE, HORIZONTAL_LABEL_SIZE, HORIZONTAL_LABEL_SIZE)
                .addGap(HORIZONTAL_DATA_GAP_SIZE)
                .addComponent(dataComponent, HORIZONTAL_DATA_SIZE, HORIZONTAL_DATA_SIZE, HORIZONTAL_DATA_SIZE)
    }


    /**
     * Returns vertical layout of components.
     *
     * @param layout layout
     * @return vertical layout of components
     */
    private fun createVerticalLayout(layout: GroupLayout): GroupLayout.SequentialGroup {
        return layout.createSequentialGroup()
                .addGap(VERTICAL_GAP_SIZE)
                .addGroup(createVerticalComponents(layout, usernameLabel, usernameData))
                .addGap(VERTICAL_GAP_SIZE)
                .addGroup(createVerticalComponents(layout, passwordLabel, passwordData))
                .addGap(VERTICAL_GAP_SIZE)
                .addComponent(loginButton, CatalogSwingConstants.VERTICAL_BUTTON_SIZE, CatalogSwingConstants.VERTICAL_BUTTON_SIZE, CatalogSwingConstants.VERTICAL_BUTTON_SIZE)
                .addGap(VERTICAL_GAP_SIZE)
                .addComponent(badCredentialsLabel)
                .addGap(VERTICAL_GAP_SIZE)
    }

    /**
     * Returns vertical layout for label component with data component.
     *
     * @param layout         layout
     * @param labelComponent label component
     * @param dataComponent  data component
     * @return vertical layout for label component with data component
     */
    private fun createVerticalComponents(layout: GroupLayout, labelComponent: JComponent, dataComponent: JComponent): GroupLayout.Group {
        return layout.createParallelGroup()
                .addComponent(labelComponent, CatalogSwingConstants.VERTICAL_COMPONENT_SIZE, CatalogSwingConstants.VERTICAL_COMPONENT_SIZE, CatalogSwingConstants.VERTICAL_COMPONENT_SIZE)
                .addComponent(dataComponent, CatalogSwingConstants.VERTICAL_COMPONENT_SIZE, CatalogSwingConstants.VERTICAL_COMPONENT_SIZE, CatalogSwingConstants.VERTICAL_COMPONENT_SIZE)
    }


    companion object {

        /**
         * Horizontal label size
         */
        private const val HORIZONTAL_LABEL_SIZE = 100

        /**
         * Horizontal data size
         */
        private const val HORIZONTAL_DATA_SIZE = 200

        /**
         * Horizontal button size
         */
        private const val HORIZONTAL_BUTTON_SIZE = 130

        /**
         * Horizontal size of gap between label and data
         */
        private const val HORIZONTAL_DATA_GAP_SIZE = 10

        /**
         * Horizontal gap size
         */
        private const val HORIZONTAL_GAP_SIZE = 60

        /**
         * Vertical gap size
         */
        private const val VERTICAL_GAP_SIZE = 40
    }

}

