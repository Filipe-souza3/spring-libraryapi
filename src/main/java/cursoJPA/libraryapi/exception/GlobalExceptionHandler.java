package cursoJPA.libraryapi.exception;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.http.HttpStatus;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import cursoJPA.libraryapi.controller.dto.ErroCampoDTO;
import cursoJPA.libraryapi.controller.dto.ErroRespostaDTO;

@RestControllerAdvice // capturar exceptions que voce quer
public class GlobalExceptionHandler {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.UNPROCESSABLE_ENTITY)
    public ErroRespostaDTO handlerMethodArgumentNotValidException(MethodArgumentNotValidException e) {
        List<FieldError> fieldsError = e.getFieldErrors();

        List<ErroCampoDTO> erroCampo = fieldsError.stream()
                .map(fe -> new ErroCampoDTO(fe.getField(), fe.getDefaultMessage()))
                .collect(Collectors.toList());

        return new ErroRespostaDTO(HttpStatus.UNPROCESSABLE_ENTITY.value(), "Erro de validação", erroCampo);
    }

    @ExceptionHandler(RegistroDuplicadoException.class)
    @ResponseStatus(HttpStatus.CONFLICT)
    public ErroRespostaDTO handlerMethodRegistroDuplicadoException(RegistroDuplicadoException e){
        return ErroRespostaDTO.conflito(e.getMessage());
    }

    @ExceptionHandler(NaoPermitidoException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErroRespostaDTO handlerMethodNaoPermitidoException(NaoPermitidoException e){
        return ErroRespostaDTO.respostaPadrao(e.getMessage());
    }

    @ExceptionHandler(RuntimeException.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ErroRespostaDTO handlerMethodErroNaoTratado(RuntimeException e){
        return new ErroRespostaDTO(HttpStatus.INTERNAL_SERVER_ERROR.value(), "Erro inesperado no servidor", List.of());
    }

}
