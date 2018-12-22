package fr.ynov.dap.controllers;

import java.util.UUID;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import fr.ynov.dap.google.data.AppUser;
import fr.ynov.dap.google.data.AppUserRepostory;
import fr.ynov.dap.microsoft.AuthHelper;
import fr.ynov.dap.microsoft.data.MicrosoftAccount;
import fr.ynov.dap.microsoft.data.TokenResponse;
import fr.ynov.dap.microsoft.models.IdToken;


/**
 * The Class AuthorizeController.
 */
@Controller
//TODO brc by Djer |POO| Pour être cohérent avec "Google" ces méthodes devraient être dans le "AccountController"
public class AuthorizeController {
		
	/** The app user repository. */
	@Autowired
	AppUserRepostory appUserRepository;

	/**
	 * Authorize.
	 *
	 * @param model the model
	 * @param code the code
	 * @param idToken the id token
	 * @param state the state
	 * @param request the request
	 * @return the string
	 * @throws Exception the exception
	 */
	@RequestMapping(value = "/authorize", method = RequestMethod.POST)
	public String authorize(Model model,@RequestParam("code") String code, @RequestParam("id_token") String idToken,
			@RequestParam("state") UUID state, HttpServletRequest request) throws Exception{
		// Get the expected state value from the session
		HttpSession session = request.getSession();

        UUID expectedState = (UUID) session.getAttribute("state");
        UUID expectedNonce = (UUID) session.getAttribute("nonce");
        String userKey = (String) session.getAttribute("userKey");
        String accountName = (String) session.getAttribute("msAccount");

        if (expectedNonce == null || expectedState == null || userKey == null || accountName == null) {
            throw new NullPointerException("Missing session parametter");
        }
		// Make sure that the state query parameter returned matches
		// the expected state
		if (state.equals(expectedState)) {
			IdToken idTokenObj = IdToken.parseEncodedToken(idToken, expectedNonce.toString());
			if (idTokenObj != null) {
				TokenResponse tokenResponse = AuthHelper.getTokenFromAuthCode(code, idTokenObj.getTenantId());
				
                AppUser currentUser = appUserRepository.findByUserkey(userKey);
                if (currentUser == null) {
                    throw new NullPointerException("No user for this userKey");
                }
				
                MicrosoftAccount msAccount = new MicrosoftAccount();
                msAccount.setToken(tokenResponse);
                msAccount.setToken(tokenResponse);
                msAccount.setTenantId(idTokenObj.getTenantId());
                msAccount.setEmail(idTokenObj.getEmail());
                msAccount.setName(accountName);

                currentUser.addMsAccount(msAccount);

                appUserRepository.save(currentUser);
				
			} else {
				throw new NullPointerException("id token null");
			}
		} else {
			throw new Exception("Unexpected state returned from authority.");
		}
		
		model.addAttribute("userKey",userKey);
		return "index";
	}

	/**
	 * Logout.
	 *
	 * @param request the request
	 * @return the string
	 */
	@RequestMapping("/logout")
	public String logout(HttpServletRequest request) {
		HttpSession session = request.getSession();
		session.invalidate();
		return "redirect:/index.html";
	}
}
