package fr.ynov.dap.web.microsoft;

import java.io.IOException;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.http.HttpStatus;
import org.apache.http.client.HttpResponseException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import fr.ynov.dap.data.AppUser;
import fr.ynov.dap.data.AppUserRepository;
import fr.ynov.dap.data.microsoft.MicrosoftAccount;
import fr.ynov.dap.data.microsoft.MicrosoftAccountRepository;
import fr.ynov.dap.microsoft.OutlookService;
import fr.ynov.dap.microsoft.OutlookServiceBuilder;
import fr.ynov.dap.microsoft.OutlookUser;
import fr.ynov.dap.microsoft.authentication.AuthHelper;
import fr.ynov.dap.microsoft.authentication.IdToken;
import fr.ynov.dap.microsoft.authentication.TokenResponse;
import fr.ynov.dap.web.HandlerErrorController;

/**
 * Controller to add microsoft account.
 * @author thibault
 *
 */
@Controller
public class AuthorizeController extends HandlerErrorController {

    /**
     * Repository of AppUser.
     */
    @Autowired
    private AppUserRepository repositoryUser;

    /**
     * Repository of MicrosoftAccount.
     */
    @Autowired
    private MicrosoftAccountRepository repositoryMicrosoftAccount;

    /**
     * CallBack url authorize of microsoft oauth2.
     * @param code the returned code
     * @param idToken microsoft id token
     * @param state state returned
     * @param request the request
     * @param redirectAttributes the redirect attributes for flash messages
     * @return template
     * @throws HttpResponseException if bad request.
     */
    @RequestMapping(value = "/authorize", method = RequestMethod.POST)
    public String authorize(@RequestParam("code") final String code, @RequestParam("id_token") final String idToken,
            @RequestParam("state") final UUID state, final HttpServletRequest request,
            final RedirectAttributes redirectAttributes) throws HttpResponseException {
        // Get the expected state value from the session
        HttpSession session = request.getSession();
        UUID expectedState = (UUID) session.getAttribute("expected_state");
        UUID expectedNonce = (UUID) session.getAttribute("expected_nonce");
        String userKey = (String) session.getAttribute("user_key");
        String accountName = (String) session.getAttribute("account_name");

        // Make sure that the state query parameter returned matches
        // the expected state
        if (state.equals(expectedState)) {
            IdToken idTokenObj = IdToken.parseEncodedToken(idToken, expectedNonce.toString());
            if (idTokenObj != null) {
                AppUser user = repositoryUser.findByUserKey(userKey);

                if (user == null) {
                    throw new HttpResponseException(HttpStatus.SC_BAD_REQUEST, "User '" + userKey + "' not found.");
                }

                TokenResponse tokenResponse = AuthHelper.getTokenFromAuthCode(code, idTokenObj.getTenantId());
                MicrosoftAccount mAccount = new MicrosoftAccount(accountName, user, tokenResponse,
                        idTokenObj.getTenantId());

                // Get user info
                OutlookService outlookService = OutlookServiceBuilder.getOutlookService(tokenResponse.getAccessToken(),
                        null);
                OutlookUser outlookUser;
                try {
                    outlookUser = outlookService.getCurrentUser().execute().body();
                    mAccount.setEmail(outlookUser.getMail());
                } catch (IOException e) {
                    redirectAttributes.addFlashAttribute("error", e.getMessage());
                }

                user.addMicrosoftAccount(mAccount);

                repositoryUser.save(user);
                redirectAttributes.addFlashAttribute("success", "Microsoft account added.");

            } else {
                redirectAttributes.addFlashAttribute("error", "ID token failed validation.");
            }

        } else {
            redirectAttributes.addFlashAttribute("error", "Unexpected state returned from authority.");
        }
        return "redirect:/";
    }

    /**
     * Add a Microsoft account (user will be prompt to connect and accept required
     * access).
     * @param accountName the user to store Data
     * @param userKey the owner (user key) of this microsoft account.
     * @param request the HTTP request
     * @param model the model to transfer data to view
     * @return the view to Display (on Error)
     * @throws IOException if file config not found or bad request
     */
    @RequestMapping("/add/account/microsoft/{accountName}")
    public String addAccount(@PathVariable final String accountName, @RequestParam final String userKey,
            final Model model, final HttpServletRequest request) throws IOException {
        AppUser user = repositoryUser.findByUserKey(userKey);

        if (user == null) {
            throw new HttpResponseException(HttpStatus.SC_BAD_REQUEST, "User '" + userKey + "' not found.");
        }

        if (repositoryMicrosoftAccount.existsByAccountName(accountName)) {
            throw new HttpResponseException(HttpStatus.SC_BAD_REQUEST,
                    "Microsoft account '" + accountName + "' already exist.");
        }

        UUID state = UUID.randomUUID();
        UUID nonce = UUID.randomUUID();

        HttpSession session = request.getSession();
        session.setAttribute("expected_state", state);
        session.setAttribute("expected_nonce", nonce);
        session.setAttribute("user_key", userKey);
        session.setAttribute("account_name", accountName);

        String loginUrl = AuthHelper.getLoginUrl(state, nonce);
        model.addAttribute("loginUrl", loginUrl);
        model.addAttribute("userKey", userKey);

        model.addAttribute("fragment", "microsoft/addaccount");

        return "base";
    }
}
