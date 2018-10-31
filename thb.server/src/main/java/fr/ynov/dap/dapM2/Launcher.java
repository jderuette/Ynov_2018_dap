package fr.ynov.dap.dapM2;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class Launcher {
	

    /**
     * The main method.
     *
     * @param args the arguments
     */
    public static void main(String... args) {
    	SpringApplication.run(Launcher.class, args);
    	Config.getInstance();
    	
    	/**
    	 * Zero config
    	 * 
    	 * Config cfg = Config.getInstance();
    	 * String path = "/desktop";
    	 * cfg.setPath(path);
    	 * 
    	 */
    } 
}

