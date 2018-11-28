package fr.ynov.dap.dap.repositories;

import fr.ynov.dap.dap.data.microsoft.Token;
import org.springframework.data.repository.CrudRepository;

public interface TokenRepository extends CrudRepository<Token, Integer> {
}
