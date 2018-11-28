package fr.ynov.dap.dap.controller;

import java.io.IOException;
import java.security.GeneralSecurityException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import fr.ynov.dap.dap.data.AppUser;
import fr.ynov.dap.dap.data.AppUserRepostory;
import fr.ynov.dap.dap.data.GoogleAccount;
import fr.ynov.dap.dap.data.MicrosoftAccount;
import fr.ynov.dap.dap.exception.SecretFileAccesException;
import fr.ynov.dap.dap.google.service.ContactService;
import fr.ynov.dap.dap.microsoft.services.MicrosoftContactService;

/**
 *
 * @author David_tepoche
 *
 */
@RestController
@RequestMapping("/contact")
public class ContactController extends BaseController {

    /**
     * link with contactService.
     */
    @Autowired
    private ContactService contactService;

    /**
     * link msContactService.
     */
    @Autowired
    private MicrosoftContactService microsoftContactService;

    /**
     * link appUser repository.
     */
    @Autowired
    private AppUserRepostory appUserRepository;

    /**
     * get the number of contact from userId.
     *
     * @param userKey user token
     * @return number of contact
     * @throws GeneralSecurityException throw if the contactService fail
     * @throws IOException              throw if the contactService fail
     * @throws SecretFileAccesException throw if you can't get the info from the
     *                                  properties
     */
    @GetMapping("/nbrContact/{userKey}")
    public @ResponseBody Integer nbrOfContact(@PathVariable("userKey") final String userKey)
            throws IOException, GeneralSecurityException, SecretFileAccesException {

        AppUser appUser = appUserRepository.findByUserKey(userKey);
        if (appUser == null) {
            getLogger().warn("Utilisateur non present en bdd: " + userKey);
            throw new NullPointerException("Utilisateur non present en base de donnée");
        }

        Integer nbrContact = 0;

        for (GoogleAccount account : appUser.getgAccounts()) {
            nbrContact += contactService.getNbrContact(account.getAccountName());
        }
        for (MicrosoftAccount account : appUser.getmAccounts()) {
            nbrContact += microsoftContactService.getNbrContact(account);
        }
        return nbrContact;
    }

    /**
     * return the name of the current class.
     */
    @Override
    public String getClassName() {
        return ContactController.class.getName();
    }

}
