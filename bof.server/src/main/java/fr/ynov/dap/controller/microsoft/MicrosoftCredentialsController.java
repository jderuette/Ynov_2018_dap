package fr.ynov.dap.controller.microsoft;

import java.util.HashMap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

import fr.ynov.dap.auth.TokenResponse;
import fr.ynov.dap.service.microsoft.MicrososftCredentialsService;

@Controller
@RequestMapping("/microsoft")
public class MicrosoftCredentialsController {

	@Autowired
	MicrososftCredentialsService microsoftCredentialsService;
	
	@RequestMapping("/credentials")
	public String getCredentials(ModelMap model) {
		
		HashMap<String, TokenResponse> response = microsoftCredentialsService.getCredentials();
		model.addAttribute("credentials", response);
		
		return "MicrosoftCredentials";
		
	}
}
