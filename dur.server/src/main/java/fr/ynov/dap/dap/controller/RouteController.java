package fr.ynov.dap.dap.controller;

import java.io.IOException;
import java.security.GeneralSecurityException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import fr.ynov.dap.dap.Config;
import fr.ynov.dap.dap.google.CalendarService;
import fr.ynov.dap.dap.google.ContactService;
import fr.ynov.dap.dap.google.GmailService;
import fr.ynov.dap.dap.model.EventModel;

// TODO: Auto-generated Javadoc
/**
 * The Class RouteController.
 */
@RestController
public class RouteController {

    /** The gmail. */
    @Autowired
    GmailService gmail;

    /** The calendar. */
    @Autowired
    CalendarService calendar;

    /** The contact. */
    @Autowired
    ContactService contact;

    /**
     * Gets the nb readmessage.
     *
     * @param userID the user ID
     * @return the string
     * @throws IOException Signals that an I/O exception has occurred.
     * @throws GeneralSecurityException the general security exception
     */
    @RequestMapping("/mails/unreads")
    public String GetNbReadmessage(@RequestParam final String userID) throws IOException, GeneralSecurityException {
        //TODO dur by Djer U u n d v c !!!!! (Utilise un nom de varaible claire)
        Integer i = gmail.getNbUnreadEmails(userID);
        //TODO dur by Djer Evite de renvoyer une chaine, renvoie un Objet et laisse l'appelant décider du format/langue d'affichage
        return "Mail de " + userID + ": " + i.toString();
    }

    /**
     * Gets the events.
     *
     * @param userID the user ID
     * @return the string
     * @throws IOException Signals that an I/O exception has occurred.
     * @throws GeneralSecurityException the general security exception
     */
    //TODO dur by Djer ton URL et ta méthode à un "S" mais n'en revoi qu'un seul ... pas très claire
    @RequestMapping("/events")
    public String GetEvents(@RequestParam final String userID) throws IOException, GeneralSecurityException {
        EventModel googleEvent = calendar.getNextEvent();
        if (googleEvent == null) {
            //TODO dur by Djer évite les multiple return dans la même méthode
            //TODO dur by Djer Evite de renvoyer une chaine, renvoie un Objet et laisse l'appelant décider du format/langue d'affichage
            return "Aucun évènements de prévu pour " + userID;
        } else {
            //TODO dur by Djer Evite de renvoyer une chaine, renvoie un Objet et laisse l'appelant décider du format/langue d'affichage
            return "Prochain Event prévu pour " + userID + ": " + googleEvent.getTitle() + ", à partir de "
                    + googleEvent.getStartDate() + " jusqu'à " + googleEvent.getEndDate() + ". Statut : "
                    + googleEvent.getStatus();
        }
    }

    /**
     * Gets the contacts.
     *
     * @param userID the user ID
     * @return the string
     * @throws IOException Signals that an I/O exception has occurred.
     * @throws GeneralSecurityException the general security exception
     */
    @RequestMapping("/contacts")
    public String GetContacts(@RequestParam final String userID) throws IOException, GeneralSecurityException {
        Integer total = contact.getContacts();
        if (total == null) {
            //TODO dur by Djer évite les multiple return dans la même méthode
            //TODO dur by Djer Evite de renvoyer une chaine, renvoie un Objet et laisse l'appelant décider du format/langue d'affichage
            return userID + " n'a aucun contact";
        } else {
            //TODO dur by Djer Evite de renvoyer une chaine, renvoie un Objet et laisse l'appelant décider du format/langue d'affichage
            return userID + " possède " + total + " contacts";
        }
    }

    /**
     * Hello.
     *
     * @return the string
     * @throws IOException Signals that an I/O exception has occurred.
     * @throws GeneralSecurityException the general security exception
     */
    @RequestMapping("/")
    public String Hello() throws IOException, GeneralSecurityException {
        return "C'est la détresse";
    }
}
