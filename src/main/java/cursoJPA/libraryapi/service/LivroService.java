package cursoJPA.libraryapi.service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import cursoJPA.libraryapi.model.Livro;
import cursoJPA.libraryapi.repository.LivroRepository;
import cursoJPA.libraryapi.validator.LivroValidator;

@Service
public class LivroService {

    private final LivroRepository livroRepository;
    private final LivroValidator livroValidator;

    public LivroService(LivroRepository livroRepository, LivroValidator livroValidator) {
        this.livroRepository = livroRepository;
        this.livroValidator = livroValidator;
    }

    public Livro salvar(Livro livro) {
        this.livroValidator.validar(livro);
        return this.livroRepository.save(livro);
    }

    @Transactional
    public List<Livro> listarComAutor() {
        return livroRepository.findAll();
    }

    public List<Livro> listar() {
        return livroRepository.findAll();
    }

    public void atualizar(Livro livro){
        livroRepository.save(livro);
    }


    // public List<Livro> listarPesquisar(GeneroLivro genero, String titulo) {
    public List<Livro> listarPesquisar(Livro livro) {
        // Livro livro = new Livro();
        // if(genero != null){
        //     livro.setGenero(genero);
        // }

        // if(titulo != null){
        //     livro.setTitulo(titulo);
        // }
        

        ExampleMatcher match = ExampleMatcher.matching().withIgnoreCase().withIgnoreNullValues()
                .withStringMatcher(ExampleMatcher.StringMatcher.CONTAINING);

        Example<Livro> exampleLivro = Example.of(livro, match);
        return  this.livroRepository.findAll(exampleLivro);
    }

    public Optional<Livro> buscarId(UUID id) {
        return livroRepository.findById(id);
    }

    @Transactional
    public Optional<Livro> buscarIdComAutor(UUID id) {
        return livroRepository.findById(id);
    }

    public void deletar(Livro livro) {
        this.livroRepository.delete(livro);
    }

}
