package fr.ynov.dap.web;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;


/**
 * The web Controller is the main controller of the application.
 * @author Antoine
 *
 */
@Controller
public class WebController {

  /**
   * Renders the template "index.html".
   * @return a string that references a template
   */
  @RequestMapping("/")
  public String index() {
    return "welcome";
  }
}
