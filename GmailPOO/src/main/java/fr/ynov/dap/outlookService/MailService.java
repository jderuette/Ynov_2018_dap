package fr.ynov.dap.outlookService;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

import fr.ynov.dap.authOutlook.AuthHelper;
import fr.ynov.dap.authOutlook.TokenResponse;
import fr.ynov.dap.data.Account;
import fr.ynov.dap.data.MicrosoftAccount;
import fr.ynov.dap.metier.Data;
import javassist.NotFoundException;

@Service
public class MailService {
	@Autowired
	Data data;

	//TODO bes by Djer |SOA| Un service ne devrait pas avoir besoin du "model" ni de la request (se sont des objets sous la responsabilité du "controller")
	//TODO bes by Djer |POO| Le nom de ta méthode doit commencer par une minuscule
	public OutlookService ConnexionOutlook(final Model model, final HttpServletRequest request, final String compte)
			throws NotFoundException {
		OutlookService conn = null;
		Account outlook = data.getAccount(compte);
		String email = outlook.getAdrMail();
		String tenantId = (((MicrosoftAccount) outlook)).getTenantId();
		TokenResponse ancienTokens = data.toTokenObjectById(outlook.getId());
		ancienTokens.setChanged(false);
		TokenResponse nouveauTokens = AuthHelper.ensureTokens(ancienTokens, tenantId);
		if (nouveauTokens.getError() == null) {
			if (nouveauTokens.isChanged()) {
				data.Update(nouveauTokens, outlook.getId());

			}
			conn = OutlookServiceBuilder.getOutlookService(nouveauTokens.getAccessToken(), email);
		}

		return conn;

	}

	//TODO bes by Djer |SOA| C'est étrange un service (MailService) qui à besoin d'un autre service (OutlookService). En generale on a : controller -> Service -> Dao -> (BDD || Api externe)
	//TODO bes by Djer |IOC| Injecte se service, ne demande pas à l'appelant de le fournir
	//TODO bes by Djer |P| Le nom de ta méthode doit commencer par une minuscule
	public Message[] Listmail(OutlookService outlookService) throws IOException {

		String folder = "inbox";

		String sort = "receivedDateTime DESC";

		String properties = "receivedDateTime,from,isRead,subject,bodyPreview";

		PagedResult<Message> messages;
		messages = outlookService.getMessages(folder, sort, properties, 1000000).execute().body();

		return messages.getValue();

	}

	public int unreadMail(OutlookService outlookService) throws IOException {
		int i = 0;
		Message[] messages = Listmail(outlookService);
		for (Message m : messages) {
			if (m.getIsRead() == false)
				i++;

		}

		return i;
	}

}
