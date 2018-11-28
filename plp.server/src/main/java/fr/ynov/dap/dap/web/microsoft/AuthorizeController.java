package fr.ynov.dap.dap.web.microsoft;

import fr.ynov.dap.dap.microsoft.AuthorizeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;
import java.util.UUID;

@RestController
@RequestMapping("/microsoft")
public class AuthorizeController {
    /**
     * instantiate AuthorizeService
     */
    @Autowired
    AuthorizeService authorizeService;

    /**
     *
     * @param code
     * @param idToken
     * @param state
     * @param request
     * @return
     */
    @RequestMapping(value = "/authorize", method = RequestMethod.POST)
    public Map<String, String> authorize(
            @RequestParam("code") String code,
            @RequestParam("id_token") String idToken,
            @RequestParam("state") UUID state,
            HttpServletRequest request) {
        return authorizeService.authorize(code, idToken, state, request);
    }

    @RequestMapping("/logout")
    public String logout(HttpServletRequest request) {
        return authorizeService.logout(request);
    }
}
