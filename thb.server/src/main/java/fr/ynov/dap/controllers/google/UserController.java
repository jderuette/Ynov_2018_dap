package fr.ynov.dap.controllers.google;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import fr.ynov.dap.data.AppUser;
import fr.ynov.dap.data.interfaces.AppUserRepository;
import fr.ynov.dap.utils.ExtendsUtils;

/**
 * The Class UserController.
 */
@RestController
@RequestMapping("/user")
public class UserController extends ExtendsUtils {

	/** The app user repo. */
  //TODO thb by Djer |POO| Précise le modifier sinon le même que celui de la classe
	@Autowired
	AppUserRepository appUserRepo;

	/**
	 * Index.
	 *
	 * @param userKey the user key
	 * @return the string
	 */
	//TODO thb by Djer |Spring| Par defaut les méthodes mappées dans un **Rest**Controller renvoie le body. C'est redondant de le préciser
	@RequestMapping("/add/{userKey}")
	public @ResponseBody String index(@PathVariable String userKey) {

		AppUser appU = new AppUser();
		appU.setUserKey(userKey);

		appUserRepo.save(appU);

		//TODO thb by Djer |JPA| La méthode "save" te renvoie l'entité "appuser" qui à été sauvegardée, tu peux simplement récupérer cette valeur plutot que de re faire une requete
		return appUserRepo.findByUserKey(userKey).getUserKey();
	}
}
