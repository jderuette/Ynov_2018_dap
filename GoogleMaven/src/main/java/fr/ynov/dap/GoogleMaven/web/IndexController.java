package fr.ynov.dap.GoogleMaven.web;

import java.io.IOException;
import java.security.GeneralSecurityException;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

import fr.ynov.dap.GoogleMaven.auth.AuthHelper;

@Service
public class IndexController {
	
	/**
	 * 
	 * @param user
	 * @return index controller with outlook api
	 * @throws IOException
	 * @throws GeneralSecurityException
	 */
    //TODO elj by Djer |Spring| TU peux injecter directement la Session
	public String index(ModelMap model, HttpServletRequest request) {
		UUID state = UUID.randomUUID();
		UUID nonce = UUID.randomUUID();
		
		// Save the state and nonce in the session so we can
		// verify after the auth process redirects back
		HttpSession session = request.getSession();
		session.setAttribute("expected_state", state);
		session.setAttribute("expected_nonce", nonce);
		
		String loginUrl = AuthHelper.getLoginUrl(state, nonce);
		model.addAttribute("loginUrl", loginUrl);
		// Name of a definition in WEB-INF/defs/pages.xml
		return loginUrl;
	}
}
