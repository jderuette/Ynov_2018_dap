package fr.ynov.dap.GmailPOO.web;

import java.io.IOException;
import java.security.GeneralSecurityException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.hibernate.mapping.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.google.api.client.auth.oauth2.Credential;
import com.google.api.client.auth.oauth2.StoredCredential;
import com.google.api.client.googleapis.auth.oauth2.GoogleAuthorizationCodeFlow;
import com.google.api.client.util.store.DataStore;

import fr.ynov.dap.GmailPOO.data.Account;
import fr.ynov.dap.GmailPOO.data.AppUser;
import fr.ynov.dap.GmailPOO.data.GoogleAccount;
import fr.ynov.dap.GmailPOO.data.MicrosoftAccount;
import fr.ynov.dap.GmailPOO.metier.CalendarService;
import fr.ynov.dap.GmailPOO.metier.Data;
import fr.ynov.dap.GmailPOO.metier.Evenement;
import fr.ynov.dap.GmailPOO.metier.GoogleDataStore;
import fr.ynov.dap.GmailPOO.metier.GoogleService;
import fr.ynov.dap.GmailPOO.metier.MailService;
import fr.ynov.dap.GmailPOO.metier.outContactService;
import javassist.NotFoundException;
import fr.ynov.Outlook.auth.AuthHelper;
import fr.ynov.Outlook.auth.IdToken;
import fr.ynov.Outlook.auth.TokenResponse;
import fr.ynov.Outlook.service.Event;
import fr.ynov.Outlook.service.OutlookService;
import fr.ynov.Outlook.service.OutlookServiceBuilder;
import fr.ynov.Outlook.service.OutlookUser;

/**
 * @author acer
 *
 */
//TODO bes by Djer |POO| Qu'est-ce que se monstre de classe ? Un controlelr qui utilise d'autres Controller, des services et qui contient autant de routes idfférentes ? 
@Controller
public class WelcomeController {
	@Autowired
	Data dataBase;
	// @Autowired
	// EventsController eventsController;
	@Autowired
	fr.ynov.dap.GmailPOO.metier.GoogleAccount googleAccount;
	@Autowired
	GmailController mailController;
	@Autowired
	ContactController contactController;
	@Autowired
	CalendarService calendarService;
	@Autowired
	GoogleService googleService;
	public String sessionUser = " ";
	@Autowired
	MailService outmailService;
	@Autowired
	EventsController eventsController;
	@Autowired
	MailController outMailController;
@Autowired
AuthorizeController auth;
@Autowired
outContactService outontactService;
	@RequestMapping("/")
	public String welcome() {

		return "welcome";
	}

	@RequestMapping("/ajouter")
	public String aj() {

		return "ajouter";
	}

	void welcome(ModelMap model) throws IOException, GeneralSecurityException {
		// model.addAttribute("nbEmail", mailController.getNbUnreadEmails("me"));
		// model.addAttribute("nbContact", contactController.nombre_total_contacts());
	}

	//TODO bes by Djer |Spring| Plutot que de (re) coder la pagination regarde du coté de l'intégration JPA <-> Web de Spring : https://docs.spring.io/spring-data/jpa/docs/2.0.5.RELEASE/reference/html/#core.web
	@RequestMapping("/Info/general/{userKey}")
	public String consulterAppUser(final HttpServletRequest request, final HttpSession session, Model model,
			@PathVariable("userKey") String userKey, @RequestParam(name = "page", defaultValue = "0") int page,
			@RequestParam(name = "size", defaultValue = "4") int size,
			@RequestParam(name = "idAccount", defaultValue = "0") int idAccount)
			throws NotFoundException, IOException, GeneralSecurityException {
		model.addAttribute("userKey", userKey);

		AppUser au = dataBase.consulterUser(userKey);
		if (au != null) {
			model.addAttribute("AppUser", au);
		} else
			model.addAttribute("exception", new NotFoundException("Compte introvable  "));
		Page<Account> pageGoogleAccount = dataBase.listAccount(userKey, page, size);
		model.addAttribute("listGoogleAccount", pageGoogleAccount.getContent());
		int[] pages = new int[pageGoogleAccount.getTotalPages()];
		model.addAttribute("pages", pages);
		if (idAccount > 0) {
			model.addAttribute("accountName", dataBase.getAccount(idAccount).getAccountName());
			model.addAttribute("idAccount", idAccount);
			if (dataBase.getAccount(idAccount) instanceof GoogleAccount) {
				try {
					model.addAttribute("nbMicroinread",
							mailController.getListEmails(dataBase.getAccount(idAccount).getAccountName()).size());

					model.addAttribute("eventsG",
							calendarService.allEvent(dataBase.getAccount(idAccount).getAccountName()));

				} catch (Exception e) {
					model.addAttribute("errorMicro", e.getMessage());
				}

			}

			else if (dataBase.getAccount(idAccount) instanceof MicrosoftAccount) {
				try {
					OutlookService outlookService = outmailService.ConnexionOutlook(model, request,dataBase.getAccount(idAccount).getAccountName() );
					int mailOut = outmailService.Listmail(outlookService).length;
					model.addAttribute("nbMicroinread", mailOut);
					model.addAttribute("events", eventsController.events(outlookService));
				} catch (Exception e) {
					model.addAttribute("errorMicro", e.getMessage());
				}
				// model.addAttribute("errorMicro",contactController.nombre_total_contacts());
			}
		} else {
			try {
				List<Account> accounts = dataBase.listAccount1(sessionUser);
				//TODO bes by Djer |MVC| Tu n'ajoutes pas cette varaible dans ton modele (en général en fin de traitement) du coups rien n'est visible
				int nbMailUnread = 0;
				//TODO bes by Djer |MVC| Tu n'ajoutes pas cette varaible dans ton modele (en général en fin de traitement) du coups rien n'est visible
				List<Evenement> evenements = new ArrayList<Evenement>();
				for (Account account : accounts) {
					if (account instanceof MicrosoftAccount) {
						OutlookService outlookService = outmailService.ConnexionOutlook(model, request,
								account.getAccountName());
						nbMailUnread += outmailService.Listmail(outlookService).length;
						Event[] eventsMicrosoft = eventsController.events(outlookService);
						for (Event eventMicrosoft : eventsMicrosoft) {
							Evenement evenement = new Evenement(
									eventMicrosoft.getOrganizer().getEmailAddress().getName(),
									eventMicrosoft.getSubject(), eventMicrosoft.getStart().getDateTime(),
									eventMicrosoft.getEnd().getDateTime());
							evenements.add(evenement);

						}
					} else if (account instanceof GoogleAccount) {
						nbMailUnread += mailController.getListEmails(account.getAccountName()).size();
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

					}
				}

				model.addAttribute("idAccount", 0);
				model.addAttribute("accountName", "Allaccount");
				model.addAttribute("nbMicroinread", nbMailUnread);
				model.addAttribute("allevents", TriEvenements(evenements));
			} catch (Exception e) {
				model.addAttribute("errorMicro", e.getMessage());
			}
		}

		return "listaccount";
	}



	@RequestMapping(value = "/add/microsoft/account/{accountName}")
	public String addAccountMicrosoft(@PathVariable("accountName") final String accountName,
			@RequestParam(name = "userKey", required = true) String userKey, final HttpServletRequest request,
			final HttpSession session, Model model) throws GeneralSecurityException {
		String response = "addUser";

		try {
			AppUser user = dataBase.consulterUser(userKey);
			if (user != null) {
				Account account = dataBase.getAccount(accountName);
				if (account!=null) {
					model.addAttribute("error", accountName + " existe déjà  ");
				} else {
					String urlOutlook = auth.index(model, request);
					 session.setAttribute("accountName", accountName);
		               session.setAttribute("userKey", userKey);
		               response = "redirect:" + urlOutlook;
				}
			}else {
				model.addAttribute("error", userKey + " n'existe Pas");
			}
		} catch (Exception e) {
			model.addAttribute("error", userKey + "  " + e.getMessage());
		}

		return response;
	}

	@RequestMapping(value = "/saveAccount", method = RequestMethod.POST)
	public String saveAccount(Model model, HttpServletRequest request, String userKey, String accountName,
			String adrMail) {
		HttpSession session = request.getSession();
		TokenResponse tokens = (TokenResponse) session.getAttribute("tokens");
		String tenantId = (String) session.getAttribute("userTenantId");
		com.google.api.client.auth.oauth2.TokenResponse token = (com.google.api.client.auth.oauth2.TokenResponse) session
				.getAttribute("TokenGoogle");

		try {

			if (accountName.isEmpty()) {
				return "redirect:/consulterAccount?userKey=" + sessionUser + "&error="
						+ new NotFoundException("Name non valide !!!!! ").getMessage();
			} else if (adrMail.isEmpty()) {
				return "redirect:/consulterAccount?userKey=" + sessionUser + "&error="
						+ new NotFoundException("adrMail non valide !!!!! ").getMessage();

			}

			model.addAttribute("accountName", accountName);
			model.addAttribute("adrmail", adrMail);
			// dataBase.ajouterAccountMicrosoft(sessionUser,
			// adrMail,accountName,tokens,tenantId);
			dataBase.ajouterAccountGoogle("saber", adrMail, accountName, token);

		} catch (Exception e) {
			model.addAttribute("error", e);
			return "redirect:/consulterAccount?userKey=" + sessionUser + "&error=" + e.getMessage();
		}

		return "redirect:/consulterAccount?userKey=" + sessionUser;
	}

	@RequestMapping("/admin")
	public String admin(Model model) throws IOException, GeneralSecurityException {
		List<Account> accounts = dataBase.AllAccount();
		Account a;
		
		model.addAttribute("Account",accounts );
		return "admin.html";
	}

	@RequestMapping("/Callback")
	public String oAuthCallback(@RequestParam(value = "code") String code, final HttpServletRequest request,
			final HttpSession session) throws ServletException, GeneralSecurityException {
		return googleAccount.oAuthCallback(code, request, session);
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
