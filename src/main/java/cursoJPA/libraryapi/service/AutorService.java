package cursoJPA.libraryapi.service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.stereotype.Service;

import cursoJPA.libraryapi.model.Autor;
import cursoJPA.libraryapi.repository.AutorRepository;
import cursoJPA.libraryapi.validator.AutorValidador;

/*anotacao do lombok q ja faz o construtor com todas variaveis final, assim n precisa criar o construtor*/
// @RequiredArgsConstructor
@Service
public class AutorService {

    private final AutorRepository autorRepository;
    private final AutorValidador autorValidador;

    public AutorService(AutorRepository autorRepository, AutorValidador autorValidador) {
        this.autorRepository = autorRepository;
        this.autorValidador = autorValidador;
    }

    public Autor salvar(Autor autor) {
        this.autorValidador.validar(autor);
        return this.autorRepository.save(autor);
    }

    public Autor atualizar(Autor autor) {

        if (autor.getId() == null) {
            throw new IllegalArgumentException("Autor nao esta cadstrado na base");
        }
        this.autorValidador.validar(autor);
        return this.autorRepository.save(autor);
    }

    public Optional<Autor> buscarId(UUID id) {
        return this.autorRepository.findById(id);
    }

    public void deleteById(UUID id) {
        this.autorRepository.deleteById(id);
    }

    public void delete(Autor autor) {
        this.autorValidador.validarPossuiLivros(autor);
        this.autorRepository.delete(autor);
    }

    public List<Autor> listarPesquisar(String nome, String nacionalidade) {
        if (nome != null && nacionalidade != null) {
            return this.autorRepository.findByNomeAndNacionalidade(nome, nacionalidade);
            // return
            // this.autorRepository.findByNomeAndNacionalidadeContainingIgnoreCase(nome,
            // nacionalidade);
        }

        if (nome != null) {
            // return this.autorRepository.findByNome(nome);
            return this.autorRepository.findByNomeContainingIgnoreCase(nome);
        }

        if (nacionalidade != null) {
            // return this.autorRepository.findByNacionalidade(nacionalidade);
            return this.autorRepository.findByNacionalidadeContainingIgnoreCase(nacionalidade);
        }

        return this.autorRepository.findAll();
    }

    public List<Autor> listarPesquisarByExample(String nome, String nacionalidade) {
        Autor autor = new Autor();
        autor.setNome(nome);
        autor.setNacionalidade(nacionalidade);

        ExampleMatcher matcher = ExampleMatcher.matching().withIgnoreNullValues().withIgnoreCase()
                .withStringMatcher(ExampleMatcher.StringMatcher.CONTAINING);
        
        Example<Autor> autorExample = Example.of(autor, matcher);

        return this.autorRepository.findAll(autorExample);

    }

}
