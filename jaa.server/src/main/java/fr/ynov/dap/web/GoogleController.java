package fr.ynov.dap.web;

import java.io.IOException;
import java.security.GeneralSecurityException;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;

import fr.ynov.dap.exceptions.AuthorizationException;

/**
 * Controller used by the client.
 * @author adrij
 *
 */
@RestController
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

    /**
     * Handle GeneralSecurityException.
     * @param ex exception
     * @return GeneralSecurity exception for the client.
     */
    @ExceptionHandler(value = GeneralSecurityException.class)
    public ResponseEntity<String> handleGeneralSecurityException(final GeneralSecurityException ex) {
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(ex.getMessage());
    }

    /**
     * Handle server IOException.
     * @param ex exception
     * @return IO exception for the client.
     */
    @ExceptionHandler(value = IOException.class)
    public ResponseEntity<String> handleIOException(final IOException ex) {
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(ex.getMessage());
    }
}
