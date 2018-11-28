package com.ynov.dap.controller.microsoft;

import java.io.IOException;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.ynov.dap.controller.BaseController;
import com.ynov.dap.domain.AppUser;
import com.ynov.dap.domain.microsoft.MicrosoftAccount;
import com.ynov.dap.model.microsoft.IdToken;
import com.ynov.dap.model.microsoft.TokenResponse;
import com.ynov.dap.repository.AppUserRepository;
import com.ynov.dap.repository.microsoft.MicrosoftAccountRepository;
import com.ynov.dap.service.microsoft.AuthHelper;

/**
 * The Class AuthorizeController.
 */
@Controller
public class AuthorizeController extends BaseController {

	/** The app user repository. */
	@Autowired
	private AppUserRepository appUserRepository;

	/** The microsoft account repository. */
	@Autowired
	private MicrosoftAccountRepository microsoftAccountRepository;

	/**
	 * Authorize.
	 *
	 * @param code the code
	 * @param idToken the id token
	 * @param state the state
	 * @param request the request
	 * @return the string
	 * @throws JsonParseException the json parse exception
	 * @throws JsonMappingException the json mapping exception
	 * @throws IOException Signals that an I/O exception has occurred.
	 */
	@RequestMapping(value = "/authorize", method = RequestMethod.POST)
	public String authorize(@RequestParam("code") String code, @RequestParam("id_token") String idToken,
			@RequestParam("state") UUID state, HttpServletRequest request) throws JsonParseException, JsonMappingException, IOException {

		HttpSession session = request.getSession();
		UUID expectedState = (UUID) session.getAttribute("expected_state");
		UUID expectedNonce = (UUID) session.getAttribute("expected_nonce");

		String accountName = (String) session.getAttribute("accountName");
		String userKey = (String) session.getAttribute("userKey");

		if (state.equals(expectedState)) {
			IdToken idTokenObj = IdToken.parseEncodedToken(idToken, expectedNonce.toString());
			if (idTokenObj != null) {
				TokenResponse tokenResponse = AuthHelper.getTokenFromAuthCode(code, idTokenObj.getTenantId());
				String tenantId = idTokenObj.getTenantId();

				AppUser appUser = appUserRepository.findByName(userKey);
				
				if (appUser == null) {
					getLogger().error("userKey '" + userKey + "' not found");
					return "index";
				}

				MicrosoftAccount microsoftAccount = new MicrosoftAccount();
				microsoftAccount.setOwner(appUser);
				microsoftAccount.setName(accountName);
				microsoftAccount.setTenantId(tenantId);
				microsoftAccount.setTokenResponse(tokenResponse);

				microsoftAccount.setEmail(idTokenObj.getName());
				appUser.addMicrosoftAccount(microsoftAccount);
				microsoftAccountRepository.save(microsoftAccount);
				
				session.invalidate();
			} else {
				getLogger().error("ID token failed validation.");
			}
		} else {
			getLogger().error("Unexpected state returned from authority.");
		}

		return "index";
	}
	
	/* (non-Javadoc)
	 * @see com.ynov.dap.controller.BaseController#getClassName()
	 */
	@Override
	public String getClassName() {
		return AuthorizeController.class.getName();
	}

}
