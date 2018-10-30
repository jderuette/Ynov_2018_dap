package fr.ynov.dap.web;

import java.io.IOException;
import java.security.GeneralSecurityException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import fr.ynov.dap.dap.GoogleGmail;

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
     * @param user utilisateur demandant la connexion
     * @return liste des labels de la boite mail
     * @throws IOException exception
     * @throws GeneralSecurityException exception
     */
  //TODO roa by Djer évite les majuscule dans les chemin d'URL, le support n'est pas garanti !
    @RequestMapping("/Gmail/listLabel")
    public List<String> getListLabel(final @RequestParam("userKey") String user)
            throws IOException, GeneralSecurityException {
        return gMail.getListLabel(user);
    }
    /**
     * Récupération du nombre de mail non lu dans la boite gmail de l'utilisateur.
     * @param user utilisateur
     * @return nombre mail non lu
     * @throws IOException exception
     * @throws GeneralSecurityException exception
     */
  //TODO roa by Djer évite les majuscule dans les chemin d'URL, le support n'est pas garanti !
    @RequestMapping("/Gmail/nbMailNonLu")
    public Integer getNbMailNonLu(final @RequestParam("userKey") String user)
            throws IOException, GeneralSecurityException {
        return gMail.getNbMailNonLu(user);
    }
}
