package cursoJPA.libraryapi.validator;

import java.time.LocalDate;
import java.util.Optional;

import org.springframework.stereotype.Component;

import cursoJPA.libraryapi.exception.PrecoObrigatorioException;
import cursoJPA.libraryapi.exception.RegistroDuplicadoException;
import cursoJPA.libraryapi.model.Livro;
import cursoJPA.libraryapi.repository.AutorRepository;
import cursoJPA.libraryapi.repository.LivroRepository;
import cursoJPA.libraryapi.service.AutorService;
import cursoJPA.libraryapi.service.LivroService;

@Component
public class LivroValidator {

    private LivroRepository livroRepository;
    private AutorRepository autorRepository;

    public LivroValidator(LivroRepository livroRepository, AutorRepository autorRepository) {
        this.livroRepository = livroRepository;
        this.autorRepository = autorRepository;
    }

    public void validar(Livro livro) {
        if (precoObrigatorio(livro)) {
            throw new PrecoObrigatorioException("o preco e obrigatorio", "preco");
        }
        if (this.existeLivro(livro)) {
            throw new RegistroDuplicadoException("ISBN livro já existe");
        }
    }

    private boolean precoObrigatorio(Livro livro) {
        return livro.getPreco() == null || livro.getDataPublicacao().getYear() > LocalDate.now().getYear();
    }

    private boolean existeLivro(Livro livro) {

        Optional<Livro> livroOptional = this.livroRepository.findByIsbn(livro.getIsbn());
        return livroOptional.map(l -> l.getId()).stream().anyMatch(id -> !id.equals(livro.getId()));
    }

}
