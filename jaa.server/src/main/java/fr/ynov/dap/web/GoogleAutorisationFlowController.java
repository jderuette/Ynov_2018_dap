package fr.ynov.dap.web;

import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

import com.google.api.client.auth.oauth2.StoredCredential;
import fr.ynov.dap.services.GoogleAuthorizationFlowService;
import fr.ynov.dap.web.api.GoogleController;

/**
 * @author adrij
 *
 */
@Controller
public class GoogleAutorisationFlowController extends GoogleController {

    @Autowired
    private GoogleAuthorizationFlowService service;
    
    /**
     * 
     */
    public GoogleAutorisationFlowController() {
    }

    @RequestMapping("/datastore")
    public String datastore(ModelMap model) throws Exception {
        Map<String, StoredCredential> map = service.getStoreCredentialMap();
        model.addAttribute("map", map);
        return "datastore";
    }

}
