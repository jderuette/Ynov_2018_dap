package fr.ynov.dap.services;

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

import fr.ynov.dap.Config;
import fr.ynov.dap.data.AppUser;
import fr.ynov.dap.data.AppUserRepository;
import fr.ynov.dap.web.ConnexionGoogle;

/**
 * @author alex
 */
@Service
public class GoogleGmail {
    /**
     * Récupération de l'objet connexionGoogle par autowire.
     */
    @Autowired
    private ConnexionGoogle connexionGoogle;
    /**
     * repository.
     */
    @Autowired
    private AppUserRepository repository;
    /**
     * Permet de récupérer la liste des labels de la boite gmail de l'utilisateur.
     * @throws IOException problème lié à la récupération des credentials
     * @throws GeneralSecurityException exception de sécurité
     * @return liste des labels
     * @param user identifiant de l'utilisateur
     */
  //TODO roa by Djer |SOA| Dans tes services, renvoie de préférences des objets. Laisse au controller (via la Vue) ou au client le travail du formatage. En plus avec Spring si un RestController renvoie un objet, Spring le tranforme automatiquement (en JSON par défaut)
    public final List<String> getListLabel(final String user) throws IOException, GeneralSecurityException {
        // Se connecte à l'API gmail avec les credentials sauvegardés
        final NetHttpTransport httpTransport = GoogleNetHttpTransport.newTrustedTransport();
        JsonFactory jsonFactory = JacksonFactory.getDefaultInstance();
        Gmail service = new Gmail.Builder(httpTransport, jsonFactory,
                connexionGoogle.getCredentials(httpTransport, user)).setApplicationName(Config.getApplicationName())
                        .build();
        // récupère la liste des labels de la boite mail
        ListLabelsResponse listResponse = service.users().labels().list("me").execute();
        List<Label> labels = listResponse.getLabels();
        List<String> listeLabel = new ArrayList<String>();
        if (labels.isEmpty()) {
          //TODO roa by Djer |Rest API| Pas de SysOut sur un serveur
            System.out.println("No labels found.");
        } else {
          //TODO roa by Djer |Rest API| Pas de SysOut sur un serveur
            System.out.println("Labels:");
            for (Label label : labels) {
              //TODO roa by Djer |Rest API| Pas de SysOut sur un serveur
                System.out.printf("- %s\n", label.getName());
                listeLabel.add(label.getName());
            }
        }
        return listeLabel;
    }
    /**
     * Récupère le nombre de mail non lu.
     * @param userKey identifiant de l'utilisateur
     * @return Integer nombre mail non lu
     * @throws IOException exception IO
     * @throws GeneralSecurityException exception de sécurité
     */
    public final Integer getNbMailNonLu(final String userKey) throws IOException, GeneralSecurityException {
        // Se connecte à l'API gmail avec les credentials sauvegardés
        final NetHttpTransport httpTransport = GoogleNetHttpTransport.newTrustedTransport();
        JsonFactory jsonFactory = JacksonFactory.getDefaultInstance();
        AppUser user = repository.findByUserKey(userKey);
        List<String> accountNames = user.getAccountNames();
        Integer nbMailNonLu = 0;
        //TODO roa by Djer |POO| "string" n'est pas ... optimal comme nom de variable. "accountName" ?
        for (String string : accountNames) {
            Gmail service = new Gmail.Builder(httpTransport, jsonFactory,
                    connexionGoogle.getCredentials(httpTransport, string))
                            .setApplicationName(Config.getApplicationName()).build();
            // récupère le nombre de mail avec le label "unread"
            //TODO roa by Djer |API Google| Si tu utilise "string" ici cela m'oblige à mettre mon "google ID" (ou mon adresse mail Google) lorsque je cré mon compte (sinon j'ai un message d'erreur de "délagation")
            Label llabel = service.users().labels().get(string, "UNREAD").execute();
            nbMailNonLu += llabel.getMessagesUnread();
          //TODO roa by Djer |Rest API| Pas de SysOut sur un serveur
            System.out.println(nbMailNonLu);
        }
        return nbMailNonLu;
    }
}
