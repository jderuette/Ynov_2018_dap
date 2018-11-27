package fr.ynov.dap.dap.web.microsoft;

import fr.ynov.dap.dap.microsoft.MicrosoftAccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

@RestController
@RequestMapping("/microsoft")
public class MicrosoftAccountController {
    @Autowired
    MicrosoftAccountService microsoftAccountService;

    @RequestMapping("/index")
    public Map<String, String> index(Model model, HttpServletRequest request) {
        return microsoftAccountService.index(model, request);
    }
}
