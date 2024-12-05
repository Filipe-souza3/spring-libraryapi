package cursoJPA.libraryapi.controller;

import java.net.URI;
import java.util.Optional;
import java.util.UUID;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import cursoJPA.libraryapi.controller.dto.AutorDTO;
import cursoJPA.libraryapi.controller.dto.LivroDTO;
import cursoJPA.libraryapi.controller.dto.LivroDTOResponse;
import cursoJPA.libraryapi.controller.mappers.LivroMapper;
import cursoJPA.libraryapi.model.Autor;
import cursoJPA.libraryapi.model.Livro;
import cursoJPA.libraryapi.service.AutorService;
import cursoJPA.libraryapi.service.LivroService;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/livros")
public class LivroController implements GenericController {

    private final LivroService livroService;

    private final AutorService autorService;

    public LivroController(LivroService livroService, AutorService autorService) {
        this.livroService = livroService;
        this.autorService = autorService;
    }

    @PostMapping
    public ResponseEntity<Void> salvar(@RequestBody @Valid LivroDTO livroDTO) {
        // Livro livro = this.mapper.toEntity(livroDTO);
        Livro livro = livroDTO.mapearParaLivro();
        livro = this.livroService.salvar(livro);
        URI uri = gerarHeaderLocation(livro.getId());
        return ResponseEntity.created(uri).build();
    }

    @GetMapping("/{id}")
    public ResponseEntity<LivroDTOResponse> buscarId(@PathVariable("id") String id) {
        UUID idLivro = UUID.fromString(id);
        // Livro livro = this.livroService.buscarId(idLivro).orElse(null);
        // Autor autor = this.autorService.buscarId(livro.getAutor().getId()).orElse(null);
        // LivroDTOResponse dto = new LivroDTOResponse(
        //     idLivro, 
        //     livro.getIsbn(),
        //      livro.getTitulo(),
        //      livro.getDataPublicacao(),
        //      livro.getGenero(),
        //      livro.getPreco(),
        //      new AutorDTO(autor.getNome(), autor.getDataNascimento(), autor.getNacionalidade()));


        Optional<Livro> livro = this.livroService.buscarIdComAutor(idLivro);
        if(livro.isPresent()){
        LivroDTOResponse dto = new LivroDTOResponse(
            idLivro, 
            livro.get().getIsbn(),
             livro.get().getTitulo(),
             livro.get().getDataPublicacao(),
             livro.get().getGenero(),
             livro.get().getPreco(),
             new AutorDTO(livro.get().getAutor().getNome(),
                livro.get().getAutor().getDataNascimento(), 
                livro.get().getAutor().getNacionalidade())
             );
            return ResponseEntity.ok(dto);

        }else{
            return ResponseEntity.notFound().build();
        }

        // return this.livroService.buscarIdComAutor(idLivro).map(livro -> {
        //     LivroDTOResponse dto = new LivroDTOResponse(livro.getId(), livro.getIsbn(),
        //     livro.getTitulo(), livro.getDataPublicacao(),livro.getGenero(), livro.getPreco(),null); 
        //     return ResponseEntity.ok(dto);
        // }).orElseGet(()->{
        //     return ResponseEntity.notFound().build();
        // });
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Object> deletar(@PathVariable("id") String id){
        return this.livroService.buscarId(UUID.fromString(id)).map(livro -> {
            this.livroService.deletar(livro);
            return ResponseEntity.noContent().build();
        }).orElseGet(()-> ResponseEntity.notFound().build());
    }

}
