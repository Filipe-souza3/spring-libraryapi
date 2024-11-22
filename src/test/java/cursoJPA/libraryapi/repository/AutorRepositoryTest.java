package cursoJPA.libraryapi.repository;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import cursoJPA.libraryapi.enums.GeneroLivro;
import cursoJPA.libraryapi.model.Autor;
import cursoJPA.libraryapi.model.Livro;

@SpringBootTest
public class AutorRepositoryTest {
    
    @Autowired
    AutorRepository repository;

    @Autowired
    LivroRepository livroRepository;

    @Test
    public void resigtrar(){
        Autor autor = new Autor();
		autor.setNome("joao");
		autor.setNacionalidade("brasileiro");
		autor.setDataNascimento(LocalDate.of(1994, 6, 20));
        repository.save(autor);


        Autor autor2 = new Autor();
		autor2.setNome("filpe");
		autor2.setNacionalidade("brasileiro");
		autor2.setDataNascimento(LocalDate.of(1994, 3, 10));
        repository.save(autor2);
		//Autor autorSalvo = repository.save(autor);
    }

    @Test
    public void atualizar(){
        var id = UUID.fromString("3ec0dbe4-b9b0-4320-982c-5301bccce036");

        Optional<Autor> possivelAutor = repository.findById(id);

        if(possivelAutor.isPresent()){
            Autor autorEncontrado = possivelAutor.get();
            System.out.println("Autor encontrado:");
            System.out.println(autorEncontrado);

            autorEncontrado.setDataNascimento(LocalDate.of(1990, 3, 14));
            repository.save(autorEncontrado);
        }
    }

    @Test
    public void listar(){
        List<Autor> lista = repository.findAll();
        lista.forEach(System.out::println);
    }

    @Test
    public void count(){
        System.out.println("contagem de autores " + repository.count());
    }

    @Test
    public void deletePorId(){
        var id = UUID.fromString("3ec0dbe4-b9b0-4320-982c-5301bccce036");
        repository.deleteById(id);
    }

    @Test
    public void delete(){
        var id = UUID.fromString("2487b153-3f6b-42a9-a2ec-24c5146c73d7");
        Optional<Autor> autor = repository.findById(id);
        repository.delete(autor.get());
    }


    @Test
    void registrarAutorLivros(){
        Autor autor = new Autor();
		autor.setNome("marcio");
		autor.setNacionalidade("alemao");
		autor.setDataNascimento(LocalDate.of(1950, 3, 11)); 

        Livro livro = new Livro();
        livro.setIsbn("123-456");
        livro.setPreco(BigDecimal.valueOf(300));
        livro.setGenero(GeneroLivro.ROMANCE);
        livro.setTitulo("a flor");
        livro.setDataPublicacao(LocalDate.of(1990, 3, 10));
        livro.setAutor(autor);

        Livro livro2 = new Livro();
        livro2.setIsbn("11123-456");
        livro2.setPreco(BigDecimal.valueOf(150));
        livro2.setGenero(GeneroLivro.FANTASIA);
        livro2.setTitulo("viagem");
        livro2.setDataPublicacao(LocalDate.of(1999, 5, 8));
        livro2.setAutor(autor);

        autor.setLivros(new ArrayList<>());
        autor.getLivros().add(livro);
        autor.getLivros().add(livro2);

        repository.save(autor);
        livroRepository.saveAll(autor.getLivros());

    }

    @Test
    @Transactional
    void listarLivrosAutor(){
        UUID id = UUID.fromString("4454f3d1-2b47-4583-98de-2abc715b234f");

        Autor autor = repository.findById(id).orElse(null);
        // autor.getLivros().forEach(System.out::println);

        livroRepository.findByAutor(autor).forEach(System.out::println);
    }
    
}
