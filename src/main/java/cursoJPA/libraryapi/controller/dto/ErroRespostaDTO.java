package cursoJPA.libraryapi.controller.dto;

import java.util.List;

import org.springframework.http.HttpStatus;

public record ErroRespostaDTO(int status, String mensagem, List<ErroCampoDTO> erros) {

    public static ErroRespostaDTO respostaPadrao(String mensagem){
        return new ErroRespostaDTO(HttpStatus.BAD_REQUEST.value(), mensagem, List.of());
    }

    public static ErroRespostaDTO conflito(String mensagem){
        return new ErroRespostaDTO(HttpStatus.CONFLICT.value(), mensagem, List.of());
    }

}
