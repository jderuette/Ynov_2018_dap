package com.ynov.dap.controller.microsoft;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.ynov.dap.controller.BaseController;
import com.ynov.dap.service.microsoft.MicrosoftMailService;

@Controller
@RequestMapping("mail")
public class MicrosoftMailController extends BaseController {

	@Autowired
	private MicrosoftMailService microsoftMailService;

	@RequestMapping("/microsoft/{appUser}/view")
	public String mail(@PathVariable final String appUser, final Model model, final HttpServletRequest request,
			final RedirectAttributes redirectAttributes) throws IOException {
		
		model.addAttribute("accounts", microsoftMailService.getEmails(appUser));

		return "microsoft/mail";
	}
	
	@Override
	public String getClassName() {
		return MicrosoftMailController.class.getName();
	}
}