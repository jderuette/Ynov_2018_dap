package fr.ynov.dap.service.microsoft;

import java.io.IOException;
import java.security.GeneralSecurityException;
import java.util.UUID;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestParam;

import fr.ynov.dap.auth.AuthHelper;
import fr.ynov.dap.auth.IdToken;
import fr.ynov.dap.auth.TokenResponse;
import fr.ynov.dap.microsoft.OutlookUser;
import fr.ynov.dap.model.AppUserModel;
import fr.ynov.dap.model.Google.GoogleAccountModel;
import fr.ynov.dap.model.microsoft.OutlookAccountModel;
import fr.ynov.dap.repository.AppUserRepository;
import fr.ynov.dap.repository.microsoft.OutlookAccountRepository;
import fr.ynov.dap.service.Google.GoogleAccountService;

@Service
public class MicrosoftAccountService {

	@Autowired
	private AppUserRepository appUserRepository;
	//TODO bof by Djer |IDE| Ton IDE t'indique que ce n'est pas utilisé, Bug ? A supprimer ?
	@Autowired
	private OutlookAccountRepository outlookAccountRepository;

	//TODO bof by Djer |Log4J| ton IDE t'indique que ca n'est pas utilisé. En plus tu utilises une catégorie qui n'est pas la bonne
	private final Logger LOG = LogManager.getLogger(GoogleAccountService.class);
	
	//TODO bof by Djer |MVC| Evite que ton service dépende d'objet "sous la responsabilité du controller" comme HttpServletRequest et HttpSession (et Model)
	public String addAccount(final String accountName,final String userKey, final HttpServletRequest request,
			final HttpSession session, Model model) throws GeneralSecurityException {
		UUID state = UUID.randomUUID();
		UUID nonce = UUID.randomUUID();
		String response = "AccountAlreadyAdded";
		session.setAttribute("expected_state", state);
        session.setAttribute("expected_nonce", nonce);
		
		if(appUserRepository.findByUserKey(userKey) != null) {
			session.setAttribute("accountName", accountName);
	        session.setAttribute("userKey", userKey);
			
			String loginUrl = AuthHelper.getLoginUrl(state, nonce);
			model.addAttribute("loginUrl", loginUrl);
			response = "redirect:" + loginUrl;
		}
        return response;
	}
	
	//TODO bof by Djer |MVC| Evite que ton service dépende d'objet "sous la responsabilité du controller" comme HttpServletRequest et HttpSession (et Model)
	public String authorize(String code, String idToken, UUID state,
			HttpServletRequest request) throws ServletException {
		HttpSession session = request.getSession();
		UUID expectedState = (UUID) session.getAttribute("expected_state");
		UUID expectedNonce = (UUID) session.getAttribute("expected_nonce");
		// Make sure that the state query parameter returned matches
		// the expected state
		if (state.equals(expectedState)) {
			IdToken idTokenObj = IdToken.parseEncodedToken(idToken, expectedNonce.toString());
			if (idTokenObj != null) {
				TokenResponse token = AuthHelper.getTokenFromAuthCode(code, idTokenObj.getTenantId());
				return oAuthSuccess(session, token, idTokenObj.getTenantId());
			} else {
				throw new ServletException("ID token failed validation.");
			}
		} else {
			throw new ServletException("Unexpected state returned from authority");
		}
	}
	
	private String oAuthSuccess(HttpSession session, TokenResponse token, String tenantID) {
		String userKey = (String) session.getAttribute("userKey");
		String accountName = (String) session.getAttribute("accountName");
		
		AppUserModel appUserModel = appUserRepository.findByUserKey(userKey);
		OutlookAccountModel outlookAccount = new OutlookAccountModel();
		outlookAccount.setAccountName(accountName);
		outlookAccount.setToken(token);
		outlookAccount.setTenantID(tenantID);
		appUserModel.addOutlookAccount(outlookAccount);
		
		appUserRepository.save(appUserModel);
		return "redirect:/";
	}

}
