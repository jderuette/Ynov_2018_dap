package com.ynov.dap.microsoft.controllers;

import java.util.UUID;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.ynov.dap.microsoft.business.AuthHelper;

@Controller
public class IndexController {
	
	@RequestMapping("/index/{accountName}")
	public String index(@PathVariable final String accountName, @RequestParam(value = "userKey", required = true) final String userKey, Model model, HttpServletRequest request) {
	  UUID state = UUID.randomUUID();
	  UUID nonce = UUID.randomUUID();

	  // Save the state and nonce in the session so we can
	  // verify after the auth process redirects back
	  HttpSession session = request.getSession();
	  session.setAttribute("expected_state", state);
	  session.setAttribute("expected_nonce", nonce);
	  
	  session.setAttribute("accountName", accountName);
	  session.setAttribute("userKey", userKey);

	  String loginUrl = AuthHelper.getLoginUrl(state, nonce);
	  model.addAttribute("loginUrl", loginUrl);
	  // Name of a definition in WEB-INF/defs/pages.xml
	  return "index";
	}
	
	@RequestMapping("/index")
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
