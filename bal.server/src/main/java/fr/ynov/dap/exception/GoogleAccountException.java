package fr.ynov.dap.exception;

/**
 * The Class GoogleAccountException.
 */
public class GoogleAccountException extends Exception {

    /** The Constant serialVersionUID. */
    private static final long serialVersionUID = 1L;

    /**
     * Instantiates a new google account exception.
     */
    public GoogleAccountException() {
        super("No Google account for the current user");
    }

}