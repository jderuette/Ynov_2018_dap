package fr.ynov.dap.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * The Class IndexController.
 */
@Controller
public class IndexController {

	/**
	 * Index.
	 *
	 * @return the string
	 */
	@RequestMapping("/index")
	public String index() {
	  return "index";
	}
}
