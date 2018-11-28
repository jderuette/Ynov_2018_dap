package fr.ynov.dap.exception;

import java.util.UUID;

/**
 * Error that represent an invalid state.
 * @author Kévin Sibué
 *
 */
public class InvalidStateException extends Exception {

    /**
     * I don't know.
     */
    private static final long serialVersionUID = 1L;

    /**
     * Default constructor.
     */
    public InvalidStateException() {
        super("Invalid state");
    }

    /**
     * Default constructor.
     * @param state used but invalid
     */
    public InvalidStateException(final UUID state) {
        super("Invalid state : " + state);
    }

}
