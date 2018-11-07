package fr.ynov.dap.dap.handler;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

/**
 *
 * @author David_tepoche
 *
 */
@ControllerAdvice
public class RestResponseEntityExceptionHandler extends ResponseEntityExceptionHandler {

    /**
     * get the logger.
     */
    private final Logger logger = LogManager.getLogger();

    /**
     * handle all exception and return a formatted body.
     *
     * @param ex      the exception not catched by the app
     * @param request the url witch call the error.
     * @return an response entity formatted
     */
    @ExceptionHandler(value = { Exception.class })
    protected ResponseEntity<Object> handleException(final RuntimeException ex, final WebRequest request) {

        logger.error("Un erreur est survenue suite a l'appel de l'url : " + request.getContextPath(), ex);
        return new ResponseEntity<Object>("StackTrace: " + ex.getMessage(), new HttpHeaders(),
                HttpStatus.INTERNAL_SERVER_ERROR);
    }

}
