package fr.ynov.dap.web.account;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import fr.ynov.dap.data.AppUser;
import fr.ynov.dap.repository.AppUserRepository;
import fr.ynov.dap.repository.GoogleAccountRepository;
import fr.ynov.dap.repository.MicrosoftAccountRepository;

@Controller
public class AdminController {

  //TODO gut by Djer |POO| SI tu ne précise pas, cette attribut aurat la même porté que la classe (donc public).
	@Autowired
	AppUserRepository 
	appUserRepository;
	
	//TODO gut by Djer |POO| SI tu ne précise pas, cette attribut aurat la même porté que la classe (donc public). En plus tu ne l'utilise pas mais ton IDE ne peut pas te le signaler car l'attribut est "public"
	@Autowired 
	MicrosoftAccountRepository 
	microsoftAccountRepository;
	
	//TODO gut by Djer |POO| SI tu ne précise pas, cette attribut aurat la même porté que la classe (donc public). En plus tu ne l'utilise pas mais ton IDE ne peut pas te le signaler car l'attribut est "public"
	@Autowired
	GoogleAccountRepository
	googleAccountRepository;
	
	//FIXME gut by Djer |Spring| le "PathVariable" n'est PAS dans ton template. Ajoute dans le template d'URI ou passe en QueryParam
	@RequestMapping("/admin")
	public String addUser(ModelMap model, @PathVariable final String userKey) {
		Iterable<AppUser> userList = appUserRepository.findAll();
		
		model.addAttribute(userList);
		
		return "admin";
	}
	
}
