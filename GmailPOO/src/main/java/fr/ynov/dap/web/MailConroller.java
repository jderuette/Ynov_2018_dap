package fr.ynov.dap.web;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import fr.ynov.dap.data.Account;
import fr.ynov.dap.data.GoogleAccount;
import fr.ynov.dap.data.MicrosoftAccount;
import fr.ynov.dap.googleService.GMailService;
import fr.ynov.dap.metier.Data;
import fr.ynov.dap.outlookService.MailService;
import fr.ynov.dap.outlookService.OutlookService;

/**
 * Controller de tous les mail google et microsoft.
 *
 * @author acer
 *
 */
@Controller
public class MailConroller {
    @Autowired
    private MailService mailServiceOutlook;
    @Autowired
    private GMailService gmailService;
    @Autowired
    private Data dataBase;
    @Autowired
    MailService outmailService;

    @RequestMapping("/unreadMail/all/{userKey}")
    public String nbUnreadMail(@PathVariable("userKey") final String userKey, final Model model, final HttpServletRequest request) {
        model.addAttribute("add", "NB ALL unread mail FOR : " + userKey);
        List<Account> accounts = dataBase.listAccount(userKey);
        int nb = 0;
        String error = "";
        for (Account account : accounts) {

            if (account instanceof MicrosoftAccount) {
                try {
                    OutlookService outlookService = outmailService.ConnexionOutlook(model, request,
                            account.getAccountName());
                    nb += mailServiceOutlook.Listmail(outlookService).length;

                } catch (Exception e) {
                  //TODO bes by Djer |Log4J| Une petite log ?
                    error += " erreur authentification pour le compte " + account.getAccountName();
                }

            } else if (account instanceof GoogleAccount) {
                try {
                    nb += gmailService.getListEmails(account.getAccountName()).size();

                } catch (Exception e) {
                  //TODO bes by Djer |Log4J| Une petite log ?
                    error += " erreur authentification pour le compte " + account.getAccountName();
                }
            }

        }
      //TODO bes by Djer |MVC| "onSuccess" est un nom un peu étrange. "nbEmails" semblerais plus approrié (cela rendra ton "thymeleaf" plus lisible)
        model.addAttribute("onSuccess", nb);
        model.addAttribute("error", error);
        return "Info";
    }

}
