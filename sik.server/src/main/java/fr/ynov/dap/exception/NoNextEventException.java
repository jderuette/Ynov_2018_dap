package fr.ynov.dap.exception;

/**
 * No Next event for an account.
 * @author Kévin Sibué
 *
 */
public class NoNextEventException extends Exception {

    /**
     * I don't know.
     */
    private static final long serialVersionUID = 1L;

    /**
     * Default constructor.
     */
    public NoNextEventException() {
        super("No next event for current user");
    }

}
