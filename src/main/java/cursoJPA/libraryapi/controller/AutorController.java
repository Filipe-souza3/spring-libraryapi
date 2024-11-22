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
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import cursoJPA.libraryapi.controller.dto.AutorDTO;
import cursoJPA.libraryapi.controller.dto.AutorDTOResponse;
import cursoJPA.libraryapi.controller.dto.ErroRespostaDTO;
import cursoJPA.libraryapi.exception.NaoPermitidoException;
import cursoJPA.libraryapi.exception.RegistroDuplicadoException;
import cursoJPA.libraryapi.model.Autor;
import cursoJPA.libraryapi.service.AutorService;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/autores")
public class AutorController {

    private final AutorService autorService;

    public AutorController(AutorService autorService) {
        this.autorService = autorService;
    }

    // @RequestMapping(method = RequestMethod.POST) esse generico vc fala qual o
    // metodo
    @PostMapping
    public ResponseEntity<Object> salvar(@RequestBody @Valid AutorDTO autorDTO) {
        try {
            Autor autor = autorDTO.mapearParaAutor();
            this.autorService.salvar(autor);

            URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(autor.getId())
                    .toUri();

            return ResponseEntity.created(location).build();
            // return new ResponseEntity<>("Autor salvo com sucesso "+autorDTO,
            // HttpStatus.CREATED);
        } catch (RegistroDuplicadoException e) {
            ErroRespostaDTO erro = ErroRespostaDTO.conflito(e.getMessage());
            return ResponseEntity.status(erro.status()).body(erro);
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<AutorDTOResponse> buscarId(@PathVariable("id") String id) {
        UUID idAutor = UUID.fromString(id);
        Optional<Autor> optAutor = this.autorService.buscarId(idAutor);

        if (optAutor.isPresent()) {
            Autor autor = optAutor.get();
            AutorDTOResponse autorDTOResp = new AutorDTOResponse(autor.getId(), autor.getNome(),
                    autor.getDataNascimento(), autor.getNacionalidade());

            return ResponseEntity.ok(autorDTOResp);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Object> delete(@PathVariable("id") String id) {
        try {
            UUID idAutor = UUID.fromString(id);

            Optional<Autor> autor = this.autorService.buscarId(idAutor);

            if (autor.isEmpty()) {
                return ResponseEntity.notFound().build();
            }

            this.autorService.delete(autor.get());
            return ResponseEntity.noContent().build();

        } catch (NaoPermitidoException e) {
            ErroRespostaDTO erro = ErroRespostaDTO.respostaPadrao(e.getMessage());
            return ResponseEntity.status(erro.status()).body(erro);
        }
    }

    @GetMapping
    public ResponseEntity<List<AutorDTOResponse>> listarPesquisar(
            @RequestParam(value = "nome", required = false) String nome,
            @RequestParam(value = "nacionalidade", required = false) String nacionalidade) {

        List<Autor> list = this.autorService.listarPesquisarByExample(nome, nacionalidade);

        List<AutorDTOResponse> listDTOResp = list
                .stream()
                .map(l -> new AutorDTOResponse(l.getId(), l.getNome(), l.getDataNascimento(), l.getNacionalidade()))
                .collect(Collectors.toList());

        if (listDTOResp.size() > 0) {
            return ResponseEntity.ok(listDTOResp);
        }

        return ResponseEntity.badRequest().build();
    }

    @PutMapping("/{id}")
    public ResponseEntity<Object> atualizar(@PathVariable("id") String id, @RequestBody @Valid AutorDTO dto) {
        try {
            UUID idAutor = UUID.fromString(id);
            Optional<Autor> autorOptional = this.autorService.buscarId(idAutor);

            if (autorOptional.isEmpty()) {
                return ResponseEntity.notFound().build();
            }

            Autor autor = autorOptional.get();
            autor.setNome(dto.nome());
            autor.setDataNascimento(dto.dataNascimento());
            autor.setNacionalidade(dto.nacionalidade());

            this.autorService.atualizar(autor);

            return ResponseEntity.noContent().build();
        } catch (RegistroDuplicadoException e) {
            ErroRespostaDTO erro = ErroRespostaDTO.conflito(e.getMessage());
            return ResponseEntity.status(erro.status()).body(erro);
        }
    }

}
