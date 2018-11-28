package fr.ynov.dap.controller;


import org.springframework.web.bind.annotation.ControllerAdvice;


@ControllerAdvice
public class ExceptionController {
	
	/**
	 * 
	 * @param request Request who launch an exception
	 * @param e       Launched exception
	 * @return The formated exception response This method handle the exception and
	 *         format it to print that error in client side
	 *
	 * @ExceptionHandler(Exception.class) public ResponseEntity<ServerException>
	 *                                    exceptionHandler(final HttpServletRequest
	 *                                    request, final Exception e) {
	 * 
	 *                                    ResponseEntity<ServerException> response =
	 *                                    new ResponseEntity<ServerException>(new
	 *                                    ServerException(e.getMessage()),
	 *                                    HttpStatus.INTERNAL_SERVER_ERROR); return
	 *                                    response; }
	 */
}
