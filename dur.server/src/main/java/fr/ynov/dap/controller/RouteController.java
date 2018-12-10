package fr.ynov.dap.controller;

import java.io.IOException;
import java.security.GeneralSecurityException;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class RouteController {

    /**
     * Hello.
     *
     * @return the string
     * @throws IOException Signals that an I/O exception has occurred.
     * @throws GeneralSecurityException the general security exception
     */
    @RequestMapping("/")
    public String HelloMrDeruette() throws IOException, GeneralSecurityException {
        //TODO dur by Djer |Thymleaf| Tu aurais pu me faire un petit template pour faire plus beau (et internationnaliser !)
        return "Bienvenue sur cette belle application presque fonctionnelle";
    }
}
