package fr.ynov.dap.dap.microsoft.services;

import java.io.IOException;

import org.springframework.stereotype.Service;

import com.fasterxml.jackson.annotation.JsonBackReference;

import fr.ynov.dap.dap.data.MicrosoftAccount;
import fr.ynov.dap.dap.exception.SecretFileAccesException;
import okhttp3.ResponseBody;

/**
 *
 * @author David_tepoche
 *
 */
@Service
public class MicrosoftContactService extends MicrosoftBaseService {

    /**
     * get the nbr of contact for msAccount.
     *
     * @return nbr of contact
     * @param microsoftAccount the mAccount
     * @throws SecretFileAccesException throw if the service fail.
     * @throws IOException              throw if load of credential fail of the sett
     *                                  of redirection fail.
     */
    public Integer getNbrContact(final MicrosoftAccount microsoftAccount) throws IOException, SecretFileAccesException {
        // get the reponseBody because MS send only the nomber of contact without json
        // etc ...
        Integer nbrContact = 0;
        //TODO duv by Djer |POO| "e" ? Est-ce que ce nom est bien claire ?
        ResponseBody e = getMicrosoftService(microsoftAccount).getNbrContact().execute().body();

        if (e != null && !e.string().isEmpty()) {
            try {
                nbrContact = (Integer.valueOf(e.string()));
            } catch (NumberFormatException ex) {
                //TODO duv by Djer |log4J| Quel valeur ? On l'impression que Microsoft à renvoyé le accountName dans la log ... (via la "cause" on arrive à deviner quand même)
                getLogger().error("le count des contact microsoft a renvoyé une reponse impossible a parser !"
                        + microsoftAccount.getAccountName(), ex);
            }
            return nbrContact;
        } else {
            //TODO duv by Djer |log4J| Message pas très juste, le nbrContact pourait être "vide". Il te manque aussi un petit espace avant le nom du compte microsoft.
            //TODO duv by Djer |log4J| Le contexte dela log pourrais être amélioré. Afficher le UserKey serait bien utile. Il devrait être accessible via ton entité MicrosoftAccount. 
            getLogger().error(
                    "le count des contact microsoft a renvoyé une reponse null" + microsoftAccount.getAccountName());

        }
        return nbrContact;
    }

    @Override
    protected final String getClassName() {
        //TODO duv by Djer |Log4J| Ca n'est pas la bonne catégorie !
        return MicrosoftMailService.class.getName();
    }
}
