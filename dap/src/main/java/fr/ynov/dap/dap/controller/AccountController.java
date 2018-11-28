package fr.ynov.dap.dap.controller;

import java.io.IOException;
import java.security.GeneralSecurityException;
import java.util.UUID;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import fr.ynov.dap.dap.enums.AccountTypeEnum;
import fr.ynov.dap.dap.exception.SecretFileAccesException;
import fr.ynov.dap.dap.google.service.GoogleAccountService;
import fr.ynov.dap.dap.microsoft.services.MicrosoftAccountService;
import fr.ynov.dap.dap.model.IdToken;

/**
 * used to connect or retrieve token for an userID.
 *
 * @author David_tepoche
 *
 */
@RestController
public class AccountController extends BaseController {

    /**
     * link googleAccount service.
     */
    @Autowired
    private GoogleAccountService googleAccountService;
    /**
     * link microsoftAccount service.
     */
    @Autowired
    private MicrosoftAccountService microsoftAccountService;

    /**
     * Add an account (user will be prompt to connect and accept required access).
     *
     * @param userKey     user in bdd
     * @param accountName alias for googleAccount
     * @param accountType the type of the account (like google or microsoft)
     * @param request     the HTTP request
     * @param session     the HTTP session
     * @param response    the response of the call
     * @throws GeneralSecurityException throw if the addCount fail
     * @throws IOException              throw if the call from accountService fail
     * @throws SecretFileAccesException throw if you can't get the info from the
     *                                  properties
     */
    @GetMapping("/account/add/{userKey}/{accountName}/{accountType}")
    public void addAccount(@PathVariable("userKey") final String userKey,
            @PathVariable("accountName") final String accountName,
            @PathVariable("accountType") final String accountType, final HttpServletRequest request,
            final HttpSession session, final HttpServletResponse response)
            throws GeneralSecurityException, IOException, SecretFileAccesException {

        if (accountType.equalsIgnoreCase(AccountTypeEnum.GOOGLE.getName())) {
            googleAccountService.addAccount(accountName, userKey, request, session, response);

        } else if (accountType.equalsIgnoreCase(AccountTypeEnum.MICROSOFT.getName())) {
            UUID state = UUID.randomUUID();
            UUID nonce = UUID.randomUUID();
            session.setAttribute("expected_state", state);
            session.setAttribute("expected_nonce", nonce);

            microsoftAccountService.addAccount(accountName, userKey, request, session, response, state, nonce);
        }
    }

    /**
     * retrieve the token send by google and store it.
     *
     * @param code     code return from google service
     * @param request  the resquet given
     * @param session  session open
     * @param response http response of the call
     * @throws ServletException throw if the call from googleAccount fail
     * @throws IOException      if the redirection fail
     */
    @RequestMapping("/oAuth2Callback")
    public void oAuthCallback(@RequestParam final String code, final HttpServletRequest request,
            final HttpSession session, final HttpServletResponse response) throws ServletException, IOException {
        googleAccountService.oAuthCallback(code, request, session, response);
    }

    @Override
    public final String getClassName() {
        return AccountController.class.getName();
    }

    /**
     * recupere les infoamtion de connections depuis la page d'authente microsoft.
     *
     * @param code     the token not decoded
     * @param idToken  object that carry the tenant id
     * @param state    uuid that must match with
     * @param request  http call
     * @param response http response of the call
     * @throws SecretFileAccesException throw if you can't get the info from the
     *                                  properties
     * @throws IOException              throw if the redirect fail
     */
    @PostMapping(value = "/authorize")
    public void authorize(@RequestParam("code") final String code, @RequestParam("id_token") final String idToken,
            @RequestParam("state") final UUID state, final HttpServletRequest request,
            final HttpServletResponse response) throws SecretFileAccesException, IOException {

        // Get the expected state value from the session
        HttpSession session = request.getSession();
        UUID expectedState = (UUID) session.getAttribute("expected_state");
        UUID expectedNonce = (UUID) session.getAttribute("expected_nonce");
        String accountName = (String) session.getAttribute("accountName");
        String userKey = (String) session.getAttribute("userKey");

        // Make sure that the state query parameter returned matches
        // the expected state
        if (state.equals(expectedState)) {
            IdToken idTokenObj = IdToken.parseEncodedToken(idToken, expectedNonce.toString());
            if (idTokenObj != null) {

                fr.ynov.dap.dap.model.TokenResponse tokenResponse = microsoftAccountService.getTokenFromAuthCode(code,
                        idTokenObj.getTenantId());
                microsoftAccountService.saveNewAccountNameInUserKey(tokenResponse, userKey, accountName,
                        idTokenObj.getTenantId());
            } else {
                session.setAttribute("error", "ID token failed validation.");
            }
        } else {
            session.setAttribute("error", "Unexpected state returned from authority.");
        }
        response.sendRedirect("admin/" + userKey);
    }
}
