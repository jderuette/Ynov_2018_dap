package com.ynov.dap.controller.microsoft;

import javax.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.ynov.dap.service.microsoft.MicrosoftContactService;

@Controller
@RequestMapping("contact")
public class MicrosoftContactController {

	@Autowired
	private MicrosoftContactService microsoftContactService;

	@GetMapping("/microsoft/{appUser}/view")
	public String contacts(@PathVariable final String appUser, Model model, HttpServletRequest request, RedirectAttributes redirectAttributes) {
		
		model.addAttribute("accounts", microsoftContactService.getContacts(appUser));
		
		return "microsoft/contact";
	}
}