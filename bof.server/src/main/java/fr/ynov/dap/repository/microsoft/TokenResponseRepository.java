package fr.ynov.dap.repository.microsoft;

import org.springframework.data.repository.CrudRepository;

import fr.ynov.dap.auth.TokenResponse;

//TODO bof by Djer |JPA| Ne créé cette interface que si tu en as besoin 
public interface TokenResponseRepository extends CrudRepository<TokenResponse,Integer> {

}
