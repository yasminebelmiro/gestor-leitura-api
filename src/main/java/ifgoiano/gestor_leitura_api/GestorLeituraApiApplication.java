package ifgoiano.gestor_leitura_api;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.retry.annotation.EnableRetry;

@SpringBootApplication
@EnableRetry
public class GestorLeituraApiApplication {

	public static void main(String[] args) {
		SpringApplication.run(GestorLeituraApiApplication.class, args);
	}

}
