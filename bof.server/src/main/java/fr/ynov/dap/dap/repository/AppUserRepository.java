package fr.ynov.dap.dap.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import fr.ynov.dap.dap.model.AppUserModel;

@Repository
public interface AppUserRepository extends  CrudRepository<AppUserModel, Integer> {

	AppUserModel findByUserKey(String userKey);
}
