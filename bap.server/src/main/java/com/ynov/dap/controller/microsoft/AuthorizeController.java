package com.ynov.dap.controller.microsoft;

import java.util.UUID;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.ynov.dap.domain.AppUser;
import com.ynov.dap.domain.microsoft.MicrosoftAccount;
import com.ynov.dap.model.microsoft.IdToken;
import com.ynov.dap.model.microsoft.TokenResponse;
import com.ynov.dap.repository.AppUserRepository;
import com.ynov.dap.repository.microsoft.MicrosoftAccountRepository;
import com.ynov.dap.service.microsoft.AuthHelper;

@Controller
public class AuthorizeController {

	@Autowired
	private AppUserRepository appUserRepository;

	@Autowired
	private MicrosoftAccountRepository microsoftAccountRepository;

	@RequestMapping(value = "/authorize", method = RequestMethod.POST)
	public String authorize(@RequestParam("code") String code, @RequestParam("id_token") String idToken,
			@RequestParam("state") UUID state, HttpServletRequest request) {

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

				MicrosoftAccount microsoftAccount = new MicrosoftAccount();
				microsoftAccount.setOwner(appUser);
				microsoftAccount.setName(accountName);
				microsoftAccount.setTenantId(tenantId);
				microsoftAccount.setTokenResponse(tokenResponse);

				microsoftAccount.setEmail(idTokenObj.getName());
				appUser.addMicrosoftAccount(microsoftAccount);
				microsoftAccountRepository.save(microsoftAccount);
			} else {
				session.setAttribute("error", "ID token failed validation.");
			}
		} else {
			session.setAttribute("error", "Unexpected state returned from authority.");
		}

		return "index";
	}

	@RequestMapping("/logout")
	public String logout(HttpServletRequest request) {
		HttpSession session = request.getSession();
		session.invalidate();
		return "index";
	}

}
