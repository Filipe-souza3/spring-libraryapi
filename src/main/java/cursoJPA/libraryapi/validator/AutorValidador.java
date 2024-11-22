package cursoJPA.libraryapi.validator;

import java.util.Optional;

import org.springframework.stereotype.Component;

import cursoJPA.libraryapi.exception.NaoPermitidoException;
import cursoJPA.libraryapi.exception.RegistroDuplicadoException;
import cursoJPA.libraryapi.model.Autor;
import cursoJPA.libraryapi.repository.AutorRepository;
import cursoJPA.libraryapi.repository.LivroRepository;

@Component
public class AutorValidador {

    private AutorRepository autorRepository;
    private LivroRepository livroRepository;

    public AutorValidador(AutorRepository autorRepository, LivroRepository livroRepository) {
        this.autorRepository = autorRepository;
        this.livroRepository = livroRepository;
    }

    public void validar(Autor autor) {
        if (this.existeAutor(autor)) {
            throw new RegistroDuplicadoException("Autor já cadastrado");
        }
    }

    public void validarPossuiLivros(Autor autor) {
        if (this.possuiLivro(autor)) {
            throw new NaoPermitidoException("Não permitido, autor possui livros cadastrados");
        }
    }

    private boolean existeAutor(Autor autor) {
        Optional<Autor> autorEncontrado = this.autorRepository.findByNomeAndDataNascimentoAndNacionalidade(
                autor.getNome(),
                autor.getDataNascimento(),
                autor.getNacionalidade());

        if (autor.getId() == null) {
            return autorEncontrado.isPresent();
        }

        return !autor.getId().equals(autorEncontrado.get().getId()) && autorEncontrado.isPresent();
    }

    private boolean possuiLivro(Autor autor) {
        return this.livroRepository.existsByAutor(autor);
    }
}
