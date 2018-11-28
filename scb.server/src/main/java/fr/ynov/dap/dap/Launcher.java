package fr.ynov.dap.dap;

import java.io.IOException;
import java.security.GeneralSecurityException;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@EnableJpaRepositories("fr.ynov.dap.dap.data")
@SpringBootApplication
public class Launcher{
	
	public static void main(String... args) throws IOException, GeneralSecurityException {
		SpringApplication.run(Launcher.class, args);
	}
}