package fr.ynov.dap.repository;

import fr.ynov.dap.data.microsoft.TokenResponse;

import org.springframework.data.repository.CrudRepository;

public interface TokenRepository extends CrudRepository<TokenResponse, Integer> {

}