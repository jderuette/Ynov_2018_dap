package fr.ynov.dap.dap.controller;

import java.security.GeneralSecurityException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import fr.ynov.dap.dap.service.GoogleAccount;

/**
 * The Class AccountController.
 */
@Controller
@RequestMapping("/account")
public class AccountController {
	
	/** The google account. */
	@Autowired
	private GoogleAccount googleAccount;
	
	 /**
 	 * Adds the acount.
 	 *
 	 * @param userId the user id
 	 * @param request the request
 	 * @param session the session
 	 * @return the string
 	 * @throws GeneralSecurityException the general security exception
 	 */
 	@RequestMapping("/add/{userId}")
	 public String addAcount(@PathVariable final String userId,
		final HttpServletRequest request,
		final HttpSession session) throws GeneralSecurityException {
		return googleAccount.addAccount(userId, request, session);
	}

}
