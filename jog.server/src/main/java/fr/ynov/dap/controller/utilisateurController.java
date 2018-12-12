package fr.ynov.dap.controller;

import java.io.IOException;
import java.security.GeneralSecurityException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import fr.ynov.dap.data.AppUser;
import fr.ynov.dap.repository.AppUserRepository;

@RestController
//TODO job by Djer |Audit Code| active les outils d'audit de code (PMD/Checkstyle) pour éviter ce genre d'erreur. Une Classe Commence par une majuscule 
public class utilisateurController {
	@Autowired
	AppUserRepository repo;

	@RequestMapping("user/add/{userKey}")
	public void addUtilisateur(@PathVariable("userKey") String userKey) throws IOException, GeneralSecurityException {

		repo.save(new AppUser(userKey));
		// AppUser user =repo.selectByName(userKey);
		// System.out.println("id: "+user.getId()+" Name: "+user.getName());

	}

}
