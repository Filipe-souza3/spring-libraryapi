package cursoJPA.libraryapi.controller.dto;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.UUID;

import cursoJPA.libraryapi.enums.GeneroLivro;
import cursoJPA.libraryapi.model.Livro;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Past;

public record LivroDTO(
    //o ExceptionHandler vai capturar essa msg de exception, olhar o globalExceptionHandler
    //@ISBN
    @NotBlank(message = "campo obrigatório")
    String isbn,
    
    @NotBlank(message = "campo obrigatório")
    String titulo,

    @NotNull(message = "campo obrigatório")
    @Past(message = "não pode ser uma data futura")
    LocalDate dataPublicacao,

    GeneroLivro genero,
    BigDecimal preco,

    @NotNull(message = "campo obrigatório")
    UUID autor
) {

    public Livro mapearParaLivro(){
        Livro livro = new Livro();
        livro.setIsbn(this.isbn);
        livro.setTitulo(this.titulo);
        livro.setDataPublicacao(this.dataPublicacao);
        livro.setGenero(this.genero);
        livro.setPreco(this.preco);
        return livro;
    }

}
