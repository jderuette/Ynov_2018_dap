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
     * @param userId correspond au nom d'utilisateur dont il faut chercher le nombre de contact
     * userId put parameter
     * @return numbers of contacts to this user
     * @throws Exception si un problème est survenu lors de l'appel à cette fonction
     */
    @RequestMapping("/contact/{userId}")
    public int getAllContacts(@PathVariable("userId") final String userId) {
        int nbContact = 0;
        try {
            nbContact = peopleService.getNbContact(userId);
        } catch (IOException | GeneralSecurityException e) {
            LOG.error("Un problème est survenu lors de l'appel du service people", "UserId = " + userId, e);
        }
        return nbContact;
    }
}
