package fr.ynov.dap.exception;

/**
 * Represent an error when adding an account failed.
 * @author Kévin Sibué
 *
 */
public class AddAccountFailedException extends Exception {

    /**
     * I don't know.
     */
    private static final long serialVersionUID = 1L;

    /**
     * Default constructor.
     */
    public AddAccountFailedException() {
        super("Add account failed");
    }

}
