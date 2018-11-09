package fr.ynov.dap.dap.controller;


import javax.servlet.http.HttpServletRequest;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import fr.ynov.dap.dap.model.ServerException;
/**
 * 
 * @author Florent
 * Class who handle all the exception of the app
 */
@ControllerAdvice
public class ExceptionController {
	
	/**
	 * 
	 * @param request Request who launch an exception
	 * @param e Launched exception
	 * @return The formated exception response
	 * This method handle the exception and format it to print that error in client side
	 */
	@ExceptionHandler(Exception.class)
	public ResponseEntity<ServerException> exceptionHandler(final HttpServletRequest request, final Exception e) {
		
		ResponseEntity<ServerException> response = new ResponseEntity<ServerException>(new ServerException(e.getMessage()), HttpStatus.INTERNAL_SERVER_ERROR);
		return response;
	}
}
