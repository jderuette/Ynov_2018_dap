package fr.ynov.dap.web;

import java.io.IOException;
import java.security.GeneralSecurityException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import fr.ynov.dap.service.GMailService;
@Controller
/**
 * Pres en charges les url liés aux mails google
 * @author abaracas
 *
 */
public class GMailController {
    @Autowired GMailService gmailService;
    /**
     * Indique le nombre de mails non lus.
     * @param accountName le compte concerné
     * @return l'url où sont affichés les résultats
     * @throws IOException exception
     * @throws GeneralSecurityException exception
     */
    @RequestMapping("/email/unreadMails/{accountName}")
    public String getUnreadMessageCount(Model model, @PathVariable final String accountName) throws IOException, GeneralSecurityException {
	Integer count =  gmailService.getUnreadMessageCount(accountName);
	model.addAttribute("count", count);
	return "mailGoogle";
    }
    
//    @RequestMapping("/email/tousMessagesNonLus/{userKey}")
//    public Integer getAllUnreadMessageCount(@PathVariable final String userKey) throws IOException, GeneralSecurityException {
//	return gmailService.getAllUnreadMessageCount(userKey);
//    }
}
