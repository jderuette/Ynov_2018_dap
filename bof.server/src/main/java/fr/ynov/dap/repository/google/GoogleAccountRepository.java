package fr.ynov.dap.repository.google;

import java.util.List;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import fr.ynov.dap.model.Google.GoogleAccountModel;

@Repository
public interface GoogleAccountRepository extends CrudRepository<GoogleAccountModel, Integer> {
}
