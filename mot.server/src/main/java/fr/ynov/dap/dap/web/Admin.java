package fr.ynov.dap.dap.web;

import java.io.IOException;
import java.security.GeneralSecurityException;
import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import fr.ynov.dap.dap.data.google.AppUser;
import fr.ynov.dap.dap.data.google.GoogleAccount;
import fr.ynov.dap.dap.data.interfaces.AppUserRepository;
import fr.ynov.dap.dap.data.microsoft.MicrosoftAccount;

/**
 * The Class Admin.
 */
@Controller
public class Admin {

	@Autowired
	AppUserRepository appUserRepository;

	/**
	 * Admin.
	 *
	 * @param model   the model
	 * @param userKey the user key
	 * @return the thymeleaf template admin
	 * @throws IOException              Signals that an I/O exception has occurred.
	 * @throws GeneralSecurityException the general security exception
	 */
	@RequestMapping("/admin")
	public String admin(ModelMap model, @RequestParam(value = "userKey", required = true) String userKey)
			throws IOException, GeneralSecurityException {
		AppUser appUser = appUserRepository.findByUserKey(userKey);
		ArrayList<MicrosoftAccount> listInfoMicrosoft = new ArrayList<>();
		ArrayList<GoogleAccount> listInfoGoogle = new ArrayList<>();

		if (appUser.getMicrosoftAccount() != null) {
			for (MicrosoftAccount microsoftAccount : appUser.getMicrosoftAccount()) {
				listInfoMicrosoft.add(microsoftAccount);
			}
		}

		if (appUser.getAccounts() != null) {
			for (GoogleAccount googleAccount : appUser.getAccounts()) {
				listInfoGoogle.add(googleAccount);
			}
		}

		model.addAttribute("listInfoMicrosoft", listInfoMicrosoft);
		model.addAttribute("listInfoGoogle", listInfoGoogle);
		model.addAttribute("userKey", "?userKey=" + userKey);

		return "admin";
	}

}
