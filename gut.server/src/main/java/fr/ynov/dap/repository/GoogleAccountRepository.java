package fr.ynov.dap.repository;

import org.springframework.data.repository.CrudRepository;

import fr.ynov.dap.data.google.GoogleAccount;

//TODO gut by Djer |JPA| Ce repository n'estp as utile, il vaut mieu passer par le AppUser (cf mes autres TO-DO)
public interface GoogleAccountRepository extends CrudRepository<GoogleAccount, Integer>{

}
