package fr.ynov.dap.service.microsoft;

import java.util.HashMap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import fr.ynov.dap.auth.TokenResponse;
import fr.ynov.dap.model.microsoft.OutlookAccountModel;
import fr.ynov.dap.repository.microsoft.OutlookAccountRepository;
import fr.ynov.dap.repository.microsoft.TokenResponseRepository;

@Service
public class MicrososftCredentialsService {

    
    //TODO bof by Djer |POO| Si tu ne précise pas de modifier "hérite" de celui de la classe. Cet atribut est donc public !
	@Autowired
	OutlookAccountRepository outlookAccountRepository;
	
	
	public HashMap<String,TokenResponse> getCredentials() {
		
		HashMap<String,TokenResponse> response = new HashMap<>();
		
		for (OutlookAccountModel model : outlookAccountRepository.findAll() ) {
			response.put(model.getTenantID(), model.getToken());
		}
		
		return response;
	}
	
}
