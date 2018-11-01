package fr.ynov.dap.client.exception;

import fr.ynov.dap.client.dto.in.ExceptionInDto;

/**
 * This exception describe error from server side.
 * @author Kévin Sibué
 *
 */
public class ServerSideException extends Exception {

    /**
     * Default Serial version.
     */
    private static final long serialVersionUID = 1L;

    /**
     * Default constructor.
     * @param error Error from server
     */
    public ServerSideException(final ExceptionInDto error) {
        super(error.getErrorDescription());
    }

}
