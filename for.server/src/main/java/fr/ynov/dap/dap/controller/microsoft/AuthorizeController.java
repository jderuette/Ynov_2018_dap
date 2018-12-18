package fr.ynov.dap.dap.controller.microsoft;

import java.io.IOException;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import fr.ynov.dap.dap.data.AppUser;
import fr.ynov.dap.dap.data.MicrosoftAccount;
import fr.ynov.dap.dap.data.Token;
import fr.ynov.dap.dap.microsoft.auth.AuthHelper;
import fr.ynov.dap.dap.microsoft.auth.IdToken;
import fr.ynov.dap.dap.repository.AppUserRepository;
import fr.ynov.dap.dap.repository.MicrosoftUserRepository;
import fr.ynov.dap.dap.service.OutlookService;
import fr.ynov.dap.dap.service.OutlookServiceBuilder;
import fr.ynov.dap.dap.service.OutlookUser;

@Controller
public class AuthorizeController {

	@Autowired
	AppUserRepository userRepo;
	@Autowired
	MicrosoftUserRepository microsoftRepo;
	
	@RequestMapping(value = "/account/add/microsoft/{accountName}")
    public void addMicrosoftAccount(@PathVariable final String accountName, @RequestParam final String userKey,
            final HttpServletRequest request, final HttpSession session, final HttpServletResponse response, RedirectAttributes redirectAttributes) throws IOException{

        AppUser userAccount = userRepo.findByUserKey(userKey);

        if (null != userAccount) {
        	UUID state = UUID.randomUUID();
            UUID nonce = UUID.randomUUID();

            session.setAttribute("state", state);
            session.setAttribute("nonce", nonce);
            session.setAttribute("userKey", userKey);
            session.setAttribute("accountName", accountName);

            String redirectUrl = AuthHelper.getLoginUrl(state, nonce);

            response.sendRedirect(redirectUrl);
        }
        //TODO for by Djer |Log4J| (else) une petite Log ?
    }
	
	
  @RequestMapping(value="/authorize", method=RequestMethod.POST)
  public String authorize(
      @RequestParam("code") String code, 
      @RequestParam("id_token") String idToken,
      @RequestParam("state") UUID state,
      HttpServletRequest request) { {
	    // Get the expected state value from the session
	    HttpSession session = request.getSession();
	    UUID expectedState = (UUID) session.getAttribute("state");
        UUID expectedNonce = (UUID) session.getAttribute("nonce");
        String userKey = (String) session.getAttribute("userKey");
        String accountName = (String) session.getAttribute("accountName");
		AppUser appUser = userRepo.findByUserKey(userKey);
	
	    // Make sure that the state query parameter returned matches
	    // the expected state
	    if (state.equals(expectedState)) {
	    	IdToken idTokenObj = IdToken.parseEncodedToken(idToken, expectedNonce.toString());
	    	  OutlookUser user = null;
	    	if (idTokenObj != null) {
	    	  Token token = AuthHelper.getTokenFromAuthCode(code, idTokenObj.getTenantId());
	    	  session.setAttribute("tokens", token);
	    	  session.setAttribute("userConnected", true);
	    	  session.setAttribute("userName", idTokenObj.getName());
		    	// Get user info
		    	  OutlookService outlookService = OutlookServiceBuilder.getOutlookService(token.getAccessToken(), null);
		    	  try {
		    	    user = outlookService.getCurrentUser().execute().body();
		    	    session.setAttribute("userEmail", user.getMail());
		    	  } catch (IOException e) {
		    	    //TODO for by Djer |Log4J| Une petite Log ?
		    	    session.setAttribute("error", e.getMessage());
		    	  }
	    	  session.setAttribute("userTenantId", idTokenObj.getTenantId());
	    	  if(appUser != null)
	    	  {
	    		  MicrosoftAccount msAccount = new MicrosoftAccount();
	                msAccount.setToken(token);
	                msAccount.setTenantId(idTokenObj.getTenantId());
	                msAccount.setAccountName(accountName);

	                appUser.addMicrosoftAccount(msAccount);
	                userRepo.save(appUser);
	    		}
	    	} else {
	    	  //TODO for by Djer |Log4J| Une petite Log ?
	    	  session.setAttribute("error", "ID token failed validation.");
	    	}
	    }
	    else {
	      //TODO for by Djer |Log4J| Une petite Log ?
	      session.setAttribute("error", "Unexpected state returned from authority.");
	    }
	    return "redirect:/mail";
	  }
  }
  
  @RequestMapping("/logout")
  public String logout(HttpServletRequest request) {
    HttpSession session = request.getSession();
    session.invalidate();
    return "redirect:/index";
  }
}