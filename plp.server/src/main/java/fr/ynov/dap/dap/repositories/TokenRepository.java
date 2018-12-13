package fr.ynov.dap.dap.repositories;

import fr.ynov.dap.dap.data.microsoft.Token;
import org.springframework.data.repository.CrudRepository;

//TODO plp by DJer |JPA| Ne créer pas cette interface si tu n'en as pas besoin.
public interface TokenRepository extends CrudRepository<Token, Integer> {
}
