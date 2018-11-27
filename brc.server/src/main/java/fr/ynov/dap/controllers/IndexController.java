package fr.ynov.dap.controllers;

import java.util.UUID;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import fr.ynov.dap.microsoft.AuthHelper;

@Controller
public class IndexController {

	@RequestMapping("/index")
	public String index(Model model, HttpServletRequest request) {
	  return "index";
	}
}
