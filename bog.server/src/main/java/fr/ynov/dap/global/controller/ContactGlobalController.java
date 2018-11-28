package fr.ynov.dap.global.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import fr.ynov.dap.global.service.ContactGlobalService;

/**
 * @author Mon_PC
 */
@Controller
public class ContactGlobalController {

    /**.
     * globalContactService is managed by Spring on the loadConfig()
     */
    @Autowired
    private ContactGlobalService globalcontactService;

    /**
     * @param userKey user passé en param
     * @param model modal
     * @return vue contactGlobal
     * @throws Exception si problème lors de l'éxécution
     */
    @RequestMapping("/contact/global/{userKey}")
    public String contactGlobal(@PathVariable("userKey") final String userKey, final Model model) throws Exception {

        int globalContacts = 0;
        globalContacts = globalcontactService.totalMailGoogleMicrosoft(userKey);

        model.addAttribute("globalContacts", globalContacts);

        return "contactGlobal";
    }
}
