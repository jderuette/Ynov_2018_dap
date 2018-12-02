package fr.ynov.dap.controller;

import org.springframework.stereotype.Controller;
//TODO bal by Djer |IDE| Configure les "save action" de ton IDE pour éviter de laisser trainer des imports inutiles !
import org.springframework.web.bind.annotation.RequestMapping;
import java.util.UUID;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import org.springframework.ui.Model;

@Controller
//TODO bal by Djer |POO| Classe à supprimer ? 
public class MicrosoftAccountController {
	/*
	 * @RequestMapping("/index") public String index(Model model, HttpServletRequest
	 * request) { UUID state = UUID.randomUUID(); UUID nonce = UUID.randomUUID();
	 * 
	 * // Save the state and nonce in the session so we can // verify after the auth
	 * process redirects back HttpSession session = request.getSession();
	 * session.setAttribute("expected_state", state);
	 * session.setAttribute("expected_nonce", nonce);
	 * 
	 * String loginUrl = AuthHelper.getLoginUrl(state, nonce);
	 * model.addAttribute("loginUrl", loginUrl); // Name of a definition in
	 * WEB-INF/defs/pages.xml return "index"; }
	 */
}