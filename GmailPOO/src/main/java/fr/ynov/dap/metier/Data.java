/**
 * 
 */
package fr.ynov.dap.metier;


import java.util.List;

import fr.ynov.dap.authOutlook.TokenResponse;
import fr.ynov.dap.data.Account;
import fr.ynov.dap.data.AppUser;
import javassist.NotFoundException;





/** regrouper les accès aux données dans une seul class
 * @author acer
 * 
 */
public interface Data {
public AppUser consulterUser(String userKey) throws NotFoundException;
public void addUser(String userKey);


public List<Account> listAccount(String userKey);
public void ajouterAccountMicrosoft(String userkey,String adrMail,String accountName,TokenResponse token,String tenantId) throws NotFoundException;
public void ajouterAccountGoogle(String userkey,String adrMail,String accountName,com.google.api.client.auth.oauth2.TokenResponse token) throws NotFoundException;
public Account getAccount(int id) throws NotFoundException;
public TokenResponse toTokenObjectById(int id) throws NotFoundException;
public void Update(TokenResponse tokenResponse,int id);
public List<Account> AllAccount();
public Account getAccount(String accountName);
}
