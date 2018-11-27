package fr.ynov.dap.dap.web.microsoft;

import fr.ynov.dap.dap.microsoft.*;
import org.mortbay.log.Log;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import sun.security.ssl.Debug;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/microsoft")
public class MicrosoftMailController {
    @Autowired
    MicrosoftMailService microsoftMailService;

    @RequestMapping("/mail")
//    public Map<String, Integer> mail(Model model, HttpServletRequest request, RedirectAttributes redirectAttributes) {
    public String mail(Model model, HttpServletRequest request, RedirectAttributes redirectAttributes) {
        return microsoftMailService.mail(model, request, redirectAttributes);
    }
}
