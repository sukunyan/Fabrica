package Controlador;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import Vista.Consola;

@SpringBootApplication(scanBasePackages = "")
public class FabricaApplication {

	public static void main(String[] args) {
		SpringApplication.run(FabricaApplication.class, args);
		
		Consola consola = new Consola();
		
		consola.abrirNavegador("http://localhost:8080/");
		
	}

}
