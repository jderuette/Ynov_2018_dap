package fr.ynov.dap.exception;

/**
 * The Class UserException.
 */
public class UserException extends Exception {

    /** The Constant serialVersionUID. */
    private static final long serialVersionUID = 1L;

    /**
     * Instantiates a new user exception.
     */
    public UserException() {
        super("User not found");
    }

}
