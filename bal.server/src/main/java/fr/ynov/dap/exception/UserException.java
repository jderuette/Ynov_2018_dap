package fr.ynov.dap.exception;

/**
 * The Class UserException.
 */
//TODO bal by Djer |POO| UserNotFoundException serait plus approprié comme nom
//TODO bal by Djer |POO| Une exception parente du type "DataMissingException" indiquerait clairement que ces 3 exceptions sont "proches".
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
