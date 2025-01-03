package com.literaturalura.challengerAlura;

import com.literaturalura.challengerAlura.principal.Principal;
import com.literaturalura.challengerAlura.repository.LibrosRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class ChallengerAluraApplication implements CommandLineRunner {
	@Autowired
	private LibrosRepository librosRepository;
	public static void main(String[] args) {
		SpringApplication.run(ChallengerAluraApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		Principal principal = new Principal(librosRepository);
		principal.muestraelMenu();
	}

}
