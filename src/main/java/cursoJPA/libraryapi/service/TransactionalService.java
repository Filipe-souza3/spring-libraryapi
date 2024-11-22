package cursoJPA.libraryapi.service;

import java.math.BigDecimal;
import java.time.LocalDate;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import cursoJPA.libraryapi.enums.GeneroLivro;
import cursoJPA.libraryapi.model.Autor;
import cursoJPA.libraryapi.model.Livro;
import cursoJPA.libraryapi.repository.AutorRepository;
import cursoJPA.libraryapi.repository.LivroRepository;

@Service
public class TransactionalService {

    @Autowired
    private LivroRepository livroRepository;

    @Autowired
    private AutorRepository autorRepository;

    @Transactional
    public void execComTransacao() {
        Autor autor = new Autor();
        autor.setNome("joao kk");
        autor.setNacionalidade("brasileiro");
        autor.setDataNascimento(LocalDate.of(1994, 6, 20));

        autorRepository.save(autor);
        /*
         * o flush ele altera e salva no banco, se der errado ele da rollback
         * usar o normal nao usar com flush
         */
        // autorRepository.saveAllAndFlush(autor);

        Livro livro = new Livro();
        livro.setIsbn("123-456");
        livro.setPreco(BigDecimal.valueOf(100));
        livro.setGenero(GeneroLivro.FANTASIA);
        livro.setTitulo("fada");
        livro.setDataPublicacao(LocalDate.of(1980, 3, 24));
        livro.setAutor(autor);

        livroRepository.save(livro);

        if (autor.getNome().equals(("joao kk"))) {
            throw new RuntimeException("rollback");

        }

    }

}
