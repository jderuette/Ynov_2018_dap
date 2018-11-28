package com.ynov.dap.controller.microsoft;

import java.util.UUID;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.ynov.dap.controller.BaseController;
import com.ynov.dap.service.microsoft.AuthHelper;

@RestController
@RequestMapping("account")
public class MicrosoftAccountController extends BaseController {

	@GetMapping("/add/microsoft/{accountName}")
	public void addAccount(@PathVariable final String accountName,
			@RequestParam(value = "userKey", required = true) final String userKey, final HttpServletRequest request,
			final HttpServletResponse response) {

		UUID state = UUID.randomUUID();
		UUID nonce = UUID.randomUUID();

		HttpSession session = request.getSession();
		session.setAttribute("expected_state", state);
		session.setAttribute("expected_nonce", nonce);

		session.setAttribute("accountName", accountName);
		session.setAttribute("userKey", userKey);

		final String redirectUri = AuthHelper.getLoginUrl(state, nonce);

		response.setHeader("Location", redirectUri);
		response.setStatus(302);
	}

	@Override
	public String getClassName() {
		return MicrosoftAccountController.class.getName();
	}
	
}
