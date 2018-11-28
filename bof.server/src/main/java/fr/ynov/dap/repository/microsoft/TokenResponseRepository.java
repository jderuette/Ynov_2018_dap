package fr.ynov.dap.repository.microsoft;

import org.springframework.data.repository.CrudRepository;

import fr.ynov.dap.auth.TokenResponse;

public interface TokenResponseRepository extends CrudRepository<TokenResponse,Integer> {

}
