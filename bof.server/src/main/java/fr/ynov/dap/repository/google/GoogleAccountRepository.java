package fr.ynov.dap.repository.google;

import java.util.List;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import fr.ynov.dap.model.Google.GoogleAccountModel;

@Repository
//TODO bof by Djer |JPA| Ne créé cette interface que si tu en as besoin (pour le moment elle est utilisé 2 fois a des endroits ou elle ne sert pas)
public interface GoogleAccountRepository extends CrudRepository<GoogleAccountModel, Integer> {
}
