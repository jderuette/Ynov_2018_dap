package fr.ynov.dap.dap.controller.microsoft;

import java.io.IOException;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import fr.ynov.dap.dap.auth.IdToken;
import fr.ynov.dap.dap.auth.TokenResponse;
import fr.ynov.dap.dap.data.AppUser;
import fr.ynov.dap.dap.data.AppUserRepository;
import fr.ynov.dap.dap.data.MicrosoftAccount;
import fr.ynov.dap.dap.service.microsoft.IOutlookService;
import fr.ynov.dap.dap.service.microsoft.MicrosoftService;
import fr.ynov.dap.dap.service.microsoft.OutlookServiceBuilder;
import fr.ynov.dap.dap.service.microsoft.ObjectUtils.OutlookUser;

@Controller
public class AuthorizeController extends MicrosoftService {
	
	
	
	@Autowired
    private AppUserRepository repo;

  @RequestMapping(value="/authorize", method=RequestMethod.POST)
  public String authorize(
      @RequestParam("code") String code, 
      @RequestParam("id_token") String idToken,
      @RequestParam("state") UUID state,
      HttpServletRequest request) { {
    // Get the expected state value from the session
    HttpSession session = request.getSession();
    UUID expectedState = (UUID) session.getAttribute("expected_state");
    UUID expectedNonce = (UUID) session.getAttribute("expected_nonce");

    String userId =  (String) session.getAttribute("userId");
    String name = (String) session.getAttribute("name");

    // Make sure that the state query parameter returned matches
    // the expected state
    if (state.equals(expectedState)) {
    	IdToken idTokenObj = IdToken.parseEncodedToken(idToken, expectedNonce.toString());
    	if (idTokenObj != null) {
    	  TokenResponse tokenResponse = getTokenFromAuthCode(code, idTokenObj.getTenantId());
    	  session.setAttribute("tokens", tokenResponse);
    	  session.setAttribute("userConnected", true);
    	  session.setAttribute("userName", idTokenObj.getName());
    	  AppUser currUser = repo.findByUserkey(userId);
    	  if(currUser == null) {
    	  
    	  AppUser newUser = new AppUser();
    	  newUser.setUserkey(userId);
    	  MicrosoftAccount msAccount = new MicrosoftAccount();
          msAccount.setToken(tokenResponse);
          msAccount.setTenantId(idTokenObj.getTenantId());
          msAccount.setEmail(idTokenObj.getEmail());
          msAccount.setName(name);

          newUser.addMicrosoftAccount(msAccount);

          repo.save(newUser);
    	  }else {
    		  MicrosoftAccount msAccount = new MicrosoftAccount();
              msAccount.setToken(tokenResponse);
              msAccount.setTenantId(idTokenObj.getTenantId());
              msAccount.setEmail(idTokenObj.getEmail());
              msAccount.setName(name);

              currUser.addMicrosoftAccount(msAccount);

              repo.save(currUser);
    	  }
    		  
    	  
    	  
    	  
    	  
    	  IOutlookService outlookService = OutlookServiceBuilder.getOutlookService(tokenResponse.getAccessToken());
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
    }
    else {
      session.setAttribute("error", "Unexpected state returned from authority.");
    }
    
    return "redirect:/?userKey="+userId;
  }
}
  
  @RequestMapping("/logout")
  public String logout(HttpServletRequest request, Model model) {
    HttpSession session = request.getSession();
    session.invalidate();
    model.addAttribute("content", "login"); 
    return "index";
  }
}
