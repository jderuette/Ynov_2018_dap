package fr.ynov.dap.dap.controller.microsoft;

import java.util.UUID;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

import fr.ynov.dap.dap.microsoft.auth.AuthHelper;

@Controller
public class IndexController {
	
	@RequestMapping(value= {"/index"})
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
	  return "index";
	}
}
