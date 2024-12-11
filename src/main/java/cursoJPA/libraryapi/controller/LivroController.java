package cursoJPA.libraryapi.controller;

import java.net.URI;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import cursoJPA.libraryapi.controller.dto.AutorDTO;
import cursoJPA.libraryapi.controller.dto.LivroDTO;
import cursoJPA.libraryapi.controller.dto.LivroDTOResponse;
import cursoJPA.libraryapi.enums.GeneroLivro;
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

    @GetMapping
    public ResponseEntity<List<LivroDTOResponse>> listar(
            @RequestParam(value = "comAutor", required = false) boolean comAutor) {

        List<LivroDTOResponse> listDto = null;
        if (comAutor == true) {
            List<Livro> livros = this.livroService.listarComAutor();
            listDto = livros.stream().map((livro) -> new LivroDTOResponse(
                    livro.getId(),
                    livro.getIsbn(),
                    livro.getTitulo(),
                    livro.getDataPublicacao(),
                    livro.getGenero(),
                    livro.getPreco(),
                    new AutorDTO(
                            livro.getAutor().getNome(),
                            livro.getAutor().getDataNascimento(),
                            livro.getAutor().getNacionalidade())))
                    .collect(Collectors.toList());
        } else {

            List<Livro> livros = this.livroService.listar();
            listDto = livros.stream().map((livro) -> new LivroDTOResponse(
                    livro.getId(),
                    livro.getIsbn(),
                    livro.getTitulo(),
                    livro.getDataPublicacao(),
                    livro.getGenero(),
                    livro.getPreco(),
                    null))
                    .collect(Collectors.toList());
        }

        if (listDto.size() > 0 || listDto == null) {
            return ResponseEntity.ok(listDto);
        }

        return ResponseEntity.badRequest().build();
    }

    @GetMapping("/comExample")
    public ResponseEntity<List<LivroDTOResponse>> listarPequisar(
            @RequestParam(name = "genero", required = false) String genero,
            @RequestParam(name = "titulo", required = false) String titulo) {

        List<Livro> lista = null;
        lista = this.livroService.listar();

        // if (genero != null) {
        // GeneroLivro ResultGenero = GeneroLivro.verificar(genero);
        // lista = this.livroService.listarPesquisar(ResultGenero, null);
        // }

        // if(titulo != null){
        // lista = this.livroService.listarPesquisar(null,titulo);
        // }

        // OU altera o service para aceitar o objeto todo fica mais pratico
        Livro livro = new Livro();

        if (genero != null) {
            livro.setGenero(GeneroLivro.verificar(genero));
        }

        if (titulo != null) {
            livro.setTitulo(titulo);
        }

        lista = this.livroService.listarPesquisar(livro);

        List<LivroDTOResponse> listDTO = lista.stream()
                .map(l -> new LivroDTOResponse(l.getId(), l.getIsbn(), l.getTitulo(), l.getDataPublicacao(),
                        l.getGenero(), l.getPreco(), null))
                .collect(Collectors.toList());
        // colocar para o DTO pois o jpa faz recursividade
        return ResponseEntity.ok(listDTO);

        // return ResponseEntity.badRequest().build();
    }

    @PutMapping("/{id}")
    public ResponseEntity<LivroDTOResponse> atualizar(@PathVariable("id") String id,
            @RequestBody @Valid LivroDTO livroDTO) {

        UUID idLivro = UUID.fromString(id);
        Optional<Livro> livroOptional = this.livroService.buscarId(idLivro);

        if(!livroOptional.isPresent()){
            return ResponseEntity.notFound().build();
        }

        Livro livro = livroOptional.get();
        livro.setIsbn(livroDTO.isbn());
        livro.setTitulo(livroDTO.titulo());
        livro.setDataPublicacao(livroDTO.dataPublicacao());
        livro.setGenero(livroDTO.genero());
        livro.setPreco(livroDTO.preco());
        

        this.livroService.atualizar(livro);

        return ResponseEntity.ok().build();
    }

    @GetMapping("/{id}")
    public ResponseEntity<LivroDTOResponse> buscarId(@PathVariable("id") String id) {
        UUID idLivro = UUID.fromString(id);
        // Livro livro = this.livroService.buscarId(idLivro).orElse(null);
        // Autor autor =
        // this.autorService.buscarId(livro.getAutor().getId()).orElse(null);
        // LivroDTOResponse dto = new LivroDTOResponse(
        // idLivro,
        // livro.getIsbn(),
        // livro.getTitulo(),
        // livro.getDataPublicacao(),
        // livro.getGenero(),
        // livro.getPreco(),
        // new AutorDTO(autor.getNome(), autor.getDataNascimento(),
        // autor.getNacionalidade()));

        Optional<Livro> livro = this.livroService.buscarIdComAutor(idLivro);
        if (livro.isPresent()) {
            LivroDTOResponse dto = new LivroDTOResponse(
                    idLivro,
                    livro.get().getIsbn(),
                    livro.get().getTitulo(),
                    livro.get().getDataPublicacao(),
                    livro.get().getGenero(),
                    livro.get().getPreco(),
                    new AutorDTO(livro.get().getAutor().getNome(),
                            livro.get().getAutor().getDataNascimento(),
                            livro.get().getAutor().getNacionalidade()));
            return ResponseEntity.ok(dto);

        } else {
            return ResponseEntity.notFound().build();
        }

        // return this.livroService.buscarIdComAutor(idLivro).map(livro -> {
        // LivroDTOResponse dto = new LivroDTOResponse(livro.getId(), livro.getIsbn(),
        // livro.getTitulo(), livro.getDataPublicacao(),livro.getGenero(),
        // livro.getPreco(),null);
        // return ResponseEntity.ok(dto);
        // }).orElseGet(()->{
        // return ResponseEntity.notFound().build();
        // });
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Object> deletar(@PathVariable("id") String id) {
        return this.livroService.buscarId(UUID.fromString(id)).map(livro -> {
            this.livroService.deletar(livro);
            return ResponseEntity.noContent().build();
        }).orElseGet(() -> ResponseEntity.notFound().build());
    }

}
