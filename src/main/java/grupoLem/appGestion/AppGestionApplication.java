package grupoLem.appGestion;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;

@SpringBootApplication
@ComponentScan("grupoLem.appGestion")
@EnableWebSecurity
public class AppGestionApplication {

	public static void main(String[] args) {
		SpringApplication.run(AppGestionApplication.class, args);
	}

}
