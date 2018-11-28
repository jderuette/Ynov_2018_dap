package fr.ynov.dap.dap.web.microsoft;

import fr.ynov.dap.dap.microsoft.MicrosoftAccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.security.GeneralSecurityException;
import java.util.Map;

@Controller
//@RequestMapping("/microsoft")
public class MicrosoftAccountController {
    @Autowired
    MicrosoftAccountService microsoftAccountService;

    @RequestMapping("/microsoft/index")
    public Map<String, String> index(Model model, HttpServletRequest request) {
        return microsoftAccountService.index(model, request);
    }

    /**
     * Add a microsoft account (user will be prompt to connect and accept required
     * access).
     *
     * @param request the HTTP request
     * @return the view to Display (on Error)
     */
    @RequestMapping("/add/account/microsoft/{accountName}")
    public final String addAccount(@PathVariable final String accountName,
                                   @RequestParam("userKey") final String userKey, final HttpServletRequest request) {
        return microsoftAccountService.addAccount(accountName, userKey,request);
    }
}
