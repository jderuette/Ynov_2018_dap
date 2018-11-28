package fr.ynov.dap.exception;

/**
 * The Class GoogleAccountException.
 */
public class GoogleAccountException extends Exception {

    /**
     * I don't know.
     */
    private static final long serialVersionUID = 1L;

    /**
     * Default constructor.
     */
    public GoogleAccountException() {
        super("No Google account for the current user");
    }

}