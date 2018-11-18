package com.ynov.dap;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;


/**
 * The Class Launcher.
 */
@SpringBootApplication
public class Launcher {

    /**
     * The main method.
     *
     * @param args the arguments
     */
    public static void main(final String[] args) {
        SpringApplication.run(Launcher.class, args);
    }
    
    @Bean
    public Config loadConfig() {
        return new Config();
    }
}
