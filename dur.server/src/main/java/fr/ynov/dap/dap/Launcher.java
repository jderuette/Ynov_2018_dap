package fr.ynov.dap.dap;

import java.io.IOException;
import java.security.GeneralSecurityException;
import java.util.List;

import javax.servlet.http.HttpSession;

import org.apache.catalina.servlet4preview.http.HttpServletRequest;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.google.api.client.auth.oauth2.AuthorizationCodeRequestUrl;
import com.google.api.client.auth.oauth2.Credential;
import com.google.api.client.googleapis.auth.oauth2.GoogleAuthorizationCodeFlow;
import com.google.api.services.calendar.model.Event;
import com.google.api.services.gmail.model.Label;

import fr.ynov.dap.dap.google.CalendarService;
import fr.ynov.dap.dap.google.GmailService;
// TODO: Auto-generated Javadoc

/**
 * The Class Launcher.
 */
@RestController
@SpringBootApplication
public class Launcher {
	
	/**
	 * The main method.
	 *
	 * @param args the arguments
	 * @throws IOException Signals that an I/O exception has occurred.
	 * @throws GeneralSecurityException the general security exception
	 */
	public static void main(String... args) throws IOException, GeneralSecurityException {
		SpringApplication.run(Launcher.class, args);
	}

	/**
	 * Load config.
	 *
	 * @return the config
	 */
	@Bean
	public static Config loadConfig() {
		return new Config();
	}
}