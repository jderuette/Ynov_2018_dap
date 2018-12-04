package fr.ynov.dap.controller;


import javax.servlet.http.HttpServletRequest;

import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

//TODO bof by Djer |Audit Code| Configure PMD/checkstyle, en JavaDoc la description est obligatoirement AVANT les anotations !
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
	public String exceptionHandler(final HttpServletRequest request,Model model, final Exception e) {
		model.addAttribute("errorMessage", e.getMessage());
		return "Error";
	}
}
