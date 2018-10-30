package fr.ynov.dap.exceptions;

/**
 * Custom exception for Authorization errors.
 * For example, an AuthorizationException is thrown when an invalid userKey is provided.
 * @author adrij
 *
 */
public class AuthorizationException extends Exception {
    /**
     * Needed because it extends Exception class.
     * Apparently, it's used for the serialization.
     */
    private static final long serialVersionUID = 1L;

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

}
