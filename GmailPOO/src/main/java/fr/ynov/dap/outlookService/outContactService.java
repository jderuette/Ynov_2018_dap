/**
 * 
 */
package fr.ynov.dap.outlookService;

import java.io.IOException;

import org.springframework.stereotype.Service;

/**
 * @author acer
 *
 */
@Service
//TODO bes by Djer |POO| Le nom de ta classe doit commencer par une majuscule
public class outContactService {

    //TODO bes by Djer |SOA| C'est étrange un service (MailService) qui à besoin d'un autre service (OutlookService). En generale on a : controller -> Service -> Dao -> (BDD || Api externe)
    //TODO bes by Djer |IOC| Injecte se service, ne demande pas à l'appelant de le fournir
    public int nbContact(OutlookService outlookService) {

        // Sort by given name in ascending order (A-Z)
        String sort = "givenName ASC";
        // Only return the properties we care about
        String properties = "givenName,surname,companyName,emailAddresses";
        // Return at most 10 contacts
        Integer maxResults = 1000000;
        PagedResult<Contact> contacts = null;
        try {
            contacts = outlookService.getContacts(sort, properties, maxResults).execute().body();

        } catch (IOException e) {
            //TODO bes by Djer |Log4J| Une petite log ? 
        }
        return contacts.getValue().length;
    }
}
