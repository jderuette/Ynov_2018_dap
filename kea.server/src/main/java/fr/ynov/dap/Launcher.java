package fr.ynov.dap;

import java.io.IOException;
import java.security.GeneralSecurityException;
import java.util.Arrays;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Component;

/**
 * The main class of the project that launches a server.
 * @author Antoine
 *
 */
@SpringBootApplication
//TODO kea by Djer Pourquoi ? Ce n'est pas utile.
// deplus un "SpringBootApplication" + "componnent" pour une même classe c'est confus
@Component("config")
public class Launcher {
    private static String gmailUser;

    /**
     * Loads the configuration defined in Class Config
     * to inject it in Google services(Gmail, Calendar).
     * @return the configuration
     * @throws IOException nothing special
     * @throws GeneralSecurityException nothing special
     */
    @Bean
    @Primary
    public Config loadConfig() throws IOException, GeneralSecurityException {
        Config conf = new Config();
        return conf;
    }

    /**
     * Launch the Server spring application.
     * @param args used by Spring Framework
     * @throws IOException nothing special
     * @throws GeneralSecurityException nothing special
     */
    public static void main(String[] args) throws IOException, GeneralSecurityException {
        SpringApplication.run(Launcher.class, args);
    }

    /**
     * gets the gmail user.
     * @return the user's mail
     */
    private static String getGmailUser() {
        return Launcher.gmailUser;
    }

    /**
     * sets the gmail user.
     * @param gmailUser
     */
    private static void setGmailUser(String gmailUser) {
        Launcher.gmailUser = gmailUser;
    }
    /*
    public static void showUserInfos()
    throws IOException, GeneralSecurityException {
    	Scanner reader = new Scanner(System.in);
    	System.out.println("Quel est votre adresse Gmail ?");
    	String email = reader.nextLine();
    	reader.close();
    	setGmailUser(email);
    	Config customConfig = new Config();
    	GmailService gmail = new GmailService();
    	System.out.print(gmail.listeLabelToString());
    	System.out.print(gmail
    	.unreadMessageCountToString(gmail.getUnreadMessageCount()));
    	CalendarService calendar = new CalendarService(customConfig);
        for(Event e : calendar.get2nextEvents()) {
        	System.out.print(calendar.eventToString(e));
        }
    }*/
}
