package com.yomymoy.biblioteca_literalura;

import com.yomymoy.biblioteca_literalura.model.DatosLibro;
import com.yomymoy.biblioteca_literalura.principal.Principal;
import com.yomymoy.biblioteca_literalura.repository.AutorRepository;
import com.yomymoy.biblioteca_literalura.repository.LibroRepository;
import com.yomymoy.biblioteca_literalura.service.ConsumoAPI;
import com.yomymoy.biblioteca_literalura.service.ConvierteDatos;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class BibliotecaLiteraluraApplication implements CommandLineRunner {

	@Autowired
	private LibroRepository libroRepository;
	@Autowired
	private AutorRepository autorRepository;
	public static void main(String[] args) {
		SpringApplication.run(BibliotecaLiteraluraApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		Principal principal = new Principal(libroRepository, autorRepository);
		principal.muestraMenu();
	}
}
