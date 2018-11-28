package fr.ynov.dap.exception;

/**
 * Exception used when there are an error on the configuration.
 * @author Kévin Sibué
 *
 */
public class ConfigurationException extends NullPointerException {

    /**
     * I don't know.
     */
    private static final long serialVersionUID = 1L;

    /**
     * Default constructor.
     */
    public ConfigurationException() {
        super("Error on configuration");
    }

}
