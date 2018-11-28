package fr.ynov.dap.dap.web;

import java.io.IOException;
import java.security.GeneralSecurityException;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

import fr.ynov.dap.dap.service.GoogleService;

/**
 * @author Florian
 */
@Controller
public class AjoutUtilSucces extends GoogleService {

    /**
     * @throws Exception constructeur
     * @throws IOException constructeur
     */
    public AjoutUtilSucces() throws Exception, IOException {

    }

    /**
     * @param model renvois les valeurs a la JSP
     * @return le nom de la JSP
     * @throws IOException fonction
     * @throws GeneralSecurityException fonction
     */
    @RequestMapping("/ajoututilsucces")
    public String welcome(final ModelMap model) throws IOException, GeneralSecurityException {
        return "ajoututilsucces";
    }
}
