package cursoJPA.libraryapi;

import java.time.LocalDate;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

import cursoJPA.libraryapi.model.Autor;
import cursoJPA.libraryapi.repository.AutorRepository;

@SpringBootApplication
@EnableJpaAuditing
public class LibraryapiApplication {

	public static void main(String[] args) {
		SpringApplication.run(LibraryapiApplication.class, args);
		/*
			para nao ficar fazendo testes aqui vai na src/test
			var context = SpringApplication.run(LibraryapiApplication.class, args);
			AutorRepository respository = context.getBean(AutorRepository.class);
			salvarRegistro(respository);
		*/
	}

	public static void salvarRegistro(AutorRepository repository){
		Autor autor = new Autor();
		
		autor.setNome("filipe");
		autor.setNacionalidade("brasileiro");
		autor.setDataNascimento(LocalDate.of(1990, 8, 25));

		Autor autorSalvo = repository.save(autor);
		// var autorSalvo = repository.save(autor); pode usar esse tbm

		System.out.println(autorSalvo);
	}

}
