package fr.ynov.dap.web;

import java.io.IOException;
import java.security.GeneralSecurityException;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import fr.ynov.dap.models.AccountData;
import fr.ynov.dap.services.AdminService;

/**
 * Admin page.
 */
@Controller
public class AdminController {
    /**
     * Instantiate the Admin service using injection of dependency.
     */
    @Autowired
    private AdminService adminService;

    @RequestMapping("/admin")
    public String microsoftIndex(final ModelMap model, final HttpServletRequest request) throws GeneralSecurityException, IOException {

        model.addAttribute("fragment", "fragments/adminFragment");

        List<AccountData> accounts = adminService.getAccountsInformations();
        model.addAttribute("account", accounts);
        return "base";
    }

}
