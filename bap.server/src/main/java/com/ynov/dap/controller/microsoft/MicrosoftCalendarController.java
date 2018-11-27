package com.ynov.dap.controller.microsoft;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.ynov.dap.service.microsoft.MicrosoftCalendarService;

@Controller
@RequestMapping("calendar")
public class MicrosoftCalendarController {

    @Autowired
    private MicrosoftCalendarService microsoftCalendarService;
	
	@RequestMapping("/microsoft/{appUser}/view")
	public String getTemplateMicrosoftNextEvent(@PathVariable final String appUser, Model model, RedirectAttributes redirectAttributes) {
		model.addAttribute("accounts", microsoftCalendarService.getEvents(appUser));

		return "microsoft/events";
	}
}