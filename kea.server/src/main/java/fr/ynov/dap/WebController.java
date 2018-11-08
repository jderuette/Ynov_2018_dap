package fr.ynov.dap;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


/**
 * The web Controller is the main controller of the application.
 * @author Antoine
 *
 */
@RestController
public class WebController {

  @RequestMapping("/")
  public String index() {
    return "Greetings from Spring Boot!";
  }
}