/**
 * 
 */
package fr.ynov.dap.outlookController;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import fr.ynov.dap.data.Account;
import fr.ynov.dap.data.AppUser;
import fr.ynov.dap.data.MicrosoftAccount;
import fr.ynov.dap.metier.Data;
import fr.ynov.dap.metier.MessageOutlook;
import fr.ynov.dap.outlookService.MailService;
import fr.ynov.dap.outlookService.Message;
import fr.ynov.dap.outlookService.OutlookService;
import javassist.NotFoundException;

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

	@RequestMapping("/list/email")
	public String listmail(@RequestParam String accountName, Model model, HttpServletRequest request)
			throws IOException, NotFoundException {
		OutlookService outlookService = mailService.ConnexionOutlook(model, request, accountName);
		Message[] messages = mailService.Listmail(outlookService);
		model.addAttribute("messageMicrosoft", messages);
		return "ListeMessage";
	}

	@RequestMapping("/all/email/{userKey}")
	public String allListmail(@PathVariable("userKey") String userKey, Model model, HttpServletRequest request)
			throws IOException {
		try {
			AppUser user = dataBase.consulterUser(userKey);
			if (user != null) {
				List<MessageOutlook> messagesOutlook = new ArrayList<MessageOutlook>();
				for (Account account : dataBase.listAccount(userKey)) {
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
