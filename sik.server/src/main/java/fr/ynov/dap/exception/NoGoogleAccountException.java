package fr.ynov.dap.exception;

/**
 * Throw this exception when use not have any google account.
 * @author Kévin Sibué
 *
 */
public class NoGoogleAccountException extends Exception {

    /**
     * I don't know.
     */
    private static final long serialVersionUID = 1L;

    /**
     * Default constructor.
     */
    public NoGoogleAccountException() {
        super("No Google account for the current user");
    }

}
