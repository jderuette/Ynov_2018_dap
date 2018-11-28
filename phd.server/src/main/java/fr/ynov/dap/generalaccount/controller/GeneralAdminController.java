package fr.ynov.dap.generalaccount.controller;

import java.io.IOException;
import java.security.GeneralSecurityException;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

import com.google.api.client.auth.oauth2.StoredCredential;

import fr.ynov.dap.generalaccount.service.GeneralAdminService;
import fr.ynov.dap.microsoft.data.MicrosoftAccountData;

/**
 *
 * @author Dom
 *
 */
@Controller
public class GeneralAdminController {

    /**
     *
     */
    @Autowired
    private GeneralAdminService generalAdminService;

    /**.
     * @param model .
     * @return .
     * @throws IOException .
     * @throws GeneralSecurityException .
     */
    @RequestMapping("/admin")
    public String admin(final ModelMap model) throws IOException, GeneralSecurityException {
        Map<String, StoredCredential> map = generalAdminService.getCredentialsAdminDetails();
        Iterable<MicrosoftAccountData> iterable = generalAdminService.getDataStoreMicrosoft();
        model.addAttribute("map", map);
        model.addAttribute("iterable", iterable);
        model.addAttribute("googleType", "Google");
        model.addAttribute("microsoftType", "Microsoft");
        model.addAttribute("tenantIdEmpty", " ");
        return "admin";
    }

}
