package fr.ynov.dap.dap.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import fr.ynov.dap.dap.models.Message;
import fr.ynov.dap.dap.models.PagedResult;
import fr.ynov.dap.dap.services.microsoft.OutlookMailService;

@Controller
public class OutlookWebAccessController {
	@Autowired 
	private OutlookMailService outlookMailService;
	
	/**
	 * Display the 10 last mail on all outlook account
	 * @param userid AppAccount
	 * @return
	 */
	@RequestMapping("/outlookMails")
	public ModelAndView GetOutlookMails(@RequestParam("userKey") String userid) {
		ModelAndView mv = new ModelAndView("mail");
		List<PagedResult<Message>> messages = outlookMailService.getMails(userid);
		
		mv.addObject("messages", messages);
		return mv;
	}
}
