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
import fr.ynov.dap.googleService.ContactsService;
import fr.ynov.dap.metier.Data;
import fr.ynov.dap.outlookService.ContactService;

/**
 * Controller de tous les contact google et microsoft.
 *
 * @author acer
 *
 */
@Controller
public class ContactConroller {
    @Autowired
    Data dataBase;
    @Autowired
    ContactService ContactServiceOutlook;
    @Autowired
    ContactsService contactsServiceGoogle;

    @RequestMapping("/nbContact/all/{userKey}")
    public String nbContact(@PathVariable("userKey") final String userKey, final Model model, final HttpServletRequest request) {
        model.addAttribute("add", "NB ALL CONTACT FOR :" + userKey);
        List<Account> accounts = dataBase.listAccount(userKey);
        int nbContact = 0;
        String error = "";
        for (Account account : accounts) {

            if (account instanceof MicrosoftAccount) {
                try {
                    nbContact += ContactServiceOutlook.nbContact(account.getAccountName(), model, request);

                } catch (Exception e) {
                    //TODO bes by Djer |Log4J| Une petite log ?
                    error += " erreur authentification pour le compte " + account.getAccountName();
                }

            } else if (account instanceof GoogleAccount) {
                try {
                    nbContact += contactsServiceGoogle.connections(account.getAccountName()).size();

                } catch (Exception e) {
                    //TODO bes by Djer |Log4J| Une petite log ?
                    error += " erreur authentification pour le compte " + account.getAccountName();
                }
            }

        }
        //TODO bes by Djer |MVC| "onSuccess" est un nom un peu étrange. "nbContact" semblerais plus approrié (cela rendra ton "thymeleaf" plus lisible)
        model.addAttribute("onSuccess", nbContact);
        model.addAttribute("error", error);
        return "Info";
    }
}
