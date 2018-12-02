package fr.ynov.dap.repository;

import fr.ynov.dap.data.microsoft.TokenResponse;

import org.springframework.data.repository.CrudRepository;

//TODO bal by Djer |JPA| Tant que tu n'en a pas besoin, il n'est pas utile de créer le Repository.
public interface TokenRepository extends CrudRepository<TokenResponse, Integer> {

}