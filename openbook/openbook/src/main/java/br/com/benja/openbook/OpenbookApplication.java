package br.com.benja.openbook;

import br.com.benja.openbook.principal.Principal;
import br.com.benja.openbook.repository.AutorRepository;
import br.com.benja.openbook.repository.LivroRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;


	@SpringBootApplication
	public class OpenbookApplication implements CommandLineRunner {

		@Autowired
		private LivroRepository livroRepository;
		@Autowired
		private AutorRepository autorRepository;

		public static void main(String[] args) {
			SpringApplication.run(OpenbookApplication.class, args);
		}

		@Override
		public void run(String... args) throws Exception {
			Principal principal = new Principal(livroRepository, autorRepository);
			principal.start();
		}
	}

