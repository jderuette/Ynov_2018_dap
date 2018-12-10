package fr.ynov.dap.GoogleMaven.repository;

import java.util.List;

import javassist.NotFoundException;
import fr.ynov.dap.GoogleMaven.data.*;



public interface AccountData {
	public AppUser consulterUser(String userKey) throws NotFoundException;
	public void addUser(String userKey);
	public void deleteUser(String userKey);
	//public Page<GoogleAccount> listGoogleAccount(String userKey,int page,int size);
	public List<GoogleAccount> listGoogleAccount1(String userKey);
	public void ajouterAccount(String userkey,String adrMail,String accountName) throws NotFoundException;
	String getAccount(int id);
}
