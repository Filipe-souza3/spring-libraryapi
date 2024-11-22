package cursoJPA.libraryapi.controller.dto;

import java.time.LocalDate;

import cursoJPA.libraryapi.model.Autor;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Past;
import jakarta.validation.constraints.Size;

public record AutorDTO(

    @NotBlank(message = "Campo obrigatório")
    @Size(min=2, max = 100, message = "Campo fora do tamanho padrão")
    String nome, 

    @NotNull(message = "Campo obrigatório")
    @Past(message = "Data nascimento nao pode ser data futura")
    LocalDate dataNascimento, 

    @NotBlank(message = "Campo obrigatório")
    @Size(min=2, max = 50, message = "Campo fora do tamanho padrão")
    String nacionalidade) {

    public Autor mapearParaAutor(){
        Autor autor = new Autor();
        autor.setNome(this.nome);
        autor.setDataNascimento(this.dataNascimento);
        autor.setNacionalidade(this.nacionalidade);
        return autor;
    }
}

