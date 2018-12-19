package fr.ynov.dap.exceptions;

/**
 * Custom exception for Authorization errors.
 * For example, an AuthorizationException is thrown when an invalid userKey is provided.
 * @author adrij
 */
public class AuthorizationException extends Exception {
    /**
     * Needed because it extends Exception class.
     * Apparently, it's used for the serialization.
     * TODO jaa by Djer |POO| (info) C'est utile car les exceptions sont Serializable
     */
    private static final long serialVersionUID = -5428795312657630298L;

    /**
     * default constructor of the exception.
     */
    public AuthorizationException() {
    }

    /**
     * constructor with error message.
     * @param message error message
     */
    public AuthorizationException(final String message) {
        super(message);
    }

    /**
     * constructor with error message and exception.
     * @param message message of the exception
     * @param innerException inner exception
     */
    public AuthorizationException(final String message, final Exception innerException) {
        super(message, innerException);
    }
}
