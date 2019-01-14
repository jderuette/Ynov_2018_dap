/**
 * 
 */
package fr.ynov.dap.web;

import java.io.IOException;
import java.security.GeneralSecurityException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import fr.ynov.dap.data.Account;
import fr.ynov.dap.data.GoogleAccount;
import fr.ynov.dap.data.MicrosoftAccount;
import fr.ynov.dap.googleService.CalendarService;
import fr.ynov.dap.metier.Data;
import fr.ynov.dap.metier.Evenement;
import fr.ynov.dap.outlookController.EventsController;
import fr.ynov.dap.outlookService.Event;
import fr.ynov.dap.outlookService.MailService;
import fr.ynov.dap.outlookService.OutlookService;
import javassist.NotFoundException;

/**
 * Controller de tous les evenement google et microsoft
 * 
 * @author acer
 *
 */
@Controller
public class EvenementController {
	@Autowired
	private Data dataBase;
	@Autowired
	MailService outmailService;
	@Autowired
	EventsController eventsController;
	@Autowired
	CalendarService calendarService;

	@RequestMapping("allEvent/{userKey}")
	public String AllEvents(@PathVariable("userKey") String userKey, final HttpServletRequest request,
			final HttpSession session, Model model) throws NotFoundException, IOException, GeneralSecurityException {
		String response = "events";
		String error = "";
		List<Account> accounts = dataBase.listAccount(userKey);
		List<Evenement> evenements = new ArrayList<Evenement>();
		for (Account account : accounts) {

			if (account instanceof MicrosoftAccount) {
				try {
					OutlookService outlookService = outmailService.ConnexionOutlook(model, request,
							account.getAccountName());
					Event[] eventsMicrosoft = eventsController.events(outlookService);
					for (Event eventMicrosoft : eventsMicrosoft) {
						Evenement evenement = new Evenement(eventMicrosoft.getOrganizer().getEmailAddress().getName(),
								eventMicrosoft.getSubject(), eventMicrosoft.getStart().getDateTime(),
								eventMicrosoft.getEnd().getDateTime());
						evenements.add(evenement);
					}

				} catch (Exception e) {
					error += " erreur authentification pour le compte " + account.getAccountName();
				}

			} else if (account instanceof GoogleAccount) {
				try {
					List<com.google.api.services.calendar.model.Event> eventsGoogle = calendarService
							.allEvent(account.getAccountName());
					for (com.google.api.services.calendar.model.Event eventGoogle : eventsGoogle) {
						Date start = new Date();
						start.setTime(eventGoogle.getStart().getDateTime().getValue());
						Date end = new Date();
						end.setTime(eventGoogle.getEnd().getDateTime().getValue());

						Evenement evenement = new Evenement(eventGoogle.getOrganizer().getDisplayName(),
								eventGoogle.getSummary(), start, end);
						evenements.add(evenement);
					}

				} catch (Exception e) {
					error += " erreur authentification pour le compte " + account.getAccountName();
				}
			}

		}
		model.addAttribute("allevents", TriEvenements(evenements));
		model.addAttribute("error", error);
		return response;

	}


	/**
	 * tri liste des evenement par date BY ASD
	 * 
	 * @param evenements
	 * @return List<Evenement>
	 */
	private List<Evenement> TriEvenements(List<Evenement> evenements) {
		Collections.sort(evenements, new Comparator<Evenement>() {
			@Override
			public int compare(Evenement E1, Evenement E2) {
				return E1.getStartDate().compareTo(E2.getStartDate());
			}
		});

		return evenements;
	}

}
