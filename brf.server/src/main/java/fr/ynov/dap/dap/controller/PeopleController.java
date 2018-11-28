package fr.ynov.dap.dap.controller;

import java.io.IOException;

import javax.security.auth.callback.Callback;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import fr.ynov.dap.dap.service.GoogleService;
import fr.ynov.dap.dap.service.PeopleServiceAPI;

/**
 * @author Florian BRANCHEREAU
 *
 */
@RestController
public class PeopleController extends GoogleService implements Callback {

    /**.
     * declaration de peopleservice
     */
    @Autowired
    private PeopleServiceAPI peopleservice;

    /**
     * @throws Exception constructeur
     * @throws IOException constructeur
     */
    public PeopleController() throws Exception, IOException {
        super();
    }

    /**
     * @param userKey Nom du compte
     * @return Le nombre de contact
     * @throws Exception fonction
     */
    @RequestMapping("/contact")
    public String getAllContacts(@RequestParam("userKey") final String userKey) throws Exception {
        int nbContact = 0;
        nbContact = peopleservice.getNbContact(userKey);
        String response = "Nombre de contacts : " + nbContact;
        return response;
    }
}
