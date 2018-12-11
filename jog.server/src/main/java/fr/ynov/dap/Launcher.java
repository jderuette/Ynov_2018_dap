package fr.ynov.dap;

import java.io.IOException;
import java.security.GeneralSecurityException;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.web.bind.annotation.RestController;

@RestController
@SpringBootApplication

public class Launcher {
	/*
	 * A SUPPRIMER DES QUE POSSIBLE private static String user = "me";
	 * 
	 * public String getUser() { return user; }
	 * 
	 * public void setDefaultUser(String user) { this.user = user; }
	 */
	/**
	 * Main du programme qui lance l'invite de commande afin de laisser
	 * l'utilisateur choisir les options
	 * 
	 * @param args
	 * @throws IOException
	 * @throws GeneralSecurityException
	 */
	public static void main(String[] args) throws IOException, GeneralSecurityException {
		SpringApplication.run(Launcher.class, args);

	}

	/**
	 * Configuration de l'application
	 * 
	 * @return la configuration de l'application
	 */
	@Bean
	public static Config loadConfig() {
		Config maConf = new Config();
		return maConf;
	}

}