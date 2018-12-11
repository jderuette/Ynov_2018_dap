package fr.ynov.dap.controller;

import java.io.IOException;
import java.security.GeneralSecurityException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import fr.ynov.dap.*;

@RestController
public class CalendarController {
	@Autowired
	CalendarService calendarService;

	@RequestMapping("calendar")
	public String prochainEvents(@RequestParam("userKey") String userKey) throws IOException, GeneralSecurityException {
		return calendarService.getNextEvents(userKey);

	}

}
