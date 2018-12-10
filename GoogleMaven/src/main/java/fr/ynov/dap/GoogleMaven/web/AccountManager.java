package fr.ynov.dap.GoogleMaven.web;

import java.io.IOException;
import java.security.GeneralSecurityException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;



import fr.ynov.dap.GoogleMaven.GoogleAccount;
import fr.ynov.dap.GoogleMaven.MicrosoftAccount;

@Service
//TODO elj by Djer |POO| Suprime les classe qui ne te servent plus. Cela complexifie ton projet. (Et ajoute une surcharge de travail à Spring car il y a un @Service)
public class AccountManager {
	@Autowired GoogleAccount ManagerGoogle;
	//@Autowired MicrosoftAccount ManagerMicrosoft;
    

	/*@RequestMapping("/AccountManager")
	public String ManageAccount(@RequestParam(value="userKey") String userKey, final HttpServletRequest request,
			final HttpSession session) throws IOException, GeneralSecurityException, InstantiationException, IllegalAccessException {

		//return ManagerGoogle.addAccount(userKey, request, session);
		
	}*/
}
