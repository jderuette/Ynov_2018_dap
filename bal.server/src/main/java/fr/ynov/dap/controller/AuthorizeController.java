package fr.ynov.dap.controller;

import org.springframework.stereotype.Controller;

@Controller
//TODO bal by Djer |POO| Classe à supprimer ? 
public class AuthorizeController {
	/*
	 * @RequestMapping(value="/authorize", method=RequestMethod.POST) public String
	 * authorize(
	 * 
	 * @RequestParam("code") String code,
	 * 
	 * @RequestParam("id_token") String idToken,
	 * 
	 * @RequestParam("state") UUID state, HttpServletRequest request) { { // Get the
	 * expected state value from the session HttpSession session =
	 * request.getSession(); UUID expectedState = (UUID)
	 * session.getAttribute("expected_state"); UUID expectedNonce = (UUID)
	 * session.getAttribute("expected_nonce");
	 * 
	 * // Make sure that the state query parameter returned matches // the expected
	 * state if (state.equals(expectedState)) { session.setAttribute("authCode",
	 * code); session.setAttribute("idToken", idToken); } else {
	 * session.setAttribute("error", "Unexpected state returned from authority."); }
	 * return "mail"; }
	 * 
	 * }
	 */
 }
