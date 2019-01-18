package fr.ynov.dap.metier;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import fr.ynov.dap.authOutlook.TokenResponse;
import fr.ynov.dap.dao.AccountRepository;
import fr.ynov.dap.dao.AppUserRepository;
import fr.ynov.dap.data.Account;
import fr.ynov.dap.data.AppUser;
import fr.ynov.dap.data.MicrosoftAccount;
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

			throw new NotFoundException("Compte introvable  ");
		}

		return user;
	}

	@Override
	public Account getAccount(int id) throws NotFoundException {

		for (Account account : accountRepository.findAll()) {
			if (account.getId() == id)
				return account;

		}

		throw new NotFoundException("Compte introvable  ");

	}

	@Override
	public void addUser(String userKey) {
		appUserRepository.save(new AppUser(userKey));
	}



	@Override
	public List<Account> listAccount(String userKey) {
		
		return accountRepository.listAccount(userKey);
	}

	@Override
	public void ajouterAccountMicrosoft(String userkey, String adrMail, String accountName, TokenResponse token,
			String tenantId) throws NotFoundException {
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
	// TODO bes by Djer |JavaDoc| Précise que ton "id" est un "account Id", et
	// renome ta variable au passage
	public TokenResponse toTokenObjectById(int accountid) throws NotFoundException {
		TokenResponse token = new TokenResponse();
		Account account = getAccount(accountid);
		if (account instanceof MicrosoftAccount) {
			// TODO bes by Djer |POO| Pour simplifier la lecture des lignes suviantes tu
			// peux créer une variable local "microsoftAccount" et faire ton cast une seul
			// fois

			token.setTokenType((((MicrosoftAccount) account)).getTokenType());
			token.setScope((((MicrosoftAccount) account)).getScope());
			token.setAccessToken((((MicrosoftAccount) account)).getAccessToken());
			token.setRefreshToken((((MicrosoftAccount) account)).getRefreshToken());
			token.setIdToken((((MicrosoftAccount) account)).getIdToken());
			token.setExpirationTime((((MicrosoftAccount) account)).getExpirationTime());
		}

		return token;
	}

	@Override
	public void Update(TokenResponse tokenResponse, int id) {


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
	
		String accessToken = token.getAccessToken();
		String tokenType = token.getTokenType();
		long expiresInSeconds = token.getExpiresInSeconds();
		String refreshToken = token.getRefreshToken();
		String scope = token.getScope();
		fr.ynov.dap.data.GoogleAccount googleAccount = new fr.ynov.dap.data.GoogleAccount(accountName,
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
