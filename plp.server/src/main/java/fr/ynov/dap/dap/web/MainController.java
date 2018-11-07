package fr.ynov.dap.dap.web;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class MainController {
    @RequestMapping("/")
    public final String index() {
        return "Welcome to Ynov Dap";
    }
}
