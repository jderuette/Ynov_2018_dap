package fr.ynov.dap.web.google.controllers;

import fr.ynov.dap.data.google.AppUser;
import fr.ynov.dap.repositories.AppUserRepository;
import fr.ynov.dap.repositories.GoogleAccountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
//TODO mbf by Djer |Rest API| Il serait mieu qu ce soit un RestControlelr (plus facile pour les appels/retour vai une API)
public class AppUserController {

    @Autowired
    private AppUserRepository userRepository;

    @Autowired
    private GoogleAccountRepository gAccountRepository;

    @RequestMapping("/user/add")
    public final String createNewUser(@RequestParam("userKey") final String userKey, ModelMap model) {
        AppUser newUser = new AppUser();
        newUser.setName(userKey);
        userRepository.save(newUser);

        model.addAttribute("users",userRepository.findAll());
        //TODO mbf by Djer |Rest API| Dans le cadre d'une API Rest se sont les utilisateurs "finaux" qui vont s'ajouter eux-même, il ne faut PAS leur exposer la liste de tous les comptes !
        model.addAttribute("accounts",gAccountRepository.findAll());

        return "createNewUser";
    }
}
