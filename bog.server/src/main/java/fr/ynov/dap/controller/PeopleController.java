package fr.ynov.dap.controller;

import java.io.IOException;
import java.security.GeneralSecurityException;

import javax.security.auth.callback.Callback;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import fr.ynov.dap.service.GoogleService;
import fr.ynov.dap.service.PeopleGoogleService;

/**
 * @author Mon_PC
 * Class PeopleController
 * Manage every maps of PeopleGoogle
 */
@RestController
public class PeopleController extends GoogleService implements Callback {
    /**.
     * peopleService is managed by Spring on the loadConfig()
     */
    @Autowired
    private PeopleGoogleService peopleService;

    /**.
     * Constructor PeopleController
     * @throws Exception si un problème est survenu lors de la création de l'instance PeopleController
     * @throws IOException si un problème est survenu lors de la création de l'instance PeopleController
     */
    public PeopleController() throws Exception, IOException {
        super();
    }

    /**
     * @param userKey correspond au nom d'utilisateur dont il faut chercher le nombre de contact
     * userKey put parameter
     * @return numbers of contacts to this user
     * @throws GeneralSecurityException generalSecurity
     */
    @RequestMapping("/google/contact/{userKey}")
    public int getAllContacts(@PathVariable("userKey") final String userKey) throws GeneralSecurityException {
        int nbContact = 0;
        try {
            nbContact = peopleService.getNbContact(userKey);
        } catch (IOException e) {
            LOG.error("Un problème est survenu lors de l'appel du service people", "UserId = " + userKey, e);
        }
        return nbContact;
    }
}
