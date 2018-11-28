package fr.ynov.dap.repository.microsoft;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import fr.ynov.dap.model.microsoft.OutlookAccountModel;

@Repository
public interface OutlookAccountRepository extends CrudRepository<OutlookAccountModel, Integer>{

}
