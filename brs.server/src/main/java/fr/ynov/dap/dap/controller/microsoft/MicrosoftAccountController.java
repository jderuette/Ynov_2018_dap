package fr.ynov.dap.dap.controller.microsoft;

import java.io.IOException;
import java.security.GeneralSecurityException;
import java.util.UUID;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.springframework.ui.Model;

import fr.ynov.dap.dap.auth.TokenResponse;
import fr.ynov.dap.dap.controller.google.GmailController;
import fr.ynov.dap.dap.data.AppUser;
import fr.ynov.dap.dap.data.AppUserRepository;
import fr.ynov.dap.dap.data.MicrosoftAccount;
import fr.ynov.dap.dap.service.microsoft.MicrosoftService;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import Utils.LoggerUtils;

@Controller
public class MicrosoftAccountController extends LoggerUtils {
	
	
	
	@Autowired
	private MicrosoftService microsoftService;


	//TODO brs by Djer |Spring| Si tu n'as pas besoin de la requete ou des redirectAttributtes, ne les mets pas dans la signature de la méthode.
	  //TODO brs by Djer |Audit Code| Tes attributs devraient être static
		@RequestMapping("/account/add/microsoft/{accountName}")
		public String addAccount(Model model,@RequestParam("userKey") final String userKey, @PathVariable final String accountName, final HttpServletRequest request,
				final HttpServletResponse response) throws GeneralSecurityException, IOException {
	  UUID state = UUID.randomUUID();
	  UUID nonce = UUID.randomUUID();
	  
	  
	  
	  // Save the state and nonce in the session so we can
	  // verify after the auth process redirects back
	  HttpSession session = request.getSession();
	  session.setAttribute("expected_state", state);
	  session.setAttribute("expected_nonce", nonce);
	  session.setAttribute("userId",userKey);
	  session.setAttribute("name", accountName);
	  String loginUrl = microsoftService.getLoginUrl(state, nonce);
	//TODO brs by Djer |Log4J| Contextualise tes messages ("Login URL : " + loginUrl +" for userKey : " + userKey + " and accountName : " + accountName)
	  LOG.warn(loginUrl);
	  model.addAttribute("loginUrl", loginUrl);
	  response.sendRedirect(loginUrl);
	  //TODO brs by Djer |POO| Ce commentaire n'est pas/plus vrai
	  // Name of a definition in WEB-INF/defs/pages.xml
	  model.addAttribute("content", "welcomeViews"); 
	  return "index";
	}
	
	
}
