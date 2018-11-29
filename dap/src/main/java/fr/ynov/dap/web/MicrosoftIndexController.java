package fr.ynov.dap.web;

import java.util.UUID;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import fr.ynov.dap.AuthHelper;

@Controller
/**
 * Affiche l'index microsoft, la page à partir de laquelle on peut se log
 * @author abaracas
 *
 */
public class MicrosoftIndexController {
	
	@RequestMapping("/index")
	/**
	 * Affiche la page avec les bons attributs.
	 * @param model Model
	 * @param request HttpServlet
	 * @return l'url de base de microsoft
	 */
	public String index(Model model, HttpServletRequest request) {
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
		return "index";
	}
}
