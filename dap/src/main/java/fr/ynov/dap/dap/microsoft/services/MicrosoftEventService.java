package fr.ynov.dap.dap.microsoft.services;

import java.io.IOException;

import org.springframework.stereotype.Service;

import fr.ynov.dap.dap.data.MicrosoftAccount;
import fr.ynov.dap.dap.exception.SecretFileAccesException;
import fr.ynov.dap.dap.model.MicrosoftEvent;

/**
 *
 * @author David_tepoche
 *
 */
@Service
public class MicrosoftEventService extends MicrosoftBaseService {
    /**
     * get nbr of event from the date from.
     *
     * @param microsoftAccount the msAccount
     * 
     * TODO duv by Djer |POO| Format ? Ou alors laisse un Objet Date "Java" et format dans ton service. Comme cela est spécific Microsoft le formaté dans le service est logique. S'il s'agissait d'une information saisie par l'utilisateur ca serait plutot de la responsabilité du controller (qui la transformerait en "date standard Java" et laisserait le service Microsoft gérer les spécifités du service). C'est ausis à cela que sert la Javadoc : se poser des questions sur l'API qu'on expose ... 
     * @param dateFrom         date
     * @param maxResult        result max to display
     * @return array of microsoft event
     * @throws IOException              throw from
     * @throws SecretFileAccesException throw
     */
    public MicrosoftEvent[] getEvent(final MicrosoftAccount microsoftAccount, final String dateFrom,
            final Integer maxResult) throws IOException, SecretFileAccesException {

        return getMicrosoftService(microsoftAccount)
                .getEvents("start/dateTime DESC", "start/dateTime  GE '" + dateFrom + "'", null, maxResult).execute()
                .body().getValue();
    }

    @Override
    protected final String getClassName() {
        return MicrosoftEventService.class.getName();
    }
}
