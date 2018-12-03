/**
 * 
 */
package fr.ynov.dap.GmailPOO.metier;


import java.util.List;

import org.springframework.data.domain.Page;

import fr.ynov.Outlook.auth.TokenResponse;
import fr.ynov.dap.GmailPOO.data.Account;
import fr.ynov.dap.GmailPOO.data.AppUser;
import fr.ynov.dap.GmailPOO.data.MicrosoftAccount;
import javassist.NotFoundException;


//TODO bes by Djer |IDE| Formate le code, organise les imports.

//TODO bes by Djer |JavaDoc| une petite Javadoc pour expliquer le but de cette interface serait bien utile.
/**
 * @author acer
 *
 */
public interface Data {
public AppUser consulterUser(String userKey) throws NotFoundException;
public void addUser(String userKey);
public void deleteUser(String userKey);
public Page<Account> listAccount(String userKey,int page,int size);
public List<Account> listAccount1(String userKey);
public void ajouterAccountMicrosoft(String userkey,String adrMail,String accountName,TokenResponse token,String tenantId) throws NotFoundException;
public void ajouterAccountGoogle(String userkey,String adrMail,String accountName,com.google.api.client.auth.oauth2.TokenResponse token) throws NotFoundException;
public Account getAccount(int id);
public TokenResponse toTokenObjectById(int id);
public void Update(TokenResponse tokenResponse,int id);
public List<Account> AllAccount();
public Account getAccount(String accountName);
}
