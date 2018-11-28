package com.ynov.dap.controller.microsoft;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.ynov.dap.controller.BaseController;
import com.ynov.dap.service.microsoft.MicrosoftCalendarService;

/**
 * The Class MicrosoftCalendarController.
 */
@Controller
@RequestMapping("calendar")
public class MicrosoftCalendarController extends BaseController {

    /** The microsoft calendar service. */
    @Autowired
    private MicrosoftCalendarService microsoftCalendarService;
	
	/**
	 * Gets the template microsoft next event.
	 *
	 * @param appUser the app user
	 * @param model the model
	 * @param redirectAttributes the redirect attributes
	 * @return the template microsoft next event
	 * @throws Exception the exception
	 */
	@GetMapping("/microsoft/{appUser}/view")
	public String getTemplateMicrosoftNextEvent(@PathVariable final String appUser, Model model, RedirectAttributes redirectAttributes) throws Exception {
		model.addAttribute("accounts", microsoftCalendarService.getEvents(appUser));

		return "microsoft/events";
	}
	
	/* (non-Javadoc)
	 * @see com.ynov.dap.controller.BaseController#getClassName()
	 */
	@Override
	public String getClassName() {
		return MicrosoftCalendarController.class.getName();
	}
}