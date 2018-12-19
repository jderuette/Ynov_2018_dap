package fr.ynov.dap;

import java.io.IOException;
import java.security.GeneralSecurityException;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

/**
 * The main class of the project that launches a server.
 * @author Antoine
 *
 */
@SpringBootApplication
//TODO kea by Djer |Spring| Cette anotation dit "la classe est un composant dont le nom logique est "config"". Cette calsse est déja un coposant via l'annotation @SpringBootApplication et son nom "logique" serait plutot "laucnher" (voir pas de nom du tout et laisser Spring se débrouiller) 
@Component("config")
public class Launcher {

  /**
   * Loads the configuration defined in Class Config
   * to inject it in Google services(Gmail, Calendar).
   * @return the configuration
   * @throws IOException nothing special
   * @throws GeneralSecurityException nothing special
   */
    //TODO kea by Djer |Spring| Comme tu as ajouté uen annotation "@Componnent" sur ta Classe Config tu n'a plus besoin de ce code. Spring va automatiquement faire un Singleton de ton @Componnent et du coups il le connetra lorsqu'il aura besoin de l'injecter dans d'autres classes
  @Bean
  //@Primary
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
  public static void main(final String[] args)
      throws IOException, GeneralSecurityException {
    SpringApplication.run(Launcher.class, args);
  }

  /*
   * The method used when a client wants to connect through console.
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
    System.out
        .print(gmail.unreadMessageCountToString(gmail.getUnreadMessageCount()));
    CalendarService calendar = new CalendarService(customConfig);
    for (Event e : calendar.get2nextEvents()) {
      System.out.print(calendar.eventToString(e));
    }
  }*/
}
