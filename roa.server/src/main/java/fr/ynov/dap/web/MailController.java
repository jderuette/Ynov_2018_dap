package fr.ynov.dap.web;

import java.io.IOException;
import java.security.GeneralSecurityException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import fr.ynov.dap.services.GoogleGmail;

/**
 * Controller pour la manipulation de gmail.
 * @author alex
 */
@RestController
public class MailController {
    /**
     * Récupération du service pour gérer gmail via la google API.
     */
    @Autowired
    private GoogleGmail gMail;
    /**
     * Récupération de la liste des labels de la boite mail de l'utilisateur.
     * @param userKey utilisateur demandant la connexion
     * @return liste des labels de la boite mail
     * @throws IOException exception
     * @throws GeneralSecurityException exception
     */
    @RequestMapping("/Gmail/listLabel")
    public final List<String> getListLabel(final @RequestParam("userKey") String userKey)
            throws IOException, GeneralSecurityException {
        return gMail.getListLabel(userKey);
    }
    /**
     * Récupération du nombre de mail non lu dans la boite gmail de l'utilisateur.
     * @param userKey utilisateur
     * @return nombre mail non lu
     * @throws IOException exception
     * @throws GeneralSecurityException exception
     */
    @RequestMapping("/Gmail/nbMailNonLu")
    public final Integer getNbMailNonLu(final @RequestParam("userKey") String userKey)
            throws IOException, GeneralSecurityException {
        return gMail.getNbMailNonLu(userKey);
    }
}
