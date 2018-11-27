package fr.ynov.dap.controllers.google;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import fr.ynov.dap.data.AppUser;
import fr.ynov.dap.data.interfaces.AppUserRepository;
import fr.ynov.dap.utils.ExtendsUtils;

@RestController
@RequestMapping("/user")
public class UserController extends ExtendsUtils {
	
	@Autowired
	AppUserRepository appUserRepo;
		
	@RequestMapping("/add/{userKey}")
    public @ResponseBody String index(@PathVariable String userKey)  {
		
		AppUser appU = new AppUser();
		appU.setUserKey(userKey);
		
		appUserRepo.save(appU);
		
		return appUserRepo.findByUserKey(userKey).getUserKey();
    }
}
