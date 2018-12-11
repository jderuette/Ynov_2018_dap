package fr.ynov.dap.controller;

import java.io.IOException;
import java.security.GeneralSecurityException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import fr.ynov.dap.*;

@RestController
public class PeopleController {
	@Autowired
	PeopleService PeopleService;

	@RequestMapping("people/{gUser}")
	public String getPeople(@RequestParam("userKey") String userKey, @PathVariable("gUser") String gUser)
			throws IOException, GeneralSecurityException {

		return PeopleService.getPeople(userKey, gUser);

	}

}
