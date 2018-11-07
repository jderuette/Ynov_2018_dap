package fr.ynov.dap.web.api;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import fr.ynov.dap.exceptions.AuthorizationException;

/**
 * Controller used by the client.
 * @author adrij
 *
 */
public abstract class GoogleController {
    /**
     *  Handle authorization exception.
     * @param ex exception
     * @return authorization exception for the client
     */
    @ExceptionHandler(value = AuthorizationException.class)
    public ResponseEntity<String> unauthorizedError(final Exception ex) {
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(ex.getMessage());
    }
    /**
     * Handle server exception.
     * @param ex exception
     * @return all exceptions for the client
     */
    @ExceptionHandler(value = Exception.class)
    public ResponseEntity<String> handleInternalError(final Exception ex) {
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(ex.getMessage());
    }
}
