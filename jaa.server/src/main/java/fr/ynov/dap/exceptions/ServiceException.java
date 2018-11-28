package fr.ynov.dap.exceptions;

/**
 * Custom exception for Service errors.
 * For example, an ServiceException when the service can't be instantiate.
 * @author adrij
 */
public class ServiceException extends Exception {

    /**
     * Needed because it extends Exception class.
     * Apparently, it's used for the serialization.
     */
    private static final long serialVersionUID = -4940079005369539394L;

    /**
     * default constructor of the exception.
     */
    public ServiceException() {
    }

    /**
     * constructor with error message.
     * @param message error message
     */
    public ServiceException(final String message) {
        super(message);
    }

    /**
     * constructor with error message and exception.
     * @param message message of the exception
     * @param innerException inner exception
     */
    public ServiceException(final String message, final Exception innerException) {
        super(message, innerException);
    }
}
