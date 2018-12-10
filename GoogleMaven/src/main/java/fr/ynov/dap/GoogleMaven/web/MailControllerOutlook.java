package fr.ynov.dap.GoogleMaven.web;

import java.io.IOException;
import java.security.GeneralSecurityException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import fr.ynov.dap.GoogleMaven.OutlookMailService;
import fr.ynov.dap.GoogleMaven.auth.AuthHelper;
import fr.ynov.dap.GoogleMaven.auth.TokenResponse;
import fr.ynov.dap.GoogleMaven.service.Message;
import fr.ynov.dap.GoogleMaven.service.OutlookService;
import fr.ynov.dap.GoogleMaven.service.OutlookServiceBuilder;
import fr.ynov.dap.GoogleMaven.service.PagedResult;

@Controller
public class MailControllerOutlook {
	@Autowired IndexController indexController;
	@Autowired OutlookMailService outlookMailService;
	/**
	 * 
	 * @param user
	 * @return mail controller with outlook api
	 * @throws IOException
	 * @throws GeneralSecurityException
	 */
	
	@RequestMapping("/Outlookmails")
	public String GetOutlookMails(ModelMap model, HttpServletRequest request, HttpSession session) throws IOException, GeneralSecurityException, InstantiationException, IllegalAccessException{
		
	//	model.addAttribute("nbEmails",mailController.getNbUnreadEmails("jawad"));
		//model.addAttribute("GmailMail",mailController.getNbUnreadEmails(model,request,"jawad"));
		String responce;
		model.addAttribute("UrlOutlookService",indexController.index(model, request));
		try {
			
			model.addAttribute("messages",outlookMailService.mail(model, request));
			responce="Outlookmails";
		} catch (Exception e) {
			// TODO: handle exception
			responce="ConnexionOutlook";
		}
		//if (userKey != null)
		//model.addAttribute("UrlConnection",accountManager.ManageAccount(userKey, request, session));
		//${UrlConnection}
		return responce;
	}
}
