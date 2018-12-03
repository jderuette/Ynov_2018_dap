/**
 * 
 */
package fr.ynov.dap.GmailPOO.web;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import fr.ynov.Outlook.service.Message;
import fr.ynov.Outlook.service.OutlookService;
import fr.ynov.dap.GmailPOO.data.Account;
import fr.ynov.dap.GmailPOO.data.AppUser;
import fr.ynov.dap.GmailPOO.data.MicrosoftAccount;
import fr.ynov.dap.GmailPOO.metier.Data;
import fr.ynov.dap.GmailPOO.metier.MailService;
import fr.ynov.dap.GmailPOO.metier.MessageOutlook;

/**
 * @author acer
 *
 */
@Controller
public class MailController {
	@Autowired
	MailService mailService;
	@Autowired
	Data dataBase;

	@RequestMapping("/list/msg/{compte}")
	public String listmail(@PathVariable("compte") String compte, Model model, HttpServletRequest request)
			throws IOException {
		OutlookService outlookService = mailService.ConnexionOutlook(model, request, compte);
		Message[] messages = mailService.Listmail(outlookService);
		model.addAttribute("messageMicrosoft", messages);
		return "ListeMessage";
	}

	@RequestMapping("/allmsg/{userKey}")
	public String allListmail(@PathVariable("userKey") String userKey, Model model, HttpServletRequest request)
			throws IOException {
		try {
			AppUser user = dataBase.consulterUser(userKey);
			if (user != null) {
				List<MessageOutlook> messagesOutlook = new ArrayList<MessageOutlook>();
				for (Account account : dataBase.listAccount1(userKey)) {
					if (account instanceof MicrosoftAccount) {
						OutlookService outlookService = mailService.ConnexionOutlook(model, request,
								account.getAccountName());
						Message[] messages = mailService.Listmail(outlookService);
						MessageOutlook monmsg = new MessageOutlook(account.getAccountName(), messages);
						messagesOutlook.add(monmsg);
						model.addAttribute("messageMicrosoft", messagesOutlook);
					}
				}
			}
		} catch (Exception e) {
			model.addAttribute("error", e.getMessage());
		}
		return "ListeAllMessage";
	}
}
