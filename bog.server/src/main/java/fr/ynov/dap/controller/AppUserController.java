package fr.ynov.dap.controller;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import fr.ynov.dap.data.AppUser;
import fr.ynov.dap.repository.AppUserRepository;

/**
 * @author Mon_PC
 */
@RestController
public class AppUserController {

    /**.
     * repositoryUser is managed by Spring on the loadConfig()
     */
    //TODO bog by Djer |SOA| Pas top d'acceder directement au repository, passer par un "UserService" aurait été mieu
    @Autowired
    private AppUserRepository repositoryUser;

    /**
     * @throws Exception si un problème est survenu lors de la création de l'instance AppUserController
     * @throws IOException si un problème est survenu lors de la création de l'instance AppUserController
     */
    public AppUserController() throws Exception, IOException {
    }

    /**
     * @param userKey : Correspond au nom d'utilisateur qu'il faut ajouter
     */
    @RequestMapping("/user/add/{userKey}")
    public void addNewUser(@PathVariable("userKey") final String userKey) {
        AppUser user = new AppUser();
        user.setName(userKey);
        repositoryUser.save(user);
    }
}
