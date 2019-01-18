package fr.ynov.dap.web;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import fr.ynov.dap.data.AppUser;
import fr.ynov.dap.metier.Data;

/**
 * @author acer
 */
@Controller
public class AjouterUtilisateur {
    private static final Logger logger = LogManager.getLogger();
    @Autowired
    Data maDataBase;

    @RequestMapping("/user/add/{userKey}")
    public String AddUser(final Model model, @PathVariable("userKey") final String userKey) {
        AppUser user = null;
        try {
            model.addAttribute("add", "Add User");
            user = maDataBase.consulterUser(userKey);

        } catch (Exception e) {
            //TODO bes by Djer |Log4J| Créer ton prop^re messagen, et ajoute "e" en deuxième paramètre et laisse Log4J gèrer la "cause"
            logger.error(e.getMessage());

        }
        if (user == null) {
            maDataBase.addUser(userKey);
            model.addAttribute("onSuccess", userKey + "  ajouté avec succes ");
            logger.info(userKey + "  ajouté avec succes ");
        } else {
            model.addAttribute("NotAdd", userKey + " existe Déjà !!!!! choisissez un autre userKey ");
            //TODO bes by Djer |Log4J| Devrait un warning/error. Seul les "dev" voient les logs, ils ne PEUVENT pas "choisir un autre userKey"
            logger.info(userKey + " existe Déjà !!!!! choisissez un autre userKey ");
        }

        return "Info";
    }
}
