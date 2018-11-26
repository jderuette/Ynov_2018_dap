package fr.ynov.dap.web;

import org.apache.http.client.HttpResponseException;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;

import com.google.api.client.googleapis.json.GoogleJsonResponseException;

/**
 * @author thibault
 *
 */
public abstract class HandlerErrorController {
    /**
     * Handler for Google Json Exception.
     * @param e Exception
     * @return response with body and status code of error
     */
    @ExceptionHandler(value = GoogleJsonResponseException.class)
    public ResponseEntity<String> googleHandler(final GoogleJsonResponseException e) {

        return ResponseEntity
                .status(e.getStatusCode())
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .body(e.getDetails().toString());
    }

    /**
     * Handler for General HTTP Exception.
     * @param e Exception
     * @return response with body and status code of error
     */
    @ExceptionHandler(value = HttpResponseException.class)
    public ResponseEntity<String> httpErrorHandler(final HttpResponseException e) {

        return ResponseEntity
                .status(e.getStatusCode())
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .body("{\"code\": " + e.getStatusCode() + ", \"message\": \""
                    + e.getMessage().replaceAll("\"", "\\\\\"")
                    + "\"}");
    }

    /**
     * Handler for Exception.
     * @param e Exception
     * @return response with body and status code of error
     */
    @ExceptionHandler(value = Exception.class)
    public ResponseEntity<String> exceptionHandler(final Exception e) {
        return ResponseEntity
                .status(HttpStatus.INTERNAL_SERVER_ERROR)
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .body("{\"code\": 500, \"message\": \"" + e.getMessage().replaceAll("\"", "\\\\\"") + "\"}");
    }
}
