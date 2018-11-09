package fr.ynov.dap.dap.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import fr.ynov.dap.dap.model.GoogleAccountModel;

@Repository
public interface GoogleAccountRepository extends CrudRepository<GoogleAccountModel, Integer> {
}
