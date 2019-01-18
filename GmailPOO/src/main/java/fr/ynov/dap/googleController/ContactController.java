package fr.ynov.dap.googleController;

import java.io.IOException;
import java.security.GeneralSecurityException;
import java.util.ArrayList;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import com.google.api.services.people.v1.model.Name;
import com.google.api.services.people.v1.model.Person;

import fr.ynov.dap.googleService.ContactsService;

@Controller
public class ContactController {
    @Autowired
    private ContactsService contactService;
    private final Logger logger = LogManager.getLogger();

    @RequestMapping("/contactList/google/{accountName}")
    public String listcontacts(@PathVariable("accountName") String accountName, Model model)
            throws IOException, GeneralSecurityException {

        //TODO bes by Djer |MVC| Ne met pas de "text" dans ton controller, c'est le rôle de la vue (ou du client). Ils pourront ausis gérer la langue.
        // Du coups tu pourrais passer à la "vue" le "accountName" et laisser la vue décider de comment exploiter/afficher cette information
        model.addAttribute("add", "List Contact pour :" + accountName);
        List<Person> connections = contactService.connections(accountName);
        List<String> listContact = new ArrayList<String>();
        if (connections != null && connections.size() > 0) {
            for (Person person : connections) {
                List<Name> names = person.getNames();
                if (names != null && names.size() > 0) {
                    listContact.add(person.getNames().get(0).getDisplayName());
                    //TODO bes by Djer |POO| Tu écrase le "ListContact" pour chaque "person", ca n'est pas utile. Alimente ta liste (la ligne juste au dessus) etfait l'ajout de cette **liste** dans le "ListContact" en dehors de la boucle for
                    model.addAttribute("ListContact", listContact);
                }
            }
        } else {
            //TODO bes by Djer |Log4J| Contextualise tes messages (" for accountname :" + accountName). Sur un serveur se message peut se produire pour des dizaines d'utilisateurs et si tu ne sais pas "pour qui" ta log devient quaisment inexploitable
            logger.info("No connections found.");
            model.addAttribute("error", "No connections found.");
        }

        return "Info";
    }

    @RequestMapping("/nbContact/google/{accountName}")
    public String nbcomtact(@PathVariable("accountName") String accountName, Model model)
            throws IOException, GeneralSecurityException {
        //TODO bes by Djer |MVC| Ne met pas de "text" dans ton controller, c'est le rôle de la vue (ou du client).
        model.addAttribute("add", "Nb Contact for :" + accountName);
        //TODO bes by Djer |Thymleaf| Le nom "onSuccess" me parait étrange, il s'agit plutot du "nbContact"
        model.addAttribute("onSuccess", contactService.connections(accountName).size());
        return "Info";
    }
}
