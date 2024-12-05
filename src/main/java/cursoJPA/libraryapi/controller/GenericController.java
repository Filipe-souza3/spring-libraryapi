package cursoJPA.libraryapi.controller;

import java.net.URI;
import java.util.UUID;

import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

public interface GenericController {

    default URI gerarHeaderLocation(UUID id) {
        return ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(id)
                .toUri();
    }

}
