package fr.ynov.dap.exception;

/**
 * Exception used when user not found.
 * @author Kévin Sibué
 *
 */
public class UserNotFoundException extends Exception {

    /**
     * I don't know.
     */
    private static final long serialVersionUID = 1L;

    /**
     * Default constructor.
     */
    public UserNotFoundException() {
        super("User not found");
    }

}
