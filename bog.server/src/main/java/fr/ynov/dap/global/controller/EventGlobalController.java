package fr.ynov.dap.global.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import fr.ynov.dap.global.service.EventGlobalService;
import fr.ynov.dap.model.EventModel;

/**
 * @author Mon_PC
 */
@Controller
public class EventGlobalController {

    /**.
     * globalEventService is managed by Spring on the loadConfig()
     */
    @Autowired
    private EventGlobalService globalEventService;

    /**
     * @param userKey user passé en param
     * @param model modal
     * @return vue contactGlobal
     * @throws Exception si problème lors de l'éxécution
     */
    @RequestMapping("/event/global/{userKey}")
    public String eventGlobal(@PathVariable("userKey") final String userKey, final Model model) throws Exception {

        EventModel nextEvent = globalEventService.getNextEventMicrosoftOrGoogle(userKey);
        model.addAttribute("event", nextEvent);

        return "eventGlobal";
    }
}
