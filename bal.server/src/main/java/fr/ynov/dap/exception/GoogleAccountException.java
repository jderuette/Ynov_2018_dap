package fr.ynov.dap.exception;

/**
 * The Class GoogleAccountException.
 */
//TODO bal by Djer |POO| Une exception parente du type "DataMissingException" indiquerait clairement que ces 3 exceptions sont "proches".
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