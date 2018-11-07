package fr.ynov.dap.web;

import javax.servlet.http.HttpServletRequest;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import fr.ynov.dap.dto.out.ExceptionOutDto;

/**
 * BaseController.
 * @author Kévin Sibué
 *
 */
public abstract class BaseController {

    /**
     * Logger instance.
     */
    private Logger logger = LogManager.getLogger(getClassName());

    /**
     * Return current instance of Logger.
     * @return Logger
     */
    public Logger getLogger() {
        return logger;
    }

    /**
     * Return current class name.
     * @return Class name
     */
    protected abstract String getClassName();

    /**
     * Method to handle every exception occurred on Spring application.
     * @param request Current http request
     * @param ex Current exception which fire the handler
     * @return Response to client.
     */
    @ExceptionHandler(Exception.class)
    public @ResponseBody final ResponseEntity<ExceptionOutDto> handledException(final HttpServletRequest request,
            final Exception ex) {

        ExceptionOutDto response = new ExceptionOutDto(ex.getLocalizedMessage());

        getLogger().error(ex.getLocalizedMessage());

        return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);

    }

}
