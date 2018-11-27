package fr.ynov.dap.web;

import java.util.List;
import javax.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

import fr.ynov.dap.exceptions.ServiceException;
import fr.ynov.dap.models.AccountData;
import fr.ynov.dap.services.AdminService;
import fr.ynov.dap.web.api.DapController;

/**
 * Admin Controller.
 */
@Controller
public class AdminController extends DapController {
    /**
     * Instantiate the Admin service using injection of dependency.
     */
    @Autowired
    private AdminService adminService;

    /**
     * Display Account infos.
     * @param model AccountData model.
     * @param request request
     * @return the name of the page that will be displayed.
     */
    @RequestMapping("/admin")
    public String microsoftIndex(final ModelMap model, final HttpServletRequest request) {

        model.addAttribute("fragment", "fragments/adminFragment");

        List<AccountData> accounts;
        try {
            accounts = adminService.getAccountsInformations();
            model.addAttribute("account", accounts);
        } catch (ServiceException e) {
            model.addAttribute("error", e);
        }
        return "base";
    }

}
