package fr.ynov.dap;

import java.io.IOException;
import java.security.GeneralSecurityException;
<<<<<<< HEAD
import java.util.Arrays;
import java.util.Scanner;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;

import com.google.api.services.calendar.model.Event;

@SpringBootApplication
public class Launcher {
	/**
	 * Chargement de la configuration pour l'injection de dépendance dans les services
	 * @return
	 * @throws IOException
	 * @throws GeneralSecurityException
	 */
	@Bean
	public Config loadConfig() throws IOException, GeneralSecurityException {
		return new Config();
	}
	
	public static void main(String[] args) throws IOException, GeneralSecurityException {
		SpringApplication.run(Launcher.class, args);		
	}

	
	/**
	 * Permet de récupérer les beans instanciés post lancement
	 * @param context
	 * @return
	 */
	@Bean
	public CommandLineRunner  commandLineRunner(ApplicationContext context) {
	    //TODO gut by Djer Est-ce toujours utile ?
		return args -> {

            System.out.println("Let's inspect the beans provided by Spring Boot:");

            String[] beanNames = context.getBeanDefinitionNames();
            Arrays.sort(beanNames);
            for (String beanName : beanNames) {
                System.out.println(beanName);
            }

        };
	}
=======

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class Launcher {
	
	
	private static final Logger logger = LogManager.getLogger();
	/**
	 * Chargement de la configuration pour l'injection de dépendance dans les services
	 * @return
	 * @throws IOException
	 * @throws GeneralSecurityException
	 */
	@Bean
	public Config loadConfig() throws IOException, GeneralSecurityException {
		logger.debug("Chargement de la configuration au demarrage");
		return new Config();
	}
	
	public static void main(String[] args) throws IOException, GeneralSecurityException {
		SpringApplication.run(Launcher.class, args);		
	}

>>>>>>> refs/heads/master


}
