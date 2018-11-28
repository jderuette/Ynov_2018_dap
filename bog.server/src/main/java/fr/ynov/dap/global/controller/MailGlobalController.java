package fr.ynov.dap.global.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import fr.ynov.dap.global.service.MailGlobalService;

/**
 * @author Mon_PC
 */
@Controller
public class MailGlobalController {

    /**.
     * globalMailService is managed by Spring on the loadConfig()
     */
    @Autowired
    private MailGlobalService globalMailService;

    /**
     * @param userKey user passé en param
     * @param model modal
     * @return vue mailGlobal
     * @throws Exception si problème lors de l'éxécution
     */
    @RequestMapping("/mail/global/{userKey}")
    public String mailGlobal(@PathVariable("userKey") final String userKey, final Model model) throws Exception {

        int globalMailsUnread = 0;
        globalMailsUnread = globalMailService.totalMailGoogleMicrosoft(userKey);

        model.addAttribute("globalMailsUnread", globalMailsUnread);

        return "mailGlobal";
    }

}
