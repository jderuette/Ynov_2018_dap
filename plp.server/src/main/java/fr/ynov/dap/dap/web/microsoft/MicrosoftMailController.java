package fr.ynov.dap.dap.web.microsoft;

import fr.ynov.dap.dap.microsoft.MicrosoftMailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;

@Controller
@RequestMapping("/microsoft")
public class MicrosoftMailController {
    @Autowired
    MicrosoftMailService microsoftMailService;

    @RequestMapping("/mail")
//    public Map<String, Integer> mail(Model model, HttpServletRequest request, RedirectAttributes redirectAttributes) {
    public String mail(Model model, RedirectAttributes redirectAttributes, @RequestParam("userKey") final String userKey) {
        model.addAttribute("accounts", microsoftMailService.mail(userKey, redirectAttributes));
        return "mail_micro";
    }
}
