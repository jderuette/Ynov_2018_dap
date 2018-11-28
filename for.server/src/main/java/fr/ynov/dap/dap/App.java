package fr.ynov.dap.dap;

import java.io.IOException;
import java.security.GeneralSecurityException;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class App
{
  public static Config config = null;

  @Bean
  private static Config loadConfig() throws IOException {
    return new Config();
  }

  
  public static void main( String[] args ) throws IOException, GeneralSecurityException {
    config = new Config();
    SpringApplication.run(App.class, args);
    System.out.println( "Hello World!" );
  }
}
