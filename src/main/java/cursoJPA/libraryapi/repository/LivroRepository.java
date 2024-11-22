package cursoJPA.libraryapi.repository;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import cursoJPA.libraryapi.enums.GeneroLivro;
import cursoJPA.libraryapi.model.Autor;
import cursoJPA.libraryapi.model.Livro;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;



/*
 * 
 * @See LivroRepositoryTest
 */

public interface LivroRepository extends JpaRepository<Livro, UUID> {

    //outros metodos
    //https://docs.spring.io/spring-data/jpa/reference/jpa/query-methods.html

    //queey method
    List<Livro> findByAutor(Autor autor);

    List<Livro> findByTitulo(String titulo);

    List<Livro> findByTituloAndPreco(String titulo, BigDecimal preco);


    /**
     * jpql nos campos nao e o nome daa coluna do banco mas sim
     *  o nome dos atributos do objeto assim como nome da tabela e o nome do objeto
     * @return
     */
    @Query("select l from Livro as l order by l.titulo")
    List<Livro> listarTodos();

    /*
     * noa esquecer nome das tabelas e o nome do objeto e a primeira LETRA MAIUSCULA por seu o objeto
     */
    @Query("select a from Livro as l join l.autor as a")
    List<Autor> listaAutoresLivros();

    @Query("select distinct l.titulo from Livro as l")
    List<String> listaTodosLivrosNaoRepetidos();


    /*
     * do java 17 colocar 3 aspas pode escrever a string sem concatenar
     */
    @Query("""
            select l.genero 
            from Livro as l 
            join l.autor as a 
            where a.nacionalidade = 'brasileiro' 
            order by l.genero 
            """)
    List<String> generoAutoresBrasileiros();


    /*
     * named params
     */
    @Query("""
           select l from Livro as l 
           where l.genero = :genero 
           order by :ordenacao          
            """)
    List<Livro> findByGenero(@Param("genero") GeneroLivro genero, @Param("ordenacao") String ordenacao);

    /*
     * positional params
     * 
     */
    @Query("""
        select l from Livro as l 
        where l.genero = ?1 
        order by ?2          
         """)
    List<Livro> findByGeneroPositionalParam(GeneroLivro genero, String ordenacao);

    /*para alterar dados update delete  tem q abrir transacao e colocar a tbm Modifying 
     * os metodos q usam transactional presicam ser public
     */
    @Modifying
    @Transactional
    @Query("delete from Livro where genero = ?1")
    void delete(GeneroLivro genero);


    /*para alterar dados update delete  tem q abrir transacao e colocar a tbm Modifying
     *  os metodos q usam transactional presicam ser public
     */
    @Modifying
    @Transactional
    @Query("update Livro set dataPublicacao = ?1 where id = ?2")
    void updateDataPublicacao(LocalDate data, UUID id);

    boolean existsByAutor(Autor autor);


}
