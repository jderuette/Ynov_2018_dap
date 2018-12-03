/**
 * 
 */
package fr.ynov.dap.GmailPOO.web;

import javax.websocket.server.PathParam;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import fr.ynov.dap.GmailPOO.data.AppUser;
import fr.ynov.dap.GmailPOO.metier.Data;

/**
 * @author acer
 *
 */
@Controller
public class AjouterUtilisateur {
	private static final Logger logger = LogManager.getLogger();
	@Autowired
	Data maDataBase;

	@RequestMapping("/user/add/{userKey}")
	public String AddUser(Model model,  @PathVariable("userKey") String userKey) {
		try {
			model.addAttribute("add", "Add User");
			AppUser user = maDataBase.consulterUser(userKey);

			if (user == null) {
				maDataBase.addUser(userKey);
				model.addAttribute("onSuccess", userKey + "  ajouté avec succes ");
				logger.info(userKey + "  ajouté avec succes ");
			} else {
				model.addAttribute("NotAdd", userKey + " existe Déjà !!!!! choisissez un autre userKey ");
				logger.info(userKey + " existe Déjà !!!!! choisissez un autre userKey ");	
			}
		} catch (Exception e) {
			logger.error(e.getMessage());
			model.addAttribute("error", e.getMessage());
		}

		return "addUser";
	}
}
