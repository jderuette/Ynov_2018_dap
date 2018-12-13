package fr.ynov.dap.dap.repositories;

import fr.ynov.dap.dap.data.GoogleAccount;
import org.springframework.data.repository.CrudRepository;

//TODO plp by Djer |JPA| Tu ne devrais plus avoir besoin de ce Repo, une fois que tu fera la sauvegarde par le AppUser.
public interface GoogleAccountRepository extends CrudRepository<GoogleAccount, Integer> {

}
