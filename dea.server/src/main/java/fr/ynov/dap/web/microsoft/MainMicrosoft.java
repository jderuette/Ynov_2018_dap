
package fr.ynov.dap.web.microsoft;


import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import javax.servlet.http.HttpServletRequest;
import org.springframework.ui.Model;


/**
 * Controlleur de microsoft
 * 
 * @author antod
 *
 */
@Controller
public class MainMicrosoft
{

  /**
   * Page de microsoft
   * 
   * @param model
   * @return
   */
  @RequestMapping("/index")
  public String index(Model model, HttpServletRequest request)
  {
    return "index";
  }
}
