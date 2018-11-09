package fr.ynov.dap.exception;

import fr.ynov.dap.data.TokenResponse;

/**
 * Represent an error when token sended are invalid.
 * @author Kévin Sibué
 *
 */
public class InvalidTokenException extends Exception {

    /**
     * I don't know.
     */
    private static final long serialVersionUID = 1L;

    /**
     * Default constructor.
     */
    public InvalidTokenException() {
        super("Invalid token");
    }

    /**
     * Default constructor.
     * @param token used but invalid
     */
    public InvalidTokenException(final TokenResponse token) {
        super("Invalid token : " + token.getAccessToken());
    }

}
