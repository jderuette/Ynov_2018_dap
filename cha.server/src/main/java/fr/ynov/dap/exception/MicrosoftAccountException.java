package fr.ynov.dap.exception;

public class MicrosoftAccountException extends Exception {

    /**
     * I don't know.
     */
    private static final long serialVersionUID = 1L;

    /**
     * Default constructor.
     */
    public MicrosoftAccountException() {
        super("No Microsoft account for the current user");
    }

}