package fr.ynov.dap.outlookService;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

import javassist.NotFoundException;

@Service
public class ContactService {
    @Autowired
    private MailService outmailService;

    //TODO bes by Djer |SOA| Un service ne devrait pas avoir besoin du "model" ni de la request (se sont des objets sous la responsabilité du "controller")
    public Contact[] listContact(final String accountName, final Model model, final HttpServletRequest request)
            throws NotFoundException {
        OutlookService outlookService = outmailService.ConnexionOutlook(model, request, accountName);
        // Sort by given name in ascending order (A-Z)
        String sort = "givenName ASC";
        // Only return the properties we care about
        String properties = "givenName,surname,companyName,emailAddresses";
        // Return at most 10 contacts
        Integer maxResults = 1000;
        Contact[] listContact = null;
        try {
            PagedResult<Contact> contacts = outlookService.getContacts(sort, properties, maxResults).execute().body();
            listContact = contacts.getValue();
        } catch (IOException e) {
            //TODO bes by Djer |Log4J| Une petite log ? 
        }

        return listContact;
    }

    //TODO bes by Djer |SOA| Un service ne devrait pas avoir besoin du "model" ni de la request (se sont des objets sous la responsabilité du "controller")
    public int nbContact(final String accountName, final Model model, final HttpServletRequest request) throws NotFoundException {
        return listContact(accountName, model, request).length;
    }

}
