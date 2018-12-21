package fr.ynov.dap;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class Launcher {
    /**
     * The main method. 
     *
     * @param args the arguments
     */
    public static void main(String... args) {
        SpringApplication.run(Launcher.class, args);
    }

    @Bean
    Config loadConfig() {
        //Config configuration = new Config();
        Config configuration = new Config("djer app", "C:\\Users\\djer1\\dap\\thb\\tokens",
                "/credentials.json", "/oAuth2Callback", "https://login.microsoftonline.com",
                "https://login.microsoftonline.com/common/oauth2/v2.0/authorize");

        //    	configuration.setTokensPath("\desktop");

        return configuration;
    }
}