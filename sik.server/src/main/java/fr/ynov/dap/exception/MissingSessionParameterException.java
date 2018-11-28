package fr.ynov.dap.exception;

/**
 * Represent an error when a session params missing.
 * @author Kévin Sibué
 *
 */
public class MissingSessionParameterException extends Exception {

    /**
     * I don't know.
     */
    private static final long serialVersionUID = 1L;

    /**
     * Default constructor.
     */
    public MissingSessionParameterException() {
        super("Missing session params");
    }

}
