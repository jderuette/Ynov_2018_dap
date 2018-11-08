package fr.ynov.dap.exception;

/**
 * Throw this exception when use not have any microsoft account.
 * @author Kévin Sibué
 *
 */
public class NoMicrosoftAccountException extends Exception {

    /**
     * I don't know.
     */
    private static final long serialVersionUID = 1L;

    /**
     * Default constructor.
     */
    public NoMicrosoftAccountException() {
        super("No Microsoft account for the current user");
    }

}
