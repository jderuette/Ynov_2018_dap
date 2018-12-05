package fr.ynov.dap.web;

import java.io.IOException;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

import fr.ynov.dap.service.GoogleService;

/**
 * @author Mon_PC
 */
@Controller
public class AddUserSuccess extends GoogleService {
    /**
     * @throws Exception si un problème est survenu lors de la création de l'instance Welcome
     * @throws IOException si un problème est survenu lors de la création de l'instance Welcome
     */
    //TODO bog by Djer |POO| Ce code peut vraiment lever ces Exceptions ?
    public AddUserSuccess() throws Exception, IOException {
    }

    /**
     * @param model type Modelmap
     * @return welcome view
     * @throws Exception si un problème est survenu lors de l'appel à cette fonction
     */
    @RequestMapping("/user/success")
    public String successAddUser(final ModelMap model) throws Exception {
        return "addUserSuccess";
    }
}
