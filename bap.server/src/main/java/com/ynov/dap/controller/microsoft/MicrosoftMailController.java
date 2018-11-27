package com.ynov.dap.controller.microsoft;

import javax.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.ynov.dap.service.microsoft.MicrosoftMailService;

@Controller
@RequestMapping("mail")
public class MicrosoftMailController {

	@Autowired
	private MicrosoftMailService microsoftMailService;

	@RequestMapping("/microsoft/{appUser}/view")
	public String mail(@PathVariable final String appUser, Model model, HttpServletRequest request,
			RedirectAttributes redirectAttributes) {
		
		model.addAttribute("accounts", microsoftMailService.getEmails(appUser));
		System.out.println("model");
		System.out.println(model);

		return "microsoft/mail";
	}
}