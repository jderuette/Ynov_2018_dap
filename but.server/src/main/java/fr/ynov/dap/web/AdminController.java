package fr.ynov.dap.web;

import java.io.IOException;
import java.security.GeneralSecurityException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

import fr.ynov.dap.google.AdminService;

/**
 * Controller to manage google service via Admin page.
 * @author thibault
 *
 */
@Controller
public class AdminController extends HandlerErrorController {

    /**
     * Admin google service.
     */
    @Autowired
    private AdminService adminService;

    /**
     * Route admin page.
     * @param model Model data for View
     * @return template name
     */
    @RequestMapping("/admin")
    public String welcome(final ModelMap model) {
        try {
            model.addAttribute("credentials", this.adminService.getCredentialDataStore());
        } catch (IOException | GeneralSecurityException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return "admin";
    }
}
