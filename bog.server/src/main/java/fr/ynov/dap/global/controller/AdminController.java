package fr.ynov.dap.global.controller;

import java.io.IOException;
import java.security.GeneralSecurityException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import fr.ynov.dap.global.service.AdminService;
import fr.ynov.dap.model.CredentialModel;

/**
 * @author Mon_PC
 */
@Controller
public class AdminController {

    /**.
     * adminService is managed by Spring on the loadConfig()
     */
    @Autowired
    private AdminService adminService;

    /**
     * @param model modelMap
     * @return credentials
     * @throws GeneralSecurityException si exception levée
     * @throws IOException si exception levée
     */
    @RequestMapping("/admin")
    public String credentialGoogleAndMicrosoft(final Model model) throws IOException, GeneralSecurityException {

        List<CredentialModel> credentialsGoogle = adminService.getDataStoreGoogle();
        List<CredentialModel> credentialsMicrosoft = adminService.getDataStoreMicrosoft();
        List<CredentialModel> listCredentials = new ArrayList<CredentialModel>();

        listCredentials.addAll(credentialsGoogle);
        listCredentials.addAll(credentialsMicrosoft);

        model.addAttribute("listCredentials", listCredentials);

        return "admin";
    }
}
