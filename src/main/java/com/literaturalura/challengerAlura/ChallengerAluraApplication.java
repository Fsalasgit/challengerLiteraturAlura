package com.literaturalura.challengerAlura;

import com.literaturalura.challengerAlura.principal.Principal;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class ChallengerAluraApplication implements CommandLineRunner {


	public static void main(String[] args) {
		SpringApplication.run(ChallengerAluraApplication.class, args);
	}


	public void run(String... args) throws Exception {
		Principal principal = new Principal();
		System.out.println("ver");
		principal.muestraelMenu();
	}

}
