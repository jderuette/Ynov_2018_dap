package fr.ynov.dap.exception;

/**
 * The Class MicrosoftAccountException.
 */
public class MicrosoftAccountException extends Exception {

    /** The Constant serialVersionUID. */
    private static final long serialVersionUID = 1L;

    /**
     * Instantiates a new microsoft account exception.
     */
    public MicrosoftAccountException() {
        super("No Microsoft account for the current user");
    }

}

