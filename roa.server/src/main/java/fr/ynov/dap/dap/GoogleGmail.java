package fr.ynov.dap.dap;

import java.io.IOException;
import java.security.GeneralSecurityException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.google.api.services.gmail.Gmail;
import com.google.api.services.gmail.model.Label;
import com.google.api.services.gmail.model.ListLabelsResponse;

/**
 * @author alex
 */
@Service
public class GoogleGmail {
    /**
     * Récupération de l'objet config par autowire.
     */
    @Autowired
    private Config config;
    /**
     * Récupération de l'objet connexionGoogle par autowire.
     */
    @Autowired
    private ConnexionGoogle connexionGoogle;

    /**
     * Permet de récupérer la liste des labels de la boite gmail de l'utilisateur.
     * @throws IOException problème lié à la récupération des credentials
     * @throws GeneralSecurityException exception de sécurité
     * @return liste des labels
     * @param user identifiant de l'utilisateur
     */
    public List<String> getListLabel(final String user) throws IOException, GeneralSecurityException {
        // Se connecte à l'API gmail avec les credentials sauvegardés
        final NetHttpTransport httpTransport = GoogleNetHttpTransport.newTrustedTransport();
        JsonFactory jsonFactory = JacksonFactory.getDefaultInstance();
        Gmail service = new Gmail.Builder(httpTransport, jsonFactory,
                connexionGoogle.getCredentials(httpTransport, user)).setApplicationName(config.getApplicationName())
                        .build();

        // récupère la liste des labels de la boite mail
        ListLabelsResponse listResponse = service.users().labels().list("me").execute();
        List<Label> labels = listResponse.getLabels();
        List<String> listeLabel = new ArrayList<String>();
        if (labels.isEmpty()) {
            //TODO roa by Djer SYSOUT !!!!!
            System.out.println("No labels found.");
        } else {
            //TODO roa by Djer SYSOUT !!!!!
            System.out.println("Labels:");
            for (Label label : labels) {
                //TODO roa by Djer SYSOUT !!!!!
                System.out.printf("- %s\n", label.getName());
                listeLabel.add(label.getName());
            }
        }
        return listeLabel;
    }

    /**
     * Récupère le nombre de mail non lu.
     * @param user identifiant de l'utilisateur
     * @return Integer nombre mail non lu
     * @throws IOException exception IO
     * @throws GeneralSecurityException exception de sécurité
     */
    public Integer getNbMailNonLu(final String user) throws IOException, GeneralSecurityException {
        // Se connecte à l'API gmail avec les credentials sauvegardés
        final NetHttpTransport httpTransport = GoogleNetHttpTransport.newTrustedTransport();
        JsonFactory jsonFactory = JacksonFactory.getDefaultInstance();
        Gmail service = new Gmail.Builder(httpTransport, jsonFactory,
                connexionGoogle.getCredentials(httpTransport, user)).setApplicationName(config.getApplicationName())
                        .build();

        // récupère le nombre de mail avec le label "unread"
        Integer nbMessage = 0;
        Label llabel = service.users().labels().get(user, "UNREAD").execute();
        nbMessage = llabel.getMessagesUnread();
        //TODO roa by Djer SYSOUT !!!!!
        System.out.println(nbMessage);
        return nbMessage;
    }
}
