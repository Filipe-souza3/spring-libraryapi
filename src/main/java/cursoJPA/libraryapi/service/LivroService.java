package cursoJPA.libraryapi.service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import cursoJPA.libraryapi.model.Livro;
import cursoJPA.libraryapi.repository.LivroRepository;

@Service
public class LivroService {

    private final LivroRepository livroRepository;

    public LivroService(LivroRepository livroRepository) {
        this.livroRepository = livroRepository;
    }

    public Livro salvar(Livro livro) {
        return this.livroRepository.save(livro);
    }

    public List<Livro> listar(){
        return livroRepository.findAll();
    }

    public Optional<Livro> buscarId(UUID id) {
        return livroRepository.findById(id);
    }

    @Transactional
    public Optional<Livro> buscarIdComAutor(UUID id) {
        return livroRepository.findById(id);
    }

    public void deletar(Livro livro){
        this.livroRepository.delete(livro);
    }

    

}
