/**
 * 
 */
package fr.ynov.dap.web;

import java.io.IOException;
import java.security.GeneralSecurityException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import fr.ynov.dap.data.Account;
import fr.ynov.dap.metier.Data;

/**
 * @author acer
 *
 */
@Controller
public class Admin {
	@Autowired
	private Data dataBase;
	@RequestMapping("/admin")
	public String admin(Model model) throws IOException, GeneralSecurityException {
		List<Account> accounts = dataBase.AllAccount();
		model.addAttribute("Account", accounts);
		return "admin.html";
	}
}
