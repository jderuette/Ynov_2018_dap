package fr.ynov.dap;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import fr.ynov.dap.configuration.Config;

/**
 * @author Gaël BOSSER
 * Point d'entrée de l'application serveur
 */
@SpringBootApplication
public class Launcher {
    /**
     * @param args : regroupent tous les arguments passés en paramètres
     * @throws Exception : Levé si un problème est survenu lors du lancement de l'appli
     */
    public static void main(final String[] args) throws Exception {
        SpringApplication.run(Launcher.class, args);
    }

    /**
     * @return une nouvelle Config avec toutes les informations de configuration de base
     */
    @Bean
    public Config loadConfig() {
        return new Config();
    }
}
