package fr.ynov.dap.web;

import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

import com.google.api.client.auth.oauth2.StoredCredential;

import fr.ynov.dap.services.google.GoogleAuthorizationFlowService;
import fr.ynov.dap.web.api.DapController;

/**
 * @author adrij
 *
 */
@Controller
public class GoogleAutorisationFlowController extends DapController {

    /**
     * Instantiate GoogleAuthorizationFlow using injection of dependency.
     */
    @Autowired
    private GoogleAuthorizationFlowService service;

    /**
     * default constructor.
     */
    public GoogleAutorisationFlowController() {
    }

    /**
     * Data store page that display the content of the credentialDataStore file.
     * @param model model to send to the view
     * @return the name of the page template
     * @throws Exception exception
     */
    @RequestMapping("/datastore")
    public String datastore(final ModelMap model) throws Exception {
        Map<String, StoredCredential> map = service.getStoreCredentialMap();
        model.addAttribute("map", map);
        return "datastore";
    }

}
