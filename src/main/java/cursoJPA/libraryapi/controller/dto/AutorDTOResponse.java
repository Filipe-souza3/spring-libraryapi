package cursoJPA.libraryapi.controller.dto;

import java.time.LocalDate;
import java.util.UUID;

public record AutorDTOResponse(UUID id, String nome, LocalDate dataNascimento, String nacionalidade) {

}
