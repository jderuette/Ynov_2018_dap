package fr.ynov.dap.repository;

import fr.ynov.dap.data.microsoft.TokenResponse;

import org.springframework.data.repository.CrudRepository;

/**
 * The Interface TokenRepository.
 */
public interface TokenRepository extends CrudRepository<TokenResponse, Integer> {

}