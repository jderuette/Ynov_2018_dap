package fr.ynov.dap.contract;

import org.springframework.data.repository.CrudRepository;

import fr.ynov.dap.microsoft.model.TokenResponse;

public interface TokenRepository extends CrudRepository<TokenResponse, Integer> {

}
