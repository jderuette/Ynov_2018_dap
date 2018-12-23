package fr.ynov.dap.dap.controller;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import fr.ynov.dap.dap.data.AppUser;
import fr.ynov.dap.dap.data.AppUserRepository;

/**
 * @author Florian
 */
@RestController
public class AppUserControler {

    /**.
     * LOG
     */
    protected static final Logger LOG = LogManager.getLogger();
    /**.
     * declaration de appUserRepostory
     */
    @Autowired
    private AppUserRepository appUserRepostory;

    /**
     * @param userKey nom du compte
     * @throws Exception fonction
     */
    @RequestMapping("/user/add/{userKey}")
    public void addAppUser(@PathVariable("userKey") final String userKey) throws Exception {
        AppUser utilisateur = new AppUser();
        utilisateur.setName(userKey);
        //TODO brf by Djer |log4J| Contextualise tes log et donne un sens aux messages ("Creating user : " + utilisateur.getName())
        LOG.debug(utilisateur.getName());
        appUserRepostory.save(utilisateur);
        //TODO brf by Djer |Rest API| Renvoyer un "OK" ?
    }
}
