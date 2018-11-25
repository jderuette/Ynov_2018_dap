package com.ynov.dap.microsoft.controllers;

import java.io.IOException;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.ynov.dap.data.AppUser;
import com.ynov.dap.microsoft.business.AuthHelper;
import com.ynov.dap.microsoft.business.OutlookService;
import com.ynov.dap.microsoft.business.OutlookServiceBuilder;
import com.ynov.dap.microsoft.data.MicrosoftAccount;
import com.ynov.dap.microsoft.models.IdToken;
import com.ynov.dap.microsoft.models.OutlookUser;
import com.ynov.dap.microsoft.models.TokenResponse;
import com.ynov.dap.microsoft.repositories.MicrosoftAccountRepository;
import com.ynov.dap.repositories.AppUserRepository;

@Controller
public class AuthorizeController {
	
    @Autowired
    private AppUserRepository appUserRepository;
    
    @Autowired
    private MicrosoftAccountRepository microsoftAccountRepository;

  @RequestMapping(value="/authorize", method=RequestMethod.POST)
  public String authorize(
      @RequestParam("code") String code,
      @RequestParam("id_token") String idToken,
      @RequestParam("state") UUID state,
      HttpServletRequest request) {
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
    	  TokenResponse tokenResponse = AuthHelper.getTokenFromAuthCode(code, idTokenObj.getTenantId());
    	  session.setAttribute("tokens", tokenResponse);
    	  session.setAttribute("userConnected", true);
    	  session.setAttribute("userName", idTokenObj.getName());
          String tenantId = idTokenObj.getTenantId();
          System.out.println("Tenant ID");
          System.out.println(tenantId);
          
          AppUser appUser = appUserRepository.findByName(userKey);
          System.out.println(appUser.getName());

          MicrosoftAccount microsoftAccount = new MicrosoftAccount();
          microsoftAccount.setOwner(appUser);
          microsoftAccount.setName(accountName);
          microsoftAccount.setTenantId(tenantId);
          microsoftAccount.setTokenResponse(tokenResponse);
          
          System.out.println("email");
          System.out.println(idTokenObj.getName());

          
          microsoftAccount.setEmail(idTokenObj.getName());
          appUser.addMicrosoftAccount(microsoftAccount);
          microsoftAccountRepository.save(microsoftAccount);

	    	// Get user info
	    	  OutlookService outlookService = OutlookServiceBuilder.getOutlookService(tokenResponse.getAccessToken(), null);
	    	  OutlookUser user;
	    	  try {
	    	    user = outlookService.getCurrentUser().execute().body();
	    	    session.setAttribute("userEmail", user.getMail());
	    	  } catch (IOException e) {
	    	    session.setAttribute("error", e.getMessage());
	    	  }
    	  
    	  session.setAttribute("userTenantId", idTokenObj.getTenantId());
    	} else {
    	  session.setAttribute("error", "ID token failed validation.");
    	}
    } else {
      session.setAttribute("error", "Unexpected state returned from authority.");
    }

    return "redirect:/index";
  }

  @RequestMapping("/logout")
  public String logout(HttpServletRequest request) {
    HttpSession session = request.getSession();
    session.invalidate();
    return "redirect:/index";
  }
  
}
