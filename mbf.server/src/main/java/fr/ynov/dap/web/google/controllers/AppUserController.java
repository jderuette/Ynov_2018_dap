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
public class AppUserController {

    @Autowired
    AppUserRepository userRepository;

    @Autowired
    GoogleAccountRepository gAccountRepository;
    @RequestMapping("/user/add")
    public String createNewUser(@RequestParam("userKey") final String userKey, ModelMap model) {
        AppUser newUser = new AppUser();
        newUser.setName(userKey);
        userRepository.save(newUser);

        model.addAttribute("users",userRepository.findAll());
        model.addAttribute("accounts",gAccountRepository.findAll());

        return "createNewUser";
    }
}
