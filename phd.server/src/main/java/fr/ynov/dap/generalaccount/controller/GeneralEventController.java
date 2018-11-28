package fr.ynov.dap.generalaccount.controller;

import java.io.IOException;
import java.security.GeneralSecurityException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import fr.ynov.dap.generalaccount.model.GeneralEvent;
import fr.ynov.dap.generalaccount.service.GeneralEventService;

/**
 *
 * @author Dom
 *
 */
@Controller
public class GeneralEventController {

    /**
     *
     */
    @Autowired
    private GeneralEventService generalEventService;

    /**
    *
    * @param model .
    * @return .
    * @throws GeneralSecurityException .
    * @throws IOException .
    * @param userId .
    */
    @RequestMapping("/generalEvent")
    public String generalMail(final Model model, @RequestParam("userId") final String userId)
            throws IOException, GeneralSecurityException {
        GeneralEvent generalEvent = generalEventService.getGeneralEvent(userId);
        model.addAttribute("generalEvent", generalEvent);
        return "generalEvent";
    }

}
