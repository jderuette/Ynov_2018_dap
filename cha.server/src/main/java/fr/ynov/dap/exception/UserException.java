package fr.ynov.dap.exception;

/**
 * The Class UserException.
 */
public class UserException extends Exception {

    /**
     * I don't know.
     */
    private static final long serialVersionUID = 1L;

    /**
     * Default constructor.
     */
    public UserException() {
        super("User not found");
    }

}
