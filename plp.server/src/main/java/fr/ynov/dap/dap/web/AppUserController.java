package fr.ynov.dap.dap.web;

import fr.ynov.dap.dap.data.AppUser;
import fr.ynov.dap.dap.repositories.AppUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class AppUserController {
    @Autowired
    AppUserRepository repository;

    @RequestMapping("/user/add/{userKey}")
    public String addUser(@PathVariable final String userKey, ModelMap model) {
        AppUser newUser = new AppUser();
        newUser.setName(userKey);
        repository.save(newUser);

        model.addAttribute("users", repository.findAll());

        return "users";
    }
}
