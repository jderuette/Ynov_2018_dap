package fr.ynov.dap.GmailPOO.metier;

import java.util.Date;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import fr.ynov.Outlook.auth.TokenResponse;
import fr.ynov.dap.GmailPOO.dao.AccountRepository;
import fr.ynov.dap.GmailPOO.dao.AppUserRepository;
import fr.ynov.dap.GmailPOO.data.Account;
import fr.ynov.dap.GmailPOO.data.AppUser;
import fr.ynov.dap.GmailPOO.data.MicrosoftAccount;
import javassist.NotFoundException;

@Service
public class DataImp implements Data {
	@Autowired
	AccountRepository accountRepository;
	@Autowired
	private AppUserRepository appUserRepository;

	@Override
	public AppUser consulterUser(String userKey) throws NotFoundException {
		AppUser user = appUserRepository.findByUserkey(userKey);
		if (user == null) {
		  //TODO bes by Djer |Gestion Exception| Tu as outblié le "throw", ici tu contruit une exception "pour rien"
			new NotFoundException("Compte introvable  ");
		}
		
		return user;
	}

	@Override
	public Account getAccount(int id) {

		for (Account account : accountRepository.findAll()) {
			if (account.getId() == id)
				return account;

		}

		//TODO bes by Djer |Gestion Exception| Tu as outblié le "throw", ici tu contruit une exception "pour rien"
		new NotFoundException("Compte introvable  ");
		//Une fois le "throw" ajouté, celle ligne de code ne sera plsu accessible (donc plus utile)
		return null;

	}

	@Override
	public void addUser(String userKey) {
	    //TODO bes by Djer |POO| Supprime le s'il n'est plus exacte.
		// TODO Auto-generated method stub
		appUserRepository.save(new AppUser(userKey));
	}

	@Override
	public void deleteUser(String userKey) {
	    //TODO bes by Djer |POO| si "par erreur" tu appel cette méthode, tu auras du mal à te rendre compte que tu ne la pas implémenté. Pour éviter cela soit tu l'implemente, soit tu leves une Exception? par exemple une "NoSuchMethodError" avec un message indiquant clairement que ca n'est pas (encore) implémenté. 
		// TODO Auto-generated method stub

	}

	@Override
	public Page<Account> listAccount(String userKey, int page, int size) {
	  //TODO bes by Djer |POO| Supprime le s'il n'est plus exacte.
		// TODO Auto-generated method stub

	    //TODO bes by Djer |POO| Dans la Javadoc il est recommandé d'utilsier ""of(....)" à la palce.
		return accountRepository.listAccount(userKey, new PageRequest(page, size));
	}

	@Override
	public List<Account> listAccount1(String userKey) {
	    //TODO bes by Djer |POO| Supprime le s'il n'est plus exacte.
		// TODO Auto-generated method stub
		return accountRepository.listAccount1(userKey);
	}

	@Override
	public void ajouterAccountMicrosoft(String userkey, String adrMail, String accountName, TokenResponse token,
			String tenantId) throws NotFoundException {
	    //TODO bes by Djer |POO| Supprime le s'il n'est plus exacte.
		// TODO Auto-generated method stub
		String tokenType = token.getTokenType();
		String scope = token.getScope();
		int expiresIn = token.getExpiresIn();
		String accessToken = token.getAccessToken();
		String refreshToken = token.getRefreshToken();
		String idToken = token.getIdToken();
		Date expirationTime = token.getExpirationTime();
		MicrosoftAccount microsoftAccount = new MicrosoftAccount(accountName, adrMail, consulterUser(userkey),
				tokenType, scope, expiresIn, idToken, tenantId, new Date(), expirationTime, accessToken, refreshToken);
		accountRepository.save(microsoftAccount);

	}

	@Override
	//TODO bes by Djer |JavaDoc| Précise que ton "id" est un "account Id", et renome ta variable au passage
	public TokenResponse toTokenObjectById(int id) {
		TokenResponse token = new TokenResponse();
		Account account = getAccount(id);
		if (account instanceof MicrosoftAccount) {
		    //TODO bes by Djer |POO| Pour simplifier la lecture des lignes suviantes tu peux créer une variable local "microsoftAccount" et faire ton cast une seul fois

			token.setTokenType((((MicrosoftAccount) account)).getTokenType());
			token.setScope((((MicrosoftAccount) account)).getScope());
			// token.setExpiresIn((((MicrosoftAccount)account)).getExpiresIn());
			token.setAccessToken((((MicrosoftAccount) account)).getAccessToken());
			token.setRefreshToken((((MicrosoftAccount) account)).getRefreshToken());
			token.setIdToken((((MicrosoftAccount) account)).getIdToken());
			token.setExpirationTime((((MicrosoftAccount) account)).getExpirationTime());
		}

		return token;
	}

	@Override
	public void Update(TokenResponse tokenResponse, int id) {
	  //TODO bes by Djer |POO| Supprime le s'il n'est plus exacte.
		// TODO Auto-generated method stub

		String tokenType = tokenResponse.getTokenType();
		int expiresIn = tokenResponse.getExpiresIn();
		String accessToken = tokenResponse.getAccessToken();
		String refreshToken = tokenResponse.getRefreshToken();
		String idToken = tokenResponse.getIdToken();

		accountRepository.update(id, tokenType, expiresIn, accessToken, refreshToken, idToken, new Date(),
				tokenResponse.getExpirationTime());

	}

	@Override
	public void ajouterAccountGoogle(String userkey, String adrMail, String accountName,
			com.google.api.client.auth.oauth2.TokenResponse token) throws NotFoundException {
	  //TODO bes by Djer |POO| Supprime le s'il n'est plus exacte.
		// TODO Auto-generated method stub
		String accessToken = token.getAccessToken();
		String tokenType = token.getTokenType();
		long expiresInSeconds = token.getExpiresInSeconds();
		String refreshToken = token.getRefreshToken();
		String scope = token.getScope();
		fr.ynov.dap.GmailPOO.data.GoogleAccount googleAccount = new fr.ynov.dap.GmailPOO.data.GoogleAccount(accountName,
				adrMail, consulterUser(userkey), accessToken, tokenType, expiresInSeconds, refreshToken, scope);
		accountRepository.save(googleAccount);

	}

	@Override
	public List<Account> AllAccount() {
		
		return (List<Account>) accountRepository.findAll();
	}

	@Override
	public Account getAccount(String accountName) {
		
		return accountRepository.findByName(accountName);
	}
}
