package cursoJPA.libraryapi.repository;

import java.math.BigDecimal;
import java.time.LocalDate;
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
public class LivroRepositoryTest {

    @Autowired
    LivroRepository respository;

    @Autowired
    AutorRepository autorRepository;

    @Test
    void salvar() {
        Livro livro = new Livro();
        livro.setIsbn("123-456");
        livro.setPreco(BigDecimal.valueOf(100));
        livro.setGenero(GeneroLivro.FICCAO);
        livro.setTitulo("ufo");
        livro.setDataPublicacao(LocalDate.of(1980, 3, 24));

        Optional<Autor> autor = autorRepository.findById(UUID.fromString("88b7bc15-eb6d-4a96-ac38-ab2dd39d7a2e"));

        livro.setAutor(autor.get());

        respository.save(livro);
    }

    @Test
    void salvarComAutor() {
        Livro livro = new Livro();
        livro.setIsbn("123-456");
        livro.setPreco(BigDecimal.valueOf(100));
        livro.setGenero(GeneroLivro.FANTASIA);
        livro.setTitulo("fada");
        livro.setDataPublicacao(LocalDate.of(1980, 3, 24));

        Autor autor = new Autor();
        autor.setNome("marcos");
        autor.setNacionalidade("brasileiro");
        autor.setDataNascimento(LocalDate.of(1980, 4, 17));
        livro.setAutor(autor);

        autorRepository.save(autor);
        respository.save(livro);
    }

    // @Test
    void alterarAutorLivro() {

        Optional<Livro> livro = respository.findById(UUID.fromString("6b58135d-cbba-479f-9bf9-de57b8b06a15"));

        Autor autor = new Autor();
        autor.setId(UUID.fromString("56a4817d-e2e0-425d-b9e2-2b72240a7017"));
        livro.get().setAutor(autor);

        respository.save(livro.get());
    }

    @Test
    /*
     * abre uma transaçao com o banco para fazer a busca do autor pois usamos no
     * anotacao manytoone fetch LAZY entao ele so busca
     * o autor se tiver essa anotaçao se nao ele so busca o livro mesmo
     */
    @Transactional
    void buscarLivro() {
        UUID id = UUID.fromString("25d5709f-14a3-49e4-854f-8af429e9d78d");
        Livro livro = respository.findById(id).orElse(null);

        System.out.println("livro titulo");
        System.out.println(livro.getTitulo());
        System.out.println("nome autor");
        System.out.println(livro.getAutor().getNome());
    }

    @Test
    void buscarPorTitulo() {
        respository.findByTitulo("fada").forEach(System.out::println);
    }

    @Test
    void buscarPorTituloPreco() {
        BigDecimal preco = BigDecimal.valueOf(100);
        String titulo = "ufo";

        respository.findByTituloAndPreco(titulo, preco).forEach(System.out::println);
    }

    @Test
    void listarComJPQL() {
        respository.listarTodos().forEach(System.out::println);
    }

    @Test
    void listarAutoresLivrosJPQL() {
        respository.listaAutoresLivros().forEach(System.out::println);
    }

    @Test
    void listarLivrosNaoRepetidos() {
        respository.listaTodosLivrosNaoRepetidos().forEach(System.out::println);
    }

    @Test
    void listarGenerosAutoresBrasileiros() {
        respository.generoAutoresBrasileiros().forEach(System.out::println);
    }

    /* named params */
    @Test
    void parametrosNoQueryMEthods() {
        respository.findByGenero(GeneroLivro.FANTASIA, "preco").forEach(System.out::println);
    }

    /* positional params */
    @Test
    void parametrosNoQueryMethodsPositional() {
        respository.findByGeneroPositionalParam(GeneroLivro.FANTASIA, "preco").forEach(System.out::println);
    }

    /*
     * para alterar coisas no banco precisa abrir transacao no repository e a
     * anotacao Modifying
     */
    @Test
    void deletar() {
        respository.delete(GeneroLivro.FICCAO);
    }

    /*
     * para alterar coisas no banco precisa abrir transacao no repository e a
     * anotacao Modifying
     */
    @Test
    void updateDataPublicacao() {
        respository.updateDataPublicacao(LocalDate.of(2024, 1, 1),
                UUID.fromString("a94e68c8-5c6f-404b-938d-d1f987779ce6"));
    }

}
