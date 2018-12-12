package fr.ynov.dap.dap.microsoft.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @author Florian
 */
@Controller
public class IndexController {

    /**
     * @return la pas index
     */
    @RequestMapping("/")
    public String index() {

        return "index";

    }

}
