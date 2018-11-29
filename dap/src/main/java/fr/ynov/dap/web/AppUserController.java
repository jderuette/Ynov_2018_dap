package fr.ynov.dap.web;

import java.io.IOException;
import java.security.GeneralSecurityException;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import fr.ynov.dap.data.AppUser;
import fr.ynov.dap.data.AppUserRepository;

@RestController
/**
 * Accède aux URL relatives aux AppUser
 * @author abaracas
 *
 */
public class AppUserController {
    @Autowired 
    AppUserRepository repository;
    private static Logger LOG = LogManager.getLogger();
    @RequestMapping ("/add/user/{userkey}")
    /**
     * Ajoute un nouveau compte utilisateur applicatif (ne pas confondre avec les comptes google/microsoft).
     * @param userkey AppUser
     * @return Un message décrivant la finalité de l'opération
     * @throws IOException
     * @throws GeneralSecurityException
     */
    public String addNewAccount (@PathVariable final String userkey) throws IOException, GeneralSecurityException {
	try{
	    repository.save(new AppUser(userkey));
	    String message = "Le compte applicatif" + userkey + "a été ajouté avec succès.";
	    LOG.info(message);
	    return message;
	}
	catch (Exception exception){
	    String message = "Impossible d'ajouter le compte. L'erreur rencontrée est : " + exception;
	    LOG.error(message);
	    return message;
	}
    }    
}
