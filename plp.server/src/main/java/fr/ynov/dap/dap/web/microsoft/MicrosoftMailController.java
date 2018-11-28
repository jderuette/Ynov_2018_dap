package fr.ynov.dap.dap.web.microsoft;

import fr.ynov.dap.dap.microsoft.OutlookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/microsoft")
public class MicrosoftMailController {
    @Autowired
    OutlookService outlookService;

    @RequestMapping("/mail")
//    public Map<String, Integer> mail(Model model, HttpServletRequest request, RedirectAttributes redirectAttributes) {
    public String mail(Model model, RedirectAttributes redirectAttributes, @RequestParam("userKey") final String userKey) {
        model.addAttribute("accounts", outlookService.mail(userKey, redirectAttributes));
        return "mail_micro";
    }
}
