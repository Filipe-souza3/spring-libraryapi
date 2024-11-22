package cursoJPA.libraryapi.repository;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import cursoJPA.libraryapi.model.Autor;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;



/**
 * @see AutorRepositoryTest
 */

//e interface pois quem vai implementar a essa interface e o spring
public interface AutorRepository extends JpaRepository<Autor, UUID> {

    List<Autor> findByNome(String nome);
    List<Autor> findByNacionalidade(String nacionalidade);
    List<Autor> findByNomeAndNacionalidade(String nome, String nacionalidade);
    
    List<Autor> findByNomeContainingIgnoreCase(String nome);
    List<Autor> findByNacionalidadeContainingIgnoreCase(String nacionalidade);
    // List<Autor> findByNomeAndNacionalidadeContainingIgnoreCase(String nome, String nacionalidade);

    Optional<Autor> findByNomeAndDataNascimentoAndNacionalidade(String nome, LocalDate nascimento, String nacionalidade);
    
    //verifica se existe
    boolean existsByNomeAndDataNascimentoAndNacionalidade(String nome, LocalDate nascimento, String nacionalidade);

}
