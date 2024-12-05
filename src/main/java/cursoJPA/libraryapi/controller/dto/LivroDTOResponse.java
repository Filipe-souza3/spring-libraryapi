package cursoJPA.libraryapi.controller.dto;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.UUID;

import cursoJPA.libraryapi.enums.GeneroLivro;


public record LivroDTOResponse(
    UUID id,
    String isbn,
    String titulo,
    LocalDate dataPublicacao,
    GeneroLivro genero,
    BigDecimal preco,
    AutorDTO autor

) {


}
