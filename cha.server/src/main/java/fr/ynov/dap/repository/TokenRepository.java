package fr.ynov.dap.repository;

import fr.ynov.dap.data.microsoft.TokenResponse;

import org.springframework.data.repository.CrudRepository;

/**
 * The Interface TokenRepository.
 */
//TODO cha by Djer |JPA| Si tu ne l'utilises pas, ne cré pas cette interface
public interface TokenRepository extends CrudRepository<TokenResponse, Integer> {

}