package fr.ynov.dap.exception;

/**
 * Exception used when no configuration file provided to services.
 * @author Kévin Sibué
 *
 */
public class NoConfigurationException extends NullPointerException {

    /**
     * I don't know.
     */
    private static final long serialVersionUID = 1L;

    /**
     * Default constructor.
     */
    public NoConfigurationException() {
        super("No configuration provided.");
    }

}
